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
    private DBHelper dbHelper;
    @Override
    public void onCreate() {
        events = new LinkedList<GimbalEvent>(GimbalDAO.getEvents(getApplicationContext()));

        Gimbal.setApiKey(this.getApplication(), "8cbce2c7-6e27-419c-9be1-7b01f1d990e4");
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        placeEventListener = new PlaceEventListener() {

            @Override
            public void onVisitStart(Visit visit) {

                String visitPlace = visit.getPlace().getName();

                String visitBeaconNo= visit.getPlace().getAttributes().getValue("BC_NO");
                String visitEvent= visit.getPlace().getAttributes().getValue("EVENT");
                Double visitLit = Double.parseDouble(visit.getPlace().getAttributes().getValue("LITITUDE"));
                Double visitLong = Double.parseDouble(visit.getPlace().getAttributes().getValue("LONGITUDE"));
                String visitUrl = visit.getPlace().getAttributes().getValue("URL");

                 Log.v("tempLog  :  ", "onVisitStart Place :  "  +visitPlace);

                if( visitPlace.substring(0, 2).equals("DB")  ) {
                    //DB 용이 아닌 PLACE에는 없는 속성이라 IF 문 안으로 변경 --구민규
                    String visitPlaceSeqCd = visit.getPlace().getAttributes().getValue("PLACE_SE_CD");
                    String visitBeaconPlaceName = visit.getPlace().getAttributes().getValue("BC_PLACE_NM");
                    String visitEventImg= visit.getPlace().getAttributes().getValue("EVENT_IMG");
                    String visitTelNo= visit.getPlace().getAttributes().getValue("TEL_NO");
                    String visitAddr= visit.getPlace().getAttributes().getValue("ADDR");
                    String visitVersion= visit.getPlace().getAttributes().getValue("VERSION");
                    String visitItem1 = visit.getPlace().getAttributes().getValue("ITEM1");
                    String visitItem2 = visit.getPlace().getAttributes().getValue("ITEM2");

                    Log.v("tempLog  :  ", "onVisitStart visitBeacon:  " + visitPlace + ", " + visitBeaconNo + ", " + visitPlaceSeqCd + ", " + visitBeaconPlaceName + ", " + visitLit + ", " +
                            visitLong + ", " + visitUrl + ", " + visitEvent + ", " + visitEventImg + ", " + visitTelNo + ", " + visitAddr + ", " + visitVersion + ", " + visitItem1 + ", " + visitItem2);
                    //split \\ 추가와 resultItemArray2 추가
                    String[] resultItemArray1 = visitItem1.split("\\|");
                    String[] resultItemArray2 = visitItem2.split("\\|");

                    Log.v("tempLog  :  ", "onVisitStart resultItemArray1:  " + resultItemArray1[0] + ", " + resultItemArray1[1] + ", " + resultItemArray1[2] + ", " + resultItemArray1[3] + ", " + resultItemArray1[4]);
                    Log.v("tempLog  :  ", "onVisitStart resultItemArray2:  " + resultItemArray2[0] + ", " + resultItemArray2[1] + ", " + resultItemArray2[2] + ", " + resultItemArray2[3] + ", " + resultItemArray2[4]);
                    dbHelper.SotBeaconInfoInsert(visitBeaconNo, visitPlaceSeqCd, visitBeaconPlaceName, visitLit, visitLong, visitUrl, visitEvent, visitEventImg, visitTelNo, visitAddr, visitVersion);
                    dbHelper.SotBeaconInfoItemInsert(visitBeaconNo, resultItemArray1[0], resultItemArray1[1], resultItemArray1[2], resultItemArray1[3], resultItemArray1[4]);
                    dbHelper.SotBeaconInfoItemInsert(visitBeaconNo, resultItemArray2[0], resultItemArray2[1], resultItemArray2[2], resultItemArray2[3], resultItemArray2[4]);

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

                JejubeaconGvar.setLatitude(location.getLatitude());
                JejubeaconGvar.setLongitude(location.getLongitude());

            }

            @Override
            public void onVisitEnd(Visit visit) {
                //TODO: removc
                Log.v("tempLog  :  ", "onVisitEnd :  "  +visit.getPlace().getName());
                addEvent(new GimbalEvent(TYPE.PLACE_EXIT, visit.getPlace().getName() +" - exit", new Date(visit.getDepartureTimeInMillis()),"","","","","",""));

            }

        };

        PlaceManager.getInstance().addListener(placeEventListener);


        communicationListener = new CommunicationListener() {
            @Override
            public Collection<Communication> presentNotificationForCommunications(Collection<Communication> communications, Visit visit) {
                String commOpenYn ="Y";


                for (Communication communication : communications) {
                    Log.v("tempLog  :  ", "presentNotificationForCommunications :  "   + communication.getAttributes().getValue("beacon_no")+" , "+ communication.getAttributes().getValue("basket_chk"));

                    String strBeaconNo = communication.getAttributes().getValue("beacon_no");
                    String strBasketChk = communication.getAttributes().getValue("basket_chk");

                   //Communication 이벤트 발생시 basket_chk='Y' 인것은 장바구니에 담은 비콘만 상단바 알림 이벤트 발생
                    if(strBasketChk.equals("Y")) {
                        commOpenYn = dbHelper.selectChkBasketYn(strBeaconNo);
                        Log.v("tempLog  :  ", "commOpenYn :  "     + communication.getAttributes().getValue("beacon_no")+" , " + commOpenYn);
                    }
                }
                //commOpenYn =="Y" 인 상태에서만 Notification 이벤트 발생
                if(commOpenYn.equals("Y")) return communications;
                else return null;

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
                        Log.v("tempLog  :  ", "onNotificationClicked :  "  + communication.getTitle() + " , " + communication.getAttributes().getValue("beacon_no"));
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
