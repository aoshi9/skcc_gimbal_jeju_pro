package com.gimbal.android.skccJeju;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;

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
    private ArrayList<HashMap<String, String>> list;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /* ListView part */
        ListView listView=(ListView)findViewById(R.id.listView1);
        list = new ArrayList<HashMap<String,String>>();

        Intent intent = getIntent();
        String beaconNo = intent.getStringExtra("strBeaconNo");
        Log.v("tempLog : strBeaconNo ", beaconNo);
        //String beaconNo = "BC001";

         /* DataBase Part */
        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        /*dbHelper.SotBeaconInfoInsert("BC001", "1", "SK수산", 33.512871433920715, 126.52806210319818, "http://", "금주 행사는 ", "event_img1", "02-5445-5555", "제주도 제주시 일도2동 345번지", "1");
        dbHelper.SotBeaconInfoInsert("BC002", "1", "SK과일", 33.512849093102645, 126.52870580659532, "http://", "테스트 2 행상", "event_img2", "02-5445-5544", "제주도 제주시 일도2동 344번지", "1");
        dbHelper.SotBeaconInfoItemInsert("BC001", "BC001_01", "광어", "12,000원", "자연산", "1+1");
        dbHelper.SotBeaconInfoItemInsert("BC001", "BC001_02", "자연산 광어", "10,000원", "자연산 싱싱한 광어", "싱싱한 자연산만...");
        dbHelper.SotBeaconInfoItemInsert("BC002", "BC002_01", "천혜향", "10kg 3만원", "맛있는 천혜향", "반값 이벤트");
        dbHelper.SotBeaconInfoItemInsert("BC002", "BC002_02", "남원 한라봉", "10kg 2만원", "제주 남원에서 직접 수확한 한라봉", "싱싱한 한라봉...");*/

        String resultString = dbHelper.shopItemSelectByBeaconNo(beaconNo); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
        String[] resultArray = resultString.split("!");

        Log.v("tempLog  :  ", "dbHelper.shopItemSelectByBeaconNo:  " + dbHelper.shopItemSelectByBeaconNo(beaconNo));
        Log.v("tempLog  :  ", "resultString.resultString:  " + resultString);

        String title = resultArray[1];
        URL = resultArray[4];
        String place = resultArray[1];
        String placeEvent = resultArray[5];
        String placeImg = resultArray[6];
        String telNo = resultArray[7];
        String addr = resultArray[8];

        String resultString1 = dbHelper.placeItemInfoSelect(beaconNo); //이전의 intent에서 넘어와야 되지만 일단은 하드코딩
        String[] resultArray1 = resultString1.split("!");

        for(int i=0; i<resultArray1.length/6; i++) {
            HashMap<String,String> dataMap = new HashMap<String, String>();
            dataMap.put(FIRST_COLUMN, resultArray1[6*i]);
            dataMap.put(SECOND_COLUMN, resultArray1[6*i+1]);
            dataMap.put(THIRD_COLUMN, resultArray1[6*i+2]);
            dataMap.put(FOURTH_COLUMN, resultArray1[6*i+3]);
            dataMap.put(FIFTH_COLUMN, resultArray1[6*i+4]);
            dataMap.put(SIXTH_COLUMN, resultArray1[6*i+5]);

            list.add(dataMap);
        }

        //Log.v("HoyoungLog  :  ", "List Size : " + list.size());
        ItemList2Adapter adapter = new ItemList2Adapter(this, list,this.getApplicationContext());
        listView.setAdapter(adapter);

        //font 설정
        Typeface typeface = Typeface.createFromAsset(getAssets(),"BMJUA_ttf.ttf");

//        TextView placeName = (TextView) findViewById(R.id.placeName);
//        placeName.setText(place);
//        placeName.setTypeface(typeface);

//        TextView addrText = (TextView) findViewById(R.id.addr);
//        addrText.setTypeface(typeface);
//        addrText.setText("주소 "+ "\n" + addr);
//
//        TextView telNoText = (TextView) findViewById(R.id.telNo);
//        telNoText.setText("전화번호 " + "\n" + telNo);
//        telNoText.setTypeface(typeface);
//        TextView placeEventText = (TextView) findViewById(R.id.placeEvent);
//        placeEventText.setText("이벤트 :  "  + placeEvent);
//        placeEventText.setTypeface(typeface);


        ImageView eventImg = (ImageView) findViewById(R.id.eventImg);
        eventImg.setImageResource(getResources().getIdentifier(placeImg, "drawable", getPackageName()));
        //eventImg.setImageResource(R.drawable.market1);

        imageView = (ImageView) findViewById(R.id.eventImg);

        eventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(URL));
                startActivity(intent);

            }
        });

    }



}
