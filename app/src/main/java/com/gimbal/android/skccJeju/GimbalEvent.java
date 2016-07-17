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

import java.io.InterruptedIOException;
import java.io.Serializable;
import java.util.Date;

public class GimbalEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum TYPE {
        PLACE_ENTER, PLACE_EXIT, COMMUNICATION_PRESENTED, COMMUNICATION_ENTER, COMMUNICATION_EXIT, COMMUNICATION_INSTANT_PUSH, COMMUNICATION_TIME_PUSH,
        APP_INSTANCE_ID_RESET, COMMUNICATION, NOTIFICATION_CLICKED, EXPERIENCE_ACTION_DELIVERED, EXPERIENCE_FRAGMENT_PRESENTED, EXPERIENCE_NOTIFICATION_PREPARED,
        EXPERIENCE_NOTIFICATION_CLICKED
    }

    private TYPE type;
    private String title;
    private Date date;
    private String url;
    private String latitude;
    private String longitude;
    private String beaconNo;
    private String place;
    private String event;


    public GimbalEvent() {
    }

    public GimbalEvent(TYPE type, String title, Date date, String url, String latitude, String longitude, String beaconNo, String place , String event) {
        this.type = type;
        this.title = title;
        this.date = date;
        this.url = url;
        this.latitude = latitude;
        this.longitude= longitude;
        this.beaconNo = beaconNo;
        this.place = place;
        this.event = event;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public  String getLatitude(){
        return latitude;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public  String getLongitude(){
        return longitude;
    }

    public void setLongitude(String longitude){
        this.longitude= longitude;
    }

    public  String getBeaconNo(){
        return beaconNo;
    }

    public void setBeaconNo(String beaconNo){
        this.beaconNo = beaconNo;
    }



    public  String getPlace(){
        return place;
    }

    public void setPlace(String place){
        this.place = place;
    }

    public  String getEvent(){
        return event;
    }

    public void setEvent(String event){
        this.event = event;
    }
}
