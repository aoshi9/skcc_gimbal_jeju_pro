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


import android.app.Activity;

import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceManager;
import com.gimbal.experience.android.ExperienceManager;

public class SettingsActivity extends Activity {

    private CheckBox placeMonitoringCheckBox;
    private CheckBox commMonitoringCheckBox;
    private CheckBox experienceMonitoringCheckBox;
    LocationPermissions locationPermissions;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        placeMonitoringCheckBox = (CheckBox) findViewById(R.id.place_monitoring_checkbox);
        commMonitoringCheckBox = (CheckBox) findViewById(R.id.comm_monitoring_checkbox);
        experienceMonitoringCheckBox = (CheckBox) findViewById(R.id.exp_monitoring_checkbox);
        locationPermissions= new LocationPermissions(this);
    }

    public void onPlaceMonitoringClicked(View view) {
        placeMonitoringCheckBox.setChecked(!placeMonitoringCheckBox.isChecked());
        if (placeMonitoringCheckBox.isChecked()) {
            locationPermissions = new LocationPermissions(this);
            locationPermissions.checkAndRequestPermission();
        }
        else {
            PlaceManager.getInstance().stopMonitoring();
        }
    }

    public void onCommMonitoringClicked(View view) {
        commMonitoringCheckBox.setChecked(!commMonitoringCheckBox.isChecked());
        if (commMonitoringCheckBox.isChecked()) {
            CommunicationManager.getInstance().startReceivingCommunications();
            PushRegistrationHelper.registerForPush();
        }
        else {
            CommunicationManager.getInstance().stopReceivingCommunications();
        }
    }

    public void onExperienceMonitoringClicked(View view) {
        experienceMonitoringCheckBox.setChecked(!experienceMonitoringCheckBox.isChecked());
        if (experienceMonitoringCheckBox.isChecked()) {
            ExperienceManager.getInstance().startMonitoring();
        }
        else {
            PlaceManager.getInstance().stopMonitoring();
        }
    }

    public void onResetAppInstance(View view) {
        Gimbal.resetApplicationInstanceIdentifier();
        Toast.makeText(this, "App Instance ID reset successful", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        placeMonitoringCheckBox.setChecked(PlaceManager.getInstance().isMonitoring());
        commMonitoringCheckBox.setChecked(CommunicationManager.getInstance().isReceivingCommunications());
        experienceMonitoringCheckBox.setChecked(ExperienceManager.getInstance().isMonitoring());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        locationPermissions.onRequestPermissionResult(requestCode, permissions, grantResults);
    }

}
