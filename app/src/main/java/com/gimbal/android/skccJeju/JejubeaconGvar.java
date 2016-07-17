package com.gimbal.android.skccJeju;

import android.app.Application;

/**
 * Created by min on 2016-06-11.
 */
public class JejubeaconGvar extends Application {

    private static double latitude =0;
    private static double longitude =0;

    public static double getLatitude(){
        return latitude;
    }

    public static void setLatitude(double latitude){
        JejubeaconGvar.latitude = latitude;
    }

    public static double getLongitude(){
        return longitude;
    }

    public static void setLongitude(double longitude){
        JejubeaconGvar.longitude = longitude;
    }

}
