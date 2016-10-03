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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        //startActivity(new Intent(this, Splash.class));

        Gimbal.setApiKey(this.getApplication(), "8cbce2c7-6e27-419c-9be1-7b01f1d990e4");
        startService(new Intent(this, AppService.class));

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        GimbalDAO.setOptInShown(getApplicationContext());
        enablePlaceMonitoring();
        enableCommunications();

        //상단알림 클릭시 화면 이동
        //TODO :비콘별 이동 화면을 다르게 갖어 갈 수 있음(Attributes 활용(Communication )
        Intent intent_noti = getIntent();
        String strAction = intent_noti.getStringExtra("strAction");
        Log.v("tempLog  :  ", "strAction.intent_noti_strAction :  "  +strAction);
        if(strAction != null && strAction.equals("NotificationClicked")){
            Intent  intent_move = new Intent(this, PlaceDetailActivity.class);
            intent_move.putExtra("strBeaconNo",  intent_noti.getStringExtra("strBeaconNo"));
            startActivity(intent_move);
        }




    }

    private void enablePlaceMonitoring() {
        permissions = new LocationPermissions(this);
        permissions.checkAndRequestPermission();
    }

    private void enableCommunications() {
        CommunicationManager.getInstance().startReceivingCommunications();
        PushRegistrationHelper.registerForPush();
    }


    public void onStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "비콘 이력 접속",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

    public void onAppStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "시장하쥬? Start",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppMenuActivity.class);
        startActivity(intent);
    }

}
