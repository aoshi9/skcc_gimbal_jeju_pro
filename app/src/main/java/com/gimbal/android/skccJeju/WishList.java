package com.gimbal.android.skccJeju;

import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;    //BC_NO
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;   //BC_PLACE_NAME
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;    //LATITUDE
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;   //LONGITUDE
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;    //URL
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;    //ITEM_NM
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;  //ITEM_PRICE
import static com.gimbal.android.skccJeju.Constant.EIGHTH_COLUMN;  //ITEM_NO

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
import android.widget.Toast;

import com.gimbal.android.Beacon;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.Comparator;
import java.util.List;


/**
 * Created by min on 2016-05-05.
 */
public class WishList extends AppCompatActivity implements MapView.POIItemEventListener {
    Integer i=0;
    private MapView mMapView;
    private String URL;
    private MapPOIItem marker;
    private MapPoint MARKER_POINT;
    private ArrayList<HashMap<String, String>> list;
    private DBHelper dbHelper;
    private static double pLatitude =33.5129772790485;
    private static double pLongitude =126.52796675053673;

    private  MapPoint MyLocation = MapPoint.mapPointWithGeoCoord(pLatitude, pLongitude); // GPS에서 받아와야 정상이지만, 제주도이므로 하드코딩
    private ArrayList<MapPOIItem> markers = new ArrayList<MapPOIItem>();
    private RelativeLayout container;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


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


        /* ListView part */
        ListView listView=(ListView)findViewById(R.id.listView1);
        list = new ArrayList<HashMap<String,String>>();

        /* DataBase Part */
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        String resultString = dbHelper.selectedItemInfo("1"); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
        String[] resultArray = resultString.split("!");

        //Log.v("HoyoungLog  :  ", "Data : " + resultArray[0] + ", " + resultArray[1] + ", " + resultArray[2] + ", " + resultArray[3] + ", " + resultArray[4] + ", " + resultArray[5]);
        //Log.v("HoyoungLog  :  ", "Data : " + resultArray[6] + ", " + resultArray[7] + ", " + resultArray[8] + ", " + resultArray[9] + ", " + resultArray[10] + ", " + resultArray[11]);
        //BC_NO, BC_PLACE_NM, LATITUDE, LONGITUDE, URL, ITEM_NM, ITEM_PRICE, ITEM_NO
        for(int i=0; i<resultArray.length/8; i++) {
            HashMap<String,String> dataMap = new HashMap<String, String>();
            dataMap.put(FIRST_COLUMN, resultArray[8*i]);
            dataMap.put(SECOND_COLUMN, resultArray[8*i+1]);
            dataMap.put(THIRD_COLUMN, resultArray[8*i+2]);
            dataMap.put(FOURTH_COLUMN, resultArray[8*i+3]);
            dataMap.put(FIFTH_COLUMN, resultArray[8*i+4]);
            dataMap.put(SIXTH_COLUMN, resultArray[8*i+5]);
            dataMap.put(SEVENTH_COLUMN, resultArray[8*i+6]);
            dataMap.put(EIGHTH_COLUMN, resultArray[8*i+7]);


            //거리 계산하기..KMK
            String distance = calcDistance(pLatitude, pLongitude, Double.valueOf(resultArray[8*i+2]), Double.valueOf(resultArray[8*i+3]));
            Log.v("tempLog  :  ", "distance: " +resultArray[8*i] +  " , " + distance);
            //MAP put..KMK
            dataMap.put("DIST", distance);


            list.add(dataMap);
        }

        //DIST로 SORTING..KMK
        MapComparator comp = new MapComparator("DIST");
        Collections.sort(list, comp);



        //Log.v("HoyoungLog  :  ", "List Size : " + list.size());
        WishListListAdapter adapter = new WishListListAdapter(this, list,this.getApplicationContext());
        listView.setAdapter(adapter);


        /* MapView에 ListView Item 올리기 */
        MapPOIItem marker = new MapPOIItem();

        // MapPOIItem형 ArrayList를 ListView 가게갯수에 맞추어 채우기
        for(int i=0; i<list.size(); i++) {
            markers.add(new MapPOIItem());
        }

