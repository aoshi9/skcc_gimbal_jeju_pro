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

public class AppMenuActivity extends AppCompatActivity {
    LocationPermissions permissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("onCreate","MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

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

    public void onStartMarketBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "동문시장 방문",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DongmoonStart.class);
        startActivity(intent);
    }

}
