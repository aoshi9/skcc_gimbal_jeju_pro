package com.gimbal.android.skccJeju;

import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by min on 2016-05-05.
 */
public class BeaconMapActivity extends AppCompatActivity implements MapView.POIItemEventListener {
    Integer i=0;
    private MapView mMapView;
    private String URL;
    private MapPOIItem marker;
    private MapPoint MARKER_POINT;
    private ArrayList<HashMap<String, String>> list;
    private DBHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*  Map Part */
        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mMapView = new MapView(this);
        mMapView.setDaumMapApiKey("9d207c0434c4d2684359d20cc8e87556");
        mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.550834, 126.993057), true);
        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        RelativeLayout container = (RelativeLayout) findViewById(R.id.map_view);

        //MapView에 POIItemEventListener 등록
        mMapView.setPOIItemEventListener(this);
        container.addView(mMapView);

        ArrayList<MapPOIItem> markers = new ArrayList<MapPOIItem>();

        MapPOIItem marker1 = new MapPOIItem();
        marker1.setItemName("별시작 1");
        marker1.setTag(0);
        marker1.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        MARKER_POINT= MapPoint.mapPointWithGeoCoord(37.554974, 127.003744);
        marker1.setMapPoint(MARKER_POINT);
        markers.add(marker1);

        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("별시작 2");
        marker2.setTag(0);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        MARKER_POINT= MapPoint.mapPointWithGeoCoord(37.554974, 126.983177);
        marker2.setMapPoint(MARKER_POINT);
        markers.add(marker2);

        MapPOIItem marker3 = new MapPOIItem();
        marker3.setItemName("별시작 3");
        marker3.setTag(0);
        marker3.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        MARKER_POINT= MapPoint.mapPointWithGeoCoord(37.547500, 126.998851);
        marker3.setMapPoint(MARKER_POINT);
        markers.add(marker3);

        MapPOIItem marker4 = new MapPOIItem();
        marker4.setItemName("별시작 4");
        marker4.setTag(0);
        marker4.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker4.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        MARKER_POINT= MapPoint.mapPointWithGeoCoord(37.559918, 126.992628);
        marker4.setMapPoint(MARKER_POINT);
        markers.add(marker4);

        MapPOIItem marker5 = new MapPOIItem();
        marker5.setItemName("별시작 5");
        marker5.setTag(0);
        marker5.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker5.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        MARKER_POINT= MapPoint.mapPointWithGeoCoord(37.544914, 126.989367);
        marker5.setMapPoint(MARKER_POINT);
        markers.add(marker5);

        for(int i=0; i<markers.size(); i++) {
            mMapView.addPOIItem(markers.get(i));
        }

        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0));

        // PolyLine 좌표지정
        for(int i=0; i<markers.size(); i++) {
            polyline.addPoint(markers.get(i).getMapPoint());
        }
        polyline.addPoint(markers.get(0).getMapPoint());

        // PolyLine 지도에 올리기
        mMapView.addPolyline(polyline);

        Log.v("HoyoungLog  :  ", "Data : " + 1);
        /* ListView part */
        ListView listView=(ListView)findViewById(R.id.listView1);
        list=new ArrayList<HashMap<String,String>>();

        /* DataBase Part */
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        Log.v("HoyoungLog  :  ", "Data : " + 2);
        //세 개의 가게에 각각 하나의 아이템들이 있다고 가정을 합시다.
        long result1 = dbHelper.SotBeaconInfoInsert("00001", "1", "G마켓", 37.163351, 127.081862, "http://www.gmarket.co.kr/", "미친세일", "미친세일중.jpg", "010-2450-5037", "충남 쥐마켓 본사", "1");
        long result2 = dbHelper.SotBeaconInfoInsert("00002", "1", "11번가", 39.163351, 127.081862, "http://www.11st.co.kr/", "돌은세일", "돌은세일중.jpg", "010-1111-1111", "서울 11번가 본사", "1");
        long result3 = dbHelper.SotBeaconInfoInsert("00003", "1", "쿠퐝", 40.163351, 127.081862, "http://www.coupang.com/", "망할세일", "망할세일중.jpg", "010-9898-9898", "경기 쿠퐝 본사", "1");
        long result4 = dbHelper.SotBeaconInfoItemInsert("00001", "00001", "쥐고기", 1000, "itemDiscount", "1+1");
        long result5 = dbHelper.SotBeaconInfoItemInsert("00002", "00002", "11번뇌봉", 2000, "itemDiscount", "2+1");
        long result6 = dbHelper.SotBeaconInfoItemInsert("00003", "00003", "쿠퐝쿠폰", 3000, "itemDiscount", "3+1");

        /* insert문제없음을 확인
        Log.v("HoyoungLog  :  ", "Result1 : " + result1);
        Log.v("HoyoungLog  :  ", "Result2 : " + result2);
        Log.v("HoyoungLog  :  ", "Result3 : " + result3);
        Log.v("HoyoungLog  :  ", "Result4 : " + result4);
        Log.v("HoyoungLog  :  ", "Result5 : " + result5);
        Log.v("HoyoungLog  :  ", "Result6 : " + result6);
        */

        String resultString = dbHelper.shopItemSelectByPlaceSeCd("1"); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
        String[] resultArray = resultString.split("!");

        Log.v("HoyoungLog  :  ", "Data : " + resultArray[0] + ", " + resultArray[1] + ", " + resultArray[2] + ", " + resultArray[3] + ", " + resultArray[4] + ", " + resultArray[5]);
        Log.v("HoyoungLog  :  ", "Data : " + resultArray[6] + ", " + resultArray[7] + ", " + resultArray[8] + ", " + resultArray[9] + ", " + resultArray[10] + ", " + resultArray[11]);
        //BC_NO, BC_PLACE_NM, LATITUDE, LONGITUDE, URL, ITEM_NM, ITEM_PRICE
        for(int i=0; i<resultArray.length/7; i++) {
            HashMap<String,String> temp = new HashMap<String, String>();
            temp.put(FIRST_COLUMN, resultArray[7*i]);
            temp.put(SECOND_COLUMN, resultArray[7*i+1]);
            temp.put(THIRD_COLUMN, resultArray[7*i+2]);
            temp.put(FOURTH_COLUMN, resultArray[7*i+3]);
            temp.put(FIFTH_COLUMN, resultArray[7*i+4]);
            temp.put(SIXTH_COLUMN, resultArray[7*i+5]);
            temp.put(SEVENTH_COLUMN, resultArray[7*i+6]);
            list.add(temp);
        }

        ItemListAdapter adapter = new ItemListAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                //pos는 list 시퀀스로 0부터 시작한다.
                int pos=position+1;
                Toast.makeText(BeaconMapActivity.this, Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        //리스트 클릭된 녀석들에 대해 BC_NO, item_nm 정보를 저장하고 있다가, 이 버튼을 누르면, 기존 ITEM테이블의 BASKET_YN 모두 N으로 바꾸고, 저장된 BC_NO에 대해 BASKET_YN Y로 변경
        /* Button Part */
        Button  wishListBtn = (Button) findViewById(R.id.goToWishList);
        wishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /* POI EventListener Method */
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    /* POI EventListener Method */

}