        //가까운 위치로 선 그리기기..kmk
       MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(128, 255, 51, 0)); // Polyline 컬러 지정.
        polyline.addPoint(MapPoint.mapPointWithGeoCoord(pLatitude, pLongitude));

        //Log.v("HoyoungLog  :  ", "위도경도 : " + list.get(0).get(THIRD_COLUMN) + ", " + list.get(0).get(FOURTH_COLUMN));
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

            //선그리기  ..kmk
            polyline.addPoint(MapPoint.mapPointWithGeoCoord(Double.valueOf(list.get(i).get(THIRD_COLUMN)), Double.valueOf(list.get(i).get(FOURTH_COLUMN))));

            /* 아이템 단위로 넘어오는 LIST에서 중복을 제거하여 가게단위로 만들자 */
            if(i != 0) {
                if(!markers.get(i-1).getItemName().equals(marker.getItemName())) {
                    markers.add(marker);
                }
            } else {
                markers.add(marker);
            }
        }

        //내 위치와 관련된 MapPoiItem 구성
        MapPOIItem Mymarker = new MapPOIItem();
        Mymarker.setItemName("현재위치");
        Mymarker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
        Mymarker.setSelectedMarkerType(MapPOIItem.MarkerType.YellowPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.scale(false);
        Mymarker.setMapPoint(MyLocation);
        MapPOIItem.ShowAnimationType showAnimationType = MapPOIItem.ShowAnimationType.SpringFromGround;
        Mymarker.setShowAnimationType(showAnimationType);
        mMapView.addPOIItem(Mymarker);

        //marker들을 지도에 올린다.
        for(int i=0; i<markers.size(); i++) {
            mMapView.addPOIItem(markers.get(i));
        }

        //선그리기  ..kmk
        mMapView.addPolyline(polyline);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                //position은 sequence로서 0부터 시작한다.
                //아이템 단위의 리스트를 클릭했을경우, 가게이름이 같은 마커를 select한 효과주기
                String placeName = list.get(position).get(SECOND_COLUMN);
                for(i=0; i<markers.size(); i++) {
                    if(placeName.equals(markers.get(i).getItemName()))
                        mMapView.selectPOIItem(markers.get(i), true);
                }
            }
        });

      /*  *//* MapView에 PolyLine을 통해 경로 표시 *//*
        MapPolyline polyline = new MapPolyline();
        polyline.setTag(1000);
        polyline.setLineColor(Color.argb(255, 0, 0, 0));

        *//* PolyLine을 통한 경로 설정 *//*
        polyline.addPoint(Mymarker.getMapPoint()); //내 위치가 시작점

        //내 위치에서 가장 가까운곳, 그곳에서 가장 가까운 곳 순으로 그려가자
        ArrayList<MapPOIItem> unsortedMarkers = new ArrayList<MapPOIItem>();
        ArrayList<MapPOIItem> sortedMarkers = new ArrayList<MapPOIItem>();

        //시작점이 내 위치인 temp_markers
        unsortedMarkers.add(Mymarker);
        for(int i=0; i<markers.size(); i++) {
            unsortedMarkers.add(markers.get(i));
        }

        for(int i=0; i<markers.size(); i++) {
            polyline.addPoint(markers.get(i).getMapPoint());
        }

        // PolyLine 지도에 올리기
        mMapView.addPolyline(polyline);*/
    }

    /* POI EventListener Method */
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
        //눌렸을때 발생되는 기본적인 동작
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //말풍선 클릭하였을 시의 상황
        if(!mapPOIItem.getItemName().equals("현재위치")) {
            Intent intent = new Intent(this, PlaceDetailActivity.class);
            intent.putExtra("strBeaconNo",list.get((Integer)mapPOIItem.getUserObject()).get(FIRST_COLUMN).toString());
            //가게 상세 페이지로의 전환을 위해, BC_NO 정보를 intent에 담아서 보낸다.
            startActivity(intent);
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    /* POI EventListener Method */

    public double distance(double fromX, double fromY, double toX, double toY) {
        return Math.sqrt((fromX - toX)*(fromX - toX) + (fromY - toY)*(fromY - toY));
    }

    //뒤로가기를 누르면, 새로 activity 시작하도록
    @Override
    public void onBackPressed() {
        container.removeView(mMapView); //지도끄고 이동한다.
        Intent  intent = new Intent(this, BeaconMapActivity.class); //나중에 추가되면 변경할 것
        startActivity(intent);
    }

    //거리 계산 ..KMK
    public static String calcDistance(double lat1, double lon1, double lat2, double lon2){
        double EARTH_R, Rad, radLat1, radLat2, radDist;
        double distance, ret;

        EARTH_R = 6371000.0;
        Rad = Math.PI/180;
        radLat1 = Rad * lat1;
        radLat2 = Rad * lat2;
        radDist = Rad * (lon1 - lon2);

        distance = Math.sin(radLat1) * Math.sin(radLat2);
        distance = distance + Math.cos(radLat1) * Math.cos(radLat2) * Math.cos(radDist);
        ret = EARTH_R * Math.acos(distance);
        String result = String.valueOf(ret);

        return result;
    }



    //DIST로 SORTING..KMK
    class MapComparator implements Comparator<HashMap<String, String>> {

        private final String key;

        public MapComparator(String key) {
            this.key = key;
        }

        @Override
        public int compare(HashMap<String, String> first, HashMap<String, String> second) {
            int result = first.get(key).compareTo(second.get(key));
            return result;
        }
    }

    public void onStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "비콘 이력 접속",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

}
