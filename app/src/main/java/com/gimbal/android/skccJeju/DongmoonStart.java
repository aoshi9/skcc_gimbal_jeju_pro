package com.gimbal.android.skccJeju;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.ViewFlipper;

import com.gimbal.android.CommunicationManager;

public class DongmoonStart extends AppCompatActivity {

    LocationPermissions permissions;
    ViewFlipper flipper;

    //자동 Flipping 선택 ToggleButton 참조변수
   ToggleButton toggle_Flipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("onCreate","MainActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dongmoon);
        flipper = (ViewFlipper)findViewById(R.id.flipper);

        Animation showIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);

        flipper.setInAnimation(showIn);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
        flipper.setFlipInterval(3000);
        flipper.startFlipping();
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
        Toast.makeText(getApplicationContext(), "제주동문시장 관광 도우미.",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BeaconMapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent  intent = new Intent(this, AppMenuActivity.class); //나중에 추가되면 변경할 것
        startActivity(intent);
    }

}
