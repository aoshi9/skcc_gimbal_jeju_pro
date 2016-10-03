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


import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
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

public class ItemListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private Context ctx;
    private Typeface tf;
    TextView placeName;
    TextView latitude;
    TextView longitude;
    TextView url;
    TextView itemName;
    TextView itemPrice;
    TextView bcNo;
    TextView itemNo;
    TextView basketYn;


    public ItemListAdapter(Activity activity, ArrayList<HashMap<String, String>> list, Context ctx) {
        super();
        this.activity = activity;
        this.list = list;
        this.ctx = ctx;
        this.tf = Typeface.createFromAsset(this.ctx.getAssets(),"BMJUA_ttf.ttf");
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int position) {return list.get(position);}

    @Override
    public long getItemId(int position) { return 0;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.item_info_list, null);

            bcNo = (TextView) convertView.findViewById((R.id.bc_no));
            placeName = (TextView) convertView.findViewById(R.id.placeName);
            latitude = (TextView) convertView.findViewById(R.id.latitude);
            longitude = (TextView) convertView.findViewById(R.id.longitude);
            url = (TextView) convertView.findViewById(R.id.url);
            itemName = (TextView) convertView.findViewById(R.id.itemName);
            itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            itemNo = (TextView) convertView.findViewById(R.id.item_no);
            basketYn = (TextView) convertView.findViewById(R.id.basketYn);
        }

        HashMap<String, String> map = list.get(position);
        bcNo.setText(map.get(FIRST_COLUMN));
        bcNo.setTypeface(tf);
        placeName.setText(map.get(SECOND_COLUMN));
        latitude.setText(map.get(THIRD_COLUMN));
        longitude.setText(map.get(FOURTH_COLUMN));
        url.setText(map.get(FIFTH_COLUMN));
        itemName.setText(map.get(SIXTH_COLUMN));
        itemPrice.setText(map.get(SEVENTH_COLUMN));
        itemNo.setText(map.get(EIGHTH_COLUMN));
        basketYn.setText(map.get(NINTH_COLUMN));

//        font set Test
        bcNo.setTypeface(tf);
        placeName.setTypeface(tf);
        latitude.setTypeface(tf);
        longitude.setTypeface(tf);
        url.setTypeface(tf);
        itemName.setTypeface(tf);
        itemPrice.setTypeface(tf);
        itemNo.setTypeface(tf);
        basketYn.setTypeface(tf);
//fonts set Test

        return convertView;
    }
}