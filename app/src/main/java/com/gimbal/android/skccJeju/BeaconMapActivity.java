package com.gimbal.android.skccJeju;

import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;    //BC_NO
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;   //BC_PLACE_NAME
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;    //LATITUDE
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;   //LONGITUDE
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;    //URL
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;    //ITEM_NM
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;  //ITEM_PRICE
import static com.gimbal.android.skccJeju.Constant.EIGHTH_COLUMN;  //ITEM_NO
import static com.gimbal.android.skccJeju.Constant.NINTH_COLUMN;

import android.app.Activity;
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
import android.widget.TextView;
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
    private RelativeLayout container;
    private ArrayList<MapPOIItem> markers = new ArrayList<MapPOIItem>();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_map);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("시장 둘러보기");
        toolbar.setSubtitle("장바구니에 담을 상품을 클릭하세요");

        /*  Map Part */
        //다음이 제공하는 MapView객체 생성 및 API Key 설정
        mMapView = new MapView(this);
        mMapView.setDaumMapApiKey("9d207c0434c4d2684359d20cc8e87556");
        //지도의 중심은 동문시장 좌표로!
        mMapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(33.512789, 126.528353), -3, true);
        //xml에 선언된 map_view 레이아웃을 찾아온 후, 생성한 MapView객체 추가
        container = (RelativeLayout) findViewById(R.id.map_view);

        //MapView에 POIItemEventListener 등록
        mMapView.setPOIItemEventListener(this);
        container.addView(mMapView);


        // Log.v("HoyoungLog  :  ", "Data : " + 1);

        /* ListView part */
        ListView listView=(ListView)findViewById(R.id.listView1);
        list = new ArrayList<HashMap<String,String>>();

        /* DataBase Part */
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

//        dbHelper.DELETE();
        //Log.v("HoyoungLog  :  ", "Data : " + 2);
        //세 개의 가게에 각각 하나의 아이템들이 있다고 가정을 합시다.
