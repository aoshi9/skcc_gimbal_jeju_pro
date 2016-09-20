package com.gimbal.android.skccJeju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;

public class MainActivity extends AppCompatActivity {
    LocationPermissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("on Create MainActivity");
        Log.v("onCreate","MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

//        startService(new Intent(this, AppService.class));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        GimbalDAO.setOptInShown(getApplicationContext());
//        enablePlaceMonitoring();
//        enableCommunications();

//
//        final ImageButton imgbtn1 = (ImageButton) findViewById(R.id.mainImg1);
//        final ImageButton imgbtn2 = (ImageButton) findViewById(R.id.mainImg2);
//
//        imgbtn1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
//                    imgbtn1.setVisibility(View.INVISIBLE);
//                    imgbtn2.setVisibility(View.VISIBLE);
//                } else if (event.getAction() == MotionEvent.ACTION_BUTTON_PRESS) {
//                    imgbtn1.setVisibility(View.VISIBLE);
//                    imgbtn2.setVisibility(View.INVISIBLE);
//                }
//                return false;
//            }
//        });
    }

    private void enablePlaceMonitoring() {
        permissions = new LocationPermissions(this);
        permissions.checkAndRequestPermission();
    }

    private void enableCommunications() {
        CommunicationManager.getInstance().startReceivingCommunications();
        PushRegistrationHelper.registerForPush();
    }

    public void onBeaconConHisClick(View view) {
        Toast.makeText(getApplicationContext(), "비콘 접속이력 .",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

    public void onStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "BT SERVICE? Start.",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BeaconMapActivity.class);
        startActivity(intent);
    }

    public void onAppStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "시장하쥬? Start",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppMenuActivity.class);
        startActivity(intent);
    }

}
