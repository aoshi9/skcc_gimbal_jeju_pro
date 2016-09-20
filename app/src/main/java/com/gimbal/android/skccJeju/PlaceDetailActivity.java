package com.gimbal.android.skccJeju;

import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleAdapter;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private DBHelper dbHelper;

    private ArrayList<HashMap<String, String>> itemList;
    ListView list;
    private LinearLayout mLayout;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_place);
        setContentView(R.layout.activity_detail);

        list = (ListView) findViewById(R.id.listView);
        ListAdapter adapter;

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Intent intent = getIntent();
        //String beaconNo = intent.getStringExtra("strBeaconNo");
        String beaconNo = "00002";
        //Log.v("tempLog : strBeaconNo ", beaconNo);

         /* DataBase Part */
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        dbHelper.SotBeaconInfoInsert("00001", "1", "G마켓", 37.163351, 127.081862, "http://www.gmarket.co.kr/", "미친세일", "market1", "010-2450-5037", "충남 쥐마켓 본사", "1");
        dbHelper.SotBeaconInfoInsert("00002", "1", "11번가", 39.163351, 127.081862, "http://www.11st.co.kr/", "돌은세일", "market2", "010-1111-1111", "서울 11번가 본사", "1");
        dbHelper.SotBeaconInfoInsert("00003", "1", "쿠퐝", 40.163351, 127.081862, "http://www.coupang.com/", "망할세일", "market3", "010-9898-9898", "경기 쿠퐝 본사", "1");
        dbHelper.SotBeaconInfoItemInsert("00001", "00001", "방어", 1000, "itemDiscount", "1+1");
        dbHelper.SotBeaconInfoItemInsert("00001", "00002", "옥돔", 2000, "itemDiscount", "2+1");
        dbHelper.SotBeaconInfoItemInsert("00002", "00001", "11번뇌봉", 2000, "itemDiscount", "3+1");
        dbHelper.SotBeaconInfoItemInsert("00003", "00001", "쿠퐝쿠폰", 3000, "itemDiscount", "4+1");

        String resultString = dbHelper.shopItemSelectByBeaconNo(beaconNo); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
        String[] resultArray = resultString.split("!");

        Log.v("HoyoungLog  :  ", "Data : " + resultArray[0] + ", " + resultArray[1] + ", " + resultArray[2] + ", " + resultArray[3] + ", " + resultArray[4] + ", " + resultArray[5]);
        Log.v("HoyoungLog  :  ", "Data : " + resultArray[6] + ", " + resultArray[7] + ", " + resultArray[8] + ", " + resultArray[9] + ", " + resultArray[10] + ", " + resultArray[11]);

        Log.v("tempLog  :  ", "dbHelper.shopItemSelectByBeaconNo:  " + dbHelper.shopItemSelectByBeaconNo(beaconNo));
        Log.v("tempLog  :  ", "resultString.resultString:  " + resultString);

        String title = resultArray[1];
        URL = resultArray[4];
        String place = resultArray[1];
        String placeEvent = resultArray[5];
        String placeImg = resultArray[6];
        String telNo = resultArray[7];
        String addr = resultArray[8];

        ArrayList resultArray2 = dbHelper.placeItemInfoSelect("beaconNo");

        //새로운 apapter를 생성하여 데이터를 넣은 후..
        adapter = new SimpleAdapter(
                this, resultArray2, R.layout.list_item2,
                new String[]{"NAME","PRICE"},
                new int[]{ R.id.name, R.id.price}
        );


        //화면에 보여주기 위해 Listview에 연결합니다.
        list.setAdapter(adapter);

        Log.v("placeItemInfolect  :  ", "placeItemInflect:  " + resultArray2);
        Log.v("placeItemInfolect  :  ", "placeItemInflect:  " + resultArray2.get(0));
        Log.v("placeItemInfolect  :  ", "placeItemInflect:  " + resultArray2.get(1));


      /*  String text = null;
        ArrayList<String> temp = new ArrayList<String>();


        for(int i = 0; i<resultArray2.size(); i++){
            LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
            TextView itemlist = new TextView(this);
            itemlist = (TextView) findViewById(R.id.itemlist);

            text = i + resultArray2.get(i).toString();
            temp.add(text);
            itemlist.setText(temp.get(i));

            layout.addView(itemlist);

        }*/


        TextView placeName = (TextView) findViewById(R.id.placeName);
        placeName.setText(place);

        TextView addrText = (TextView) findViewById(R.id.addr);
        addrText.setText("주소 "+ "\n" + addr);

        TextView telNoText = (TextView) findViewById(R.id.telNo);
        telNoText.setText("전화번호 " + "\n" + telNo);

        TextView placeEventText = (TextView) findViewById(R.id.placeEvent);
        placeEventText.setText("진행중 이벤트 " + "\n" + placeEvent);


        ImageView eventImg = (ImageView) findViewById(R.id.eventImg);
        //eventImg.setImageResource(getResources().getIdentifier(placeImg, "drawable", getPackageName()));
        eventImg.setImageResource(R.drawable.market1);

        imageView = (ImageView) findViewById(R.id.eventImg);

        eventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(URL));
                startActivity(intent);

            }
        });

       /* //다음이 제공하는 MapView객체 생성 및 API Key 설정
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
        });*/



    }



}