//        long result1 = dbHelper.SotBeaconInfoInsert("00001", "1", "G마켓", 37.163351, 127.081862, "http://www.gmarket.co.kr/", "미친세일", "미친세일중.jpg", "010-2450-5037", "충남 쥐마켓 본사", "1");
//        long result2 = dbHelper.SotBeaconInfoInsert("00002", "1", "11번가", 39.163351, 127.081862, "http://www.11st.co.kr/", "돌은세일", "돌은세일중.jpg", "010-1111-1111", "서울 11번가 본사", "1");
//        long result3 = dbHelper.SotBeaconInfoInsert("00003", "1", "쿠퐝", 40.163351, 127.081862, "http://www.coupang.com/", "망할세일", "망할세일중.jpg", "010-9898-9898", "경기 쿠퐝 본사", "1");
//        long result4 = dbHelper.SotBeaconInfoItemInsert("00001", "12", "쥐고기", "1000", "itemDiscount", "1+1");
//        long result5 = dbHelper.SotBeaconInfoItemInsert("00002", "13", "11번뇌봉", "2000", "itemDiscount", "2+1");
//        long result6 = dbHelper.SotBeaconInfoItemInsert("00003", "14", "쿠퐝쿠폰", "3000", "itemDiscount", "3+1");

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

        //Log.v("HoyoungLog  :  ", "Data : " + resultArray[0] + ", " + resultArray[1] + ", " + resultArray[2] + ", " + resultArray[3] + ", " + resultArray[4] + ", " + resultArray[5]);
        //Log.v("HoyoungLog  :  ", "Data : " + resultArray[6] + ", " + resultArray[7] + ", " + resultArray[8] + ", " + resultArray[9] + ", " + resultArray[10] + ", " + resultArray[11]);
        //BC_NO, BC_PLACE_NM, LATITUDE, LONGITUDE, URL, ITEM_NM, ITEM_PRICE, ITEM_NO
        for(int i=0; i<resultArray.length/9; i++) {
            HashMap<String,String> dataMap = new HashMap<String, String>();
            dataMap.put(FIRST_COLUMN, resultArray[9*i]);
            dataMap.put(SECOND_COLUMN, resultArray[9*i+1]);
            dataMap.put(THIRD_COLUMN, resultArray[9*i+2]);
            dataMap.put(FOURTH_COLUMN, resultArray[9*i+3]);
            dataMap.put(FIFTH_COLUMN, resultArray[9*i+4]);
            dataMap.put(SIXTH_COLUMN, resultArray[9*i+5]);
            dataMap.put(SEVENTH_COLUMN, resultArray[9*i+6]);
            dataMap.put(EIGHTH_COLUMN, resultArray[9*i+7]);
            dataMap.put(NINTH_COLUMN, resultArray[9*i+8]);
            list.add(dataMap);
        }

        //Log.v("HoyoungLog  :  ", "List Size : " + list.size());
        ItemListAdapter adapter = new ItemListAdapter(this, list);
        listView.setAdapter(adapter);

        /* MapView에 ListView Item 올리기 */
        markers = new ArrayList<MapPOIItem>();
        MapPOIItem marker = new MapPOIItem();

        // MapPOIItem형 ArrayList를 ListView item의 item갯수에 맞추어 채우기
        for(int i=0; i<list.size(); i++) {
            markers.add(new MapPOIItem());
        }

        Log.v("HoyoungLog  :  ", "위도경도 : " + list.get(0).get(THIRD_COLUMN) + ", " + list.get(0).get(FOURTH_COLUMN));
        // MapPOIItem형의 ArrayList 구성객체들에 데이터를 넣어주기 -> 현재 심각한 결함은 가게단위가 아니라 아이템 단위로 보여주기에 동일위치에 여러 마커 겹쳐 존재
        for(int i=0; i<list.size(); i++) {
            marker = markers.get(i);
            marker.setItemName(list.get(i).get(SECOND_COLUMN));
            marker.setTag(1); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
            MARKER_POINT= MapPoint.mapPointWithGeoCoord(Double.valueOf(list.get(i).get(THIRD_COLUMN)), Double.valueOf(list.get(i).get(FOURTH_COLUMN)));
            marker.setMapPoint(MARKER_POINT);
            marker.setUserObject(i); // marker마다 list번호 정보를 달아둔다.
            markers.add(marker);
        }

        //marker들을 지도에 올린다.
        for(int i=0; i<markers.size(); i++) {
            mMapView.addPOIItem(markers.get(i));
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                // 선택된 item이 click 되어 있는 상태인지 체크하는 부분 (최준형)
                Log.v("HoyoungLog  :  ", "isEnabled : " + parent.getChildAt(position).isEnabled());
                if(parent.getChildAt(position).isEnabled()) {
//                    view.setBackgroundColor(Color.GRAY);             // 선택했을 때, 회색으로 하이라이트

                    Toast.makeText(getApplicationContext(), list.get(position).get(SIXTH_COLUMN) + " 장바구니 담기", Toast.LENGTH_SHORT).show();

                    parent.getChildAt(position).setEnabled(false);
                    Log.v("tempLog  :  ", "basketYShopItemSelect : " + list.get(position).get(FIRST_COLUMN) + ", " + list.get(position).get(EIGHTH_COLUMN));
                    dbHelper.SotBeaconInfoItemBasketY(list.get(position).get(FIRST_COLUMN), list.get(position).get(EIGHTH_COLUMN));     // DB BASKET_YN 컬럼 update
                    Log.v("tempLog  :  ", "basketYShopItemSelect : " + dbHelper.basketYShopItemSelect());
                    TextView tv = (TextView) view.findViewById(R.id.basketYn);
                    tv.setText("Y");
                } else {
//                    view.setBackgroundColor(Color.TRANSPARENT);     // 취소했을 때, 원상복구

                    Toast.makeText(getApplicationContext(), list.get(position).get(SIXTH_COLUMN) + " 장바구니 빼기", Toast.LENGTH_SHORT).show();

                    parent.getChildAt(position).setEnabled(true);
                    dbHelper.SotBeaconInfoItemBasketN(list.get(position).get(FIRST_COLUMN), list.get(position).get(EIGHTH_COLUMN));     // DB BASKET_YN 컬럼 update
                    Log.v("tempLog  :  ", "basketYShopItemSelect : " + dbHelper.basketYShopItemSelect());
                    TextView tv = (TextView) view.findViewById(R.id.basketYn);
                    tv.setText("N");
                }

                //position은 sequence로서 0부터 시작한다.
                //아이템 단위의 리스트를 클릭했을경우, 가게이름이 같은 마커를 select한 효과주기
                String placeName = list.get(position).get(SECOND_COLUMN);
                for(i=0; i<markers.size(); i++) {
                    if(placeName.equals(markers.get(i).getItemName()))
                        mMapView.selectPOIItem(markers.get(i), true);
                }
            }
        });


        //리스트 클릭된 녀석들에 대해 BC_NO, item_nm 정보를 저장하고 있다가, 이 버튼을 누르면, 기존 ITEM테이블의 BASKET_YN 모두 N으로 바꾸고, 저장된 BC_NO에 대해 BASKET_YN Y로 변경
        /* Button Part */
        Button  wishListBtn = (Button) findViewById(R.id.goToWishList);
        wishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 장바구니 화면 intent 연결
                container.removeView(mMapView); //지도끄고 이동한다.
                Intent  intent = new Intent(view.getContext(), WishList.class); //나중에 추가되면 변경할 것
                startActivity(intent);
            }
        });
    }

    /* POI EventListener Method */
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        //눌렸을때 발생되는 기본적인 동작
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //말풍선 클릭하였을 시의 상황
        Intent intent = new Intent(this, PlaceDetailActivity.class); //나중에 추가되면 변경할 것
        intent.putExtra("strBeaconNo",list.get((Integer)mapPOIItem.getUserObject()).get(FIRST_COLUMN).toString());
        //가게 상세 페이지로의 전환을 위해, BC_NO 정보를 intent에 담아서 보낸다.
        startActivity(intent);
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    /* POI EventListener Method */

    //뒤로가기를 누르면, 새로 activity 시작하도록

    @Override
    public void onBackPressed() {
        //container.removeView(mMapView); //지도끄고 이동한다.
        Intent  intent = new Intent(this, DongmoonStart.class); //나중에 추가되면 변경할 것
        startActivity(intent);
    }

}
