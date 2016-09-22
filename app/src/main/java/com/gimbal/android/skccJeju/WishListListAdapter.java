package com.gimbal.android.skccJeju;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.gimbal.android.skccJeju.Constant.EIGHTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.NINTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;

/**
 * Created by 호영 on 2016-09-22.
 */
public class WishListListAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    private Activity activity;
    TextView placeName;
    TextView latitude;
    TextView longitude;
    TextView url;
    TextView itemName;
    TextView itemPrice;
    TextView bcNo;
    TextView itemNo;

    public WishListListAdapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.wishlist_item_info, null);

            bcNo = (TextView) convertView.findViewById((R.id.bc_no));
            placeName = (TextView) convertView.findViewById(R.id.placeName);
            latitude = (TextView) convertView.findViewById(R.id.latitude);
            longitude = (TextView) convertView.findViewById(R.id.longitude);
            url = (TextView) convertView.findViewById(R.id.url);
            itemName = (TextView) convertView.findViewById(R.id.itemName);
            itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            itemNo = (TextView) convertView.findViewById(R.id.item_no);
        }

        HashMap<String, String> map = list.get(position);
        bcNo.setText(map.get(FIRST_COLUMN));
        placeName.setText(map.get(SECOND_COLUMN));
        latitude.setText(map.get(THIRD_COLUMN));
        longitude.setText(map.get(FOURTH_COLUMN));
        url.setText(map.get(FIFTH_COLUMN));
        itemName.setText(map.get(SIXTH_COLUMN));
        itemPrice.setText(map.get(SEVENTH_COLUMN));
        itemNo.setText(map.get(EIGHTH_COLUMN));

        return convertView;
    }
}
