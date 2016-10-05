package com.gimbal.android.skccJeju;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gimbal.android.CommunicationManager;
import com.gimbal.android.Gimbal;
import static com.gimbal.android.skccJeju.Constant.FLAG;

public class MainActivity extends AppCompatActivity {
    LocationPermissions permissions;
    private BackPressCloseHandler backPressCloseHandler;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);
        //startActivity(new Intent(this, Splash.class));

        dbHelper = new DBHelper(this.getApplicationContext(), "Gimbal.db", null, 1);

        // 최초 한번만 수행되도록, DB에 데이터가 하나도 없을때만 수행되도록 함 (최준형)
        if(dbHelper.shopItemSelectByPlaceSeCd("1").equals("")) {
            //시연을 위한 초기 데이터 적재 (최준형)
            dbHelper.SotBeaconInfoInsertOrReplace("BC001", "1", "SK수산", 33.512871433920715, 126.52806210319818, "http://jejudongmun.modoo.at", "마라도 방어 한마리 만원", "event_img1", "02-5445-5555", "제주도 제주시 일도2동 345번지", "1");
            dbHelper.SotBeaconInfoInsertOrReplace("BC002", "1", "SK과일", 33.512849093102645, 126.52870580659532, "http://jejudongmun.modoo.at", "서귀포 한라봉 특가", "event_img2", "02-5445-5544", "제주도 제주시 일도2동 344번지", "1");
            dbHelper.SotBeaconInfoInsertOrReplace("BC003", "1", "SK건어물", 33.5127645823235, 126.52819621356707, "http://jejudongmun.modoo.at", "한치 엄청싸게 팝니다.", "event_img3", "02-5445-5555", "제주도 제주시 일도2동 344번지", "1");
            dbHelper.SotBeaconInfoInsertOrReplace("BC004", "1", "스크수산", 33.512737276693, 126.52858365901943, "http://jejudongmun.modoo.at", "옥돔 1+1  Event", "event_img4", "02-5445-5222", "제주도 제주시 일도2동 325번지", "1");
            dbHelper.SotBeaconInfoInsertOrReplace("BC005", "1", "SK분식", 33.512712364111394, 126.52851220460457, "http://jejudongmun.modoo.at", "제주만의 사랑식", "event_img5", "02-5445-3333", "제주도 제주시 일도2동 325번지", "1");

            dbHelper.SotBeaconInfoItemInsertOrReplace("BC001", "BC001_01", "방어", "2,000원", "마라도산", "1+1특가");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC001", "BC001_02", "자연산 광어", "10,000원", "자연산 싱싱한 광어", "싱싱한 자연산만...");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC002", "BC002_01", "천혜향", "10kg 3만원", "맛있는 천혜향", "반값 이벤트");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC002", "BC002_02", "남원 한라봉", "10kg 2만원", "제주 남원에서 직접 수확한 한라봉", "싱싱한 한라봉....");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC003", "BC003_01", "한치", "20마리 2만원", "제주산 한치", "5마리 추가");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC003", "BC003_02", "옥돔", "10마리 4만원", "제주산 옥돔", "2마리 추가");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC004", "BC004_01", "옥돔", "한마리 10,000원", "제주특산물 옥돔", "오늘만 옥돔 1+1");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC004", "BC004_02", "우럭", "1KG 10,000원", "싱싱한 자연산", "한마리 오늘만 만원");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC005", "BC005_01", "김떡순", "5천원", "김밥,떡볶이,순대", "오뎅 써비스~!~");
            dbHelper.SotBeaconInfoItemInsertOrReplace("BC005", "BC005_02", "국물떡볶이", "3000원", "SK분식만의 궁물떡볶이", "오뎅 써비스~!~");
        }

        Gimbal.setApiKey(this.getApplication(), "8cbce2c7-6e27-419c-9be1-7b01f1d990e4");
        startService(new Intent(this, AppService.class));

        backPressCloseHandler = new BackPressCloseHandler(this);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        GimbalDAO.setOptInShown(getApplicationContext());
        enablePlaceMonitoring();
        enableCommunications();

        //상단알림 클릭시 화면 이동
        //TODO :비콘별 이동 화면을 다르게 갖어 갈 수 있음(Attributes 활용(Communication )
        Intent intent_noti = getIntent();
        String strAction = intent_noti.getStringExtra("strAction");
        Log.v("tempLog  :  ", "strAction.intent_noti_strAction :  "  +strAction);

        if(strAction != null && strAction.equals("NotificationClicked")){

            String strBeaconNo = intent_noti.getStringExtra("strBeaconNo");
            if(strBeaconNo.equals("BC006_IN")){
                Toast.makeText(getApplicationContext(), "[비상] 본부장님이 접근 알림",
                        Toast.LENGTH_LONG).show();
            }else if(strBeaconNo.equals("BC006_OUT")) {
                Toast.makeText(getApplicationContext(), "[비상해제] 본부장님 접근해제 알림",
                        Toast.LENGTH_LONG).show();
            }else {
                Intent intent_move = new Intent(this, PlaceDetailActivity.class);
                intent_move.putExtra("strBeaconNo", strBeaconNo);
                startActivity(intent_move);
            }
        }




//        final ImageButton imgbtn1 = (ImageButton) findViewById(R.id.mainImg1);
//        final ImageButton imgbtn2 = (ImageButton) findViewById(R.id.mainImg2);
//
//        imgbtn1.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()== MotionEvent.ACTION_BUTTON_PRESS){
//                    imgbtn1.setVisibility(View.INVISIBLE);
//                    imgbtn2.setVisibility(View.VISIBLE);
//                }else if(event.getAction()== MotionEvent.ACTION_BUTTON_PRESS){
//                    imgbtn1.setVisibility(View.VISIBLE);
//                    imgbtn2.setVisibility(View.INVISIBLE);
//                }
//                return false;
//            }
//        });
    }

    private void enablePlaceMonitoring() {
        permissions = new LocationPermissions(this);
        permissions.checkAndRequestPermission();
    }

    private void enableCommunications() {
        CommunicationManager.getInstance().startReceivingCommunications();
        PushRegistrationHelper.registerForPush();
    }


    public void onStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "비콘 이력 접속",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
    }

    public void onAppStartBtnClick(View view) {
        Toast.makeText(getApplicationContext(), "시장하쥬? Start",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AppMenuActivity.class);
        startActivity(intent);
    }


    @Override //뒤도가기 누르면 종료 알림창 뜨도록
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
