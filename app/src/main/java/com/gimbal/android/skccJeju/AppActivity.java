/**
 * Copyright (C) 2015 Gimbal, Inc. All rights reserved.
 *
 * This software is the confidential and proprietary information of Gimbal, Inc.
 *
 * The following sample code illustrates various aspects of the Gimbal SDK.
 *
 * The sample code herein is provided for your convenience, and has not been
 * tested or designed to work on any particular system configuration. It is
 * provided AS IS and your use of this sample code, whether as provided or
 * with any modification, is at your own risk. Neither Gimbal, Inc.
 * nor any affiliate takes any liability nor responsibility with respect
 * to the sample code, and disclaims all warranties, express and
 * implied, including without limitation warranties on merchantability,
 * fitness for a specified purpose, and against infringement.
 */
package com.gimbal.android.skccJeju;


import android.app.Dialog;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gimbal.android.Gimbal;
import com.gimbal.experience.android.Action;
import com.gimbal.experience.android.ExperienceListener;
import com.gimbal.experience.android.ExperienceManager;


import java.util.Collection;


public class AppActivity extends AppCompatActivity{
    private static final String POP_WINDOW = "popup_window";

    private GimbalEventReceiver gimbalEventReceiver;
    private GimbalEventListAdapter adapter;
    private ExperienceListener experienceListener;

    private FrameLayout fragmentContainerLayout;
    private Button fragmentCloseButton;

    private boolean inBackground;
    private Action clickedAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
        Gimbal.setApiKey(this.getApplication(), "8cbce2c7-6e27-419c-9be1-7b01f1d990e4");
//
      startService(new Intent(this, AppService.class));

//        if (GimbalDAO.showOptIn(getApplicationContext())) test{
//            startActivity(new Intent(this, OptInActivity.class));
//        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        adapter = new GimbalEventListAdapter(this);


        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: removc
                Log.v("tempLog  :  ", "listView.setOnItemClickListener:  ");
                String type = ((TextView)view.findViewById(R.id.type)).getText().toString();
                String title = ((TextView)view.findViewById(R.id.title)).getText().toString();
                String url = ((TextView)view.findViewById(R.id.url)).getText().toString();
                String latitude = ((TextView)view.findViewById(R.id.latitude)).getText().toString();
                String longitude = ((TextView)view.findViewById(R.id.longitude)).getText().toString();
                String beaconNo = ((TextView)view.findViewById(R.id.beaconNo)).getText().toString();
                String place = ((TextView)view.findViewById(R.id.place)).getText().toString();
                String placeEvent = ((TextView)view.findViewById(R.id.placeEvent)).getText().toString();



                if(type.equals("PLACE_ENTER")){
                    Intent  intent = new Intent(AppActivity.this, PlaceDetailActivity.class);
                    intent.putExtra("strType",  type);
                    intent.putExtra("strTitle",  title);
                    intent.putExtra("strUrl",  url);
                    intent.putExtra("strLatitude",  latitude);
                    intent.putExtra("strLongitude",  longitude);
                    intent.putExtra("strBeaconNo",  beaconNo);
                    intent.putExtra("strPlace",  place);
                    intent.putExtra("strPlaceEvent",  placeEvent);

                    startActivity(intent);

//                    Dialog dialog = new Dialog(this);
//                    dialog.setContentView(R.layout.popup_img);
//
//                    ImageView iv = (ImageView) dialog.findViewById(R.id.popup);
//                    iv.setImageResource(R.drawable.popup_img1);
                }else if(type.equals("COMMUNICATION_PRESENTED") || type.equals("COMMUNICATION_INSTANT_PUSH") ){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);

                }

                handleCloseFragment();
            }
        });

        fragmentContainerLayout = (FrameLayout) findViewById(R.id.popup_window);
        fragmentCloseButton = (Button) findViewById(R.id.close_button);

        fragmentCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCloseFragment();
            }
        });

        experienceListener = new ExperienceListener() {
            @Override
            public Collection<Action> filterActions(Collection<Action> actions) {
                return null;
            }

            @Override
            public void presentFragment(final Fragment fragment) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handlePresentFragment(fragment);
                    }
                });
            }

            @Override
            public Notification prepareNotificationForDisplay(Notification notification, Action action, int notificationId) {
                return null;
            }

            @Override
            public void onNotificationClicked(Action action) {
                if (inBackground) {
                    bringAppToForeground();
                    clickedAction = action;
                }
                else {
                    ExperienceManager.getInstance().receivedExperienceAction(action);
                }
            }
        };

        ExperienceManager.getInstance().addListener(experienceListener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.setEvents(GimbalDAO.getEvents(getApplicationContext()));
        inBackground = false;
        if (clickedAction != null) {
            ExperienceManager.getInstance().receivedExperienceAction(clickedAction);
            clickedAction = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        gimbalEventReceiver = new GimbalEventReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GimbalDAO.GIMBAL_NEW_EVENT_ACTION);
        registerReceiver(gimbalEventReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(gimbalEventReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        inBackground = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ExperienceManager.getInstance().removeListener(experienceListener);
    }

    class GimbalEventReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            adapter.setEvents(GimbalDAO.getEvents(getApplicationContext()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void handleCloseFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(POP_WINDOW);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        if (fragmentContainerLayout.isShown()) {
            fragmentContainerLayout.setVisibility(View.GONE);
        }
    }

    private void handlePresentFragment(Fragment fragment) {
        if (fragment != null) {
            if (!fragmentContainerLayout.isShown()) {
                fragmentContainerLayout.setVisibility(View.VISIBLE);
            }

            if (getSupportFragmentManager().findFragmentByTag(POP_WINDOW) != null) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.popup_window, fragment, POP_WINDOW).addToBackStack(POP_WINDOW).commit();
        }
    }

    private void bringAppToForeground() {
        Intent intent = new Intent(this, AppActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }
}
