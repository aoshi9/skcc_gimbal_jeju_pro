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

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.gimbal.android.BeaconSighting;
import com.gimbal.android.Communication;
import com.gimbal.android.CommunicationListener;
import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.PlaceManager;
import com.gimbal.android.Push;
import com.gimbal.android.Visit;
import com.gimbal.android.skccJeju.GimbalEvent.TYPE;
import com.gimbal.experience.android.Action;
import com.gimbal.experience.android.ExperienceListener;
import com.gimbal.experience.android.ExperienceManager;
import com.gimbal.android.skccJeju.JejubeaconGvar;

import org.json.JSONObject;

public class AppService extends Service {
    private static final int MAX_NUM_EVENTS = 100;
    private LinkedList<GimbalEvent> events;
    private PlaceEventListener placeEventListener;
    private CommunicationListener communicationListener;
    private ExperienceListener experienceListener;
    private String mapURL;
    private DBHelper dbHelper;
    int intNo = 1;
    int locationCnt = 0;
    @Override
    public void onCreate() {
        events = new LinkedList<GimbalEvent>(GimbalDAO.getEvents(getApplicationContext()));

        Gimbal.setApiKey(this.getApplication(), "8cbce2c7-6e27-419c-9be1-7b01f1d990e4");
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        placeEventListener = new PlaceEventListener() {

            @Override
            public void onVisitStart(Visit visit) {
                //TODO: removc
                intNo = 1;
                locationCnt = 0;

                String visitPlace = visit.getPlace().getName();
                String visitBeaconNo= visit.getPlace().getAttributes().getValue("BC_NO");
                String visitPlaceSeqCd = visit.getPlace().getAttributes().getValue("PLACE_SE_CD");
                String visitBeaconPlaceName = visit.getPlace().getAttributes().getValue("BC_PLACE_NM");
                Double visitLit = Double.parseDouble(visit.getPlace().getAttributes().getValue("LITITUDE"));
                Double visitLong = Double.parseDouble(visit.getPlace().getAttributes().getValue("LONGITUDE"));
                String visitUrl = visit.getPlace().getAttributes().getValue("URL");
                String visitEvent= visit.getPlace().getAttributes().getValue("EVENT");
                String visitEventImg= visit.getPlace().getAttributes().getValue("EVENT_IMG");
                String visitTelNo= visit.getPlace().getAttributes().getValue("TEL_NO");
                String visitAddr= visit.getPlace().getAttributes().getValue("ADDR");
                String visitVersion= visit.getPlace().getAttributes().getValue("VERSION");
                String visitItem1 = visit.getPlace().getAttributes().getValue("ITEM1");
                String visitItem2 = visit.getPlace().getAttributes().getValue("ITEM2");

                Log.v("tempLog  :  ", "onVisitStart substring:  "  +visitPlace.substring(0, 2));
                if( visitPlace.substring(0, 2).equals("DB")  ) {
                    Log.v("tempLog  :  ", "onVisitStart visitBeaconNo:  " + visitPlace + ", " + visitBeaconNo + ", " + visitPlaceSeqCd + ", " + visitBeaconPlaceName + ", " + visitLit + ", " +
                            visitLong + ", " + visitUrl + ", " + visitEvent + ", " + visitEventImg + ", " + visitTelNo + ", " + visitAddr + ", " + visitVersion + ", " + visitItem1 + ", " + visitItem2);
                    String[] resultItemArray1 = visitItem1.split("|");
                    String[] resultItemArray2 = visitItem2.split("|");

                    dbHelper.SotBeaconInfoInsert(visitBeaconNo, visitPlaceSeqCd, visitBeaconPlaceName, visitLit, visitLong, visitUrl, visitEvent, visitEventImg, visitTelNo, visitAddr, visitVersion);
                    dbHelper.SotBeaconInfoItemInsert(visitBeaconNo, resultItemArray1[0], resultItemArray1[1], resultItemArray1[2], resultItemArray1[3], resultItemArray1[4]);

                }


                addEvent(new GimbalEvent(TYPE.PLACE_ENTER, visitPlace + " : " + visitEvent, new Date(visit.getArrivalTimeInMillis()+visit.getDwellTimeInMillis()),visitUrl,visitLit.toString(), visitLong.toString(), visitBeaconNo,visitPlace,visitEvent));
            }
            @Override
            public void onBeaconSighting(BeaconSighting bs, List<Visit> visit) {

                    for (Visit visit1 : visit) {
                        if(visit1 != null) {
                        }
                     }
            }

            @Override
            public void locationDetected(Location location) {

                locationCnt ++;
                //TODO: removc


                JejubeaconGvar.setLatitude(location.getLatitude());
                JejubeaconGvar.setLongitude(location.getLongitude());

                String latit = String.valueOf(location.getLatitude());
                String loanit = String.valueOf(location.getLongitude());

                if(locationCnt == 1){
                    String aqa   = String.valueOf(location.getLatitude());
                    mapURL = "daummaps://look?p="+location.getLatitude()+","+location.getLongitude();
                    addEvent(new GimbalEvent(TYPE.COMMUNICATION_INSTANT_PUSH, "현재위치 : 위도 : "+ latit+" , 경도 : "+loanit, new Date(System.currentTimeMillis()),mapURL,latit,loanit,"","","" ));
                }
                else if(locationCnt > 50000){
                    locationCnt = 1;

                    mapURL = "daummaps://look?p="+location.getLatitude()+","+location.getLongitude();
                    addEvent(new GimbalEvent(TYPE.COMMUNICATION_INSTANT_PUSH, "현재위치2: 위도 : "+ latit+" , 경도 : "+loanit, new Date(System.currentTimeMillis()),mapURL,latit,loanit,"","","" ));
                }


            }

            @Override
            public void onVisitEnd(Visit visit) {
                //TODO: removc
               // Log.v("tempLog  :  ", "onVisitEnd :  "  +visit.getPlace().getName());
                addEvent(new GimbalEvent(TYPE.PLACE_EXIT, visit.getPlace().getName() +" - exit", new Date(visit.getDepartureTimeInMillis()),"","","","","",""));

            }

        };

        PlaceManager.getInstance().addListener(placeEventListener);


        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {

                for (Communication communication : communications) {

                }

                return communications;
            }

            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Push push) {

                for (Communication communication : communications) {
                }
                return communications;
            }

