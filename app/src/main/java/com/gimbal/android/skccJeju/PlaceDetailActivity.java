package com.gimbal.android.skccJeju;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;


/**
 * Created by min on 2016-05-05.
 */
public class PlaceDetailActivity extends AppCompatActivity {
    ImageView imageView;
    Integer i=0;
    private MapView mMapView;
    private String URL;
    private MapPOIItem marker;
    private MapPoint MARKER_POINT;
    private Double dLatitude= 0.0;
    private Double dLongitude= 0.0;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        String beaconNo = intent.getStringExtra("strBeaconNo");
         Log.v("tempLog : strBeaconNo ", beaconNo);


        //TODO :DB조회 변경으로 코딩 필요
//        String type = intent.getStringExtra("strType");
//        String title = intent.getStringExtra("strTitle");
//        URL = intent.getStringExtra("strUrl");
//        dLatitude = Double.parseDouble(intent.getStringExtra("strLatitude"));
//        dLongitude = Double.parseDouble(intent.getStringExtra("strLongitude"));
//        String place = intent.getStringExtra("strPlace");
//        String placeEvent = intent.getStringExtra("strPlaceEvent");

        //TODO : remove  DB조회 처리 후 삭제
        String title = "제주동문시장 [TEST4] : event";
        URL = "http://blog.naver.com/dudtns620/220730664693";
        dLatitude = 33.511573;
        dLongitude = 126.526101;
        String place = "제주동문시장";
        String placeEvent = "[TEST4]";

        TextView placeName = (TextView) findViewById(R.id.placeName);
        placeName.setText(place);

        TextView placeEventText = (TextView) findViewById(R.id.placeEvent);
        placeEventText.setText("진행중 이벤트 : " + placeEvent);

        ImageView eventImg = (ImageView) findViewById(R.id.eventImg);


        if(beaconNo.equals("1")){
            eventImg.setImageResource(R.drawable.popup_img1);
        }else{
            eventImg.setImageResource(R.drawable.popup_img2);
        }
        imageView = (ImageView) findViewById(R.id.eventImg);

        eventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(URL));
                startActivity(intent);

            }
        });


        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mMapView = new MapView(this);
        mMapView.setDaumMapApiKey("9d207c0434c4d2684359d20cc8e87556");

        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);

        container.addView(mMapView);

        marker = new MapPOIItem();
        marker.setItemName(place);
        marker.setTag(0);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);


        if(dLatitude != 0.0) {

            mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(dLatitude, dLongitude), 0, true);

            MARKER_POINT= MapPoint.mapPointWithGeoCoord(dLatitude, dLongitude);
            marker.setMapPoint(MARKER_POINT);
            mMapView.addPOIItem(marker);

        }
        Button  gotoMapBtn = (Button) findViewById(R.id.gotoMapBtn);
        gotoMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String mapUrl = "daummaps://look?p="+dLatitude+","+dLongitude;
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(mapUrl));
                startActivity(intent);

            }
        });

        Button  locationRfBtn = (Button) findViewById(R.id.locationRfBtn);
        locationRfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dLatitude!= 0.0) {

                    mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(dLatitude, dLongitude), 1, true);

                    MARKER_POINT= MapPoint.mapPointWithGeoCoord(dLatitude, dLongitude);
                    marker.setMapPoint(MARKER_POINT);
                    mMapView.addPOIItem(marker);

                }

            }
        });



    }



}
