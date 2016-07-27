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

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;


/**
 * Created by min on 2016-05-05.
 */
public class BeaconMapActivity extends AppCompatActivity {
    ImageView imageView;
    Integer i=0;
    private MapView mMapView;
    private String URL;
    private MapPOIItem marker;
    private MapPoint MARKER_POINT;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        //

        imageView = (ImageView) findViewById(R.id.dongmoon);

        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mMapView = new MapView(this);
        mMapView.setDaumMapApiKey("9d207c0434c4d2684359d20cc8e87556");

        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);

        container.addView(mMapView);

        marker = new MapPOIItem();
        marker.setItemName("동문시장 : 이벤트장소 첫번째집");
        marker.setTag(0);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);


        Log.v("tempLog  :  ", "BeaconMapActivity start:  "  + JejubeaconGvar.getLatitude());

        if(JejubeaconGvar.getLatitude() != 0.0) {

            mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(JejubeaconGvar.getLatitude(), JejubeaconGvar.getLongitude()), 1, true);

            MARKER_POINT= MapPoint.mapPointWithGeoCoord(JejubeaconGvar.getLatitude(), JejubeaconGvar.getLongitude());
            marker.setMapPoint(MARKER_POINT);
            mMapView.addPOIItem(marker);

        }



        Button  gotoMapBtn = (Button) findViewById(R.id.gotoMapBtn);
        gotoMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                URL = "daummaps://look?p="+JejubeaconGvar.getLatitude()+","+JejubeaconGvar.getLongitude();
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(URL));
                startActivity(intent);

            }
        });

        Button  locationRfBtn = (Button) findViewById(R.id.locationRfBtn);
        locationRfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(JejubeaconGvar.getLatitude() != 0.0) {

                    mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(JejubeaconGvar.getLatitude(), JejubeaconGvar.getLongitude()), 1, true);

                    MARKER_POINT= MapPoint.mapPointWithGeoCoord(JejubeaconGvar.getLatitude(), JejubeaconGvar.getLongitude());
                    marker.setMapPoint(MARKER_POINT);
                    mMapView.addPOIItem(marker);

                }

            }
        });



    }



}