            @Override
            public void onNotificationClicked(List<Communication> communications) {
                for (Communication communication : communications) {
                    if(communication != null) {

                        //TODO : Communication Attributes 에 등록 (추가 설정 필요)  -- 060911
                        String strBeaconNo = communication.getAttributes().getValue("beacon_no");
                        //앱실행 후 main이동(MainActivity 에 상세 화면 이동하도록 구현됨)
                        Intent intent = new Intent(AppService.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("strBeaconNo",  strBeaconNo);
                        intent.putExtra("strAction", "NotificationClicked" );
                        startActivity(intent);

                    }
                }

            }
        };
        CommunicationManager.getInstance().addListener(communicationListener);

        experienceListener = new ExperienceListener() {
            @Override
            public Collection<Action> filterActions(Collection<Action> actions) {
                return super.filterActions(actions);
            }

            @Override
            public void presentFragment(Fragment fragment) {
                //addEvent(new GimbalEvent(TYPE.EXPERIENCE_FRAGMENT_PRESENTED, "Experience", new Date()));
            }

            @Override
            public Notification prepareNotificationForDisplay(Notification notification, Action action, int notificationId) {
                return  super.prepareNotificationForDisplay(notification, action, notificationId);
            }

            @Override
            public void onNotificationClicked(Action action) {
            }
        };

        ExperienceManager.getInstance().addListener(experienceListener);
    }

    private void addEvent(GimbalEvent event) {
        Log.v("tempLog  :  ", "addEvent[events.size] : "  +events.size() );
        while (events.size() >= MAX_NUM_EVENTS) {
            events.removeLast();
        }
        events.add(0, event);
        GimbalDAO.setEvents(getApplicationContext(), events);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        PlaceManager.getInstance().removeListener(placeEventListener);
        CommunicationManager.getInstance().removeListener(communicationListener);
        ExperienceManager.getInstance().removeListener(experienceListener);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
