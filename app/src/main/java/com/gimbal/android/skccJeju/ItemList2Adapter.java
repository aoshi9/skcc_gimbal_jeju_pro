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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;

public class ItemList2Adapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    private Activity activity;
    TextView itemName;
    TextView itemNo;
    TextView itemPrice;
    TextView bcNo;
    TextView itemDesc;
    TextView itemEvent;

    public ItemList2Adapter(Activity activity, ArrayList<HashMap<String, String>> list) {
        super();
        this.activity = activity;
        this.list = list;
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
            convertView = inflater.inflate(R.layout.list_item2, null);

            bcNo = (TextView) convertView.findViewById((R.id.bc_no));
            itemNo = (TextView) convertView.findViewById(R.id.itemNo);
            itemName = (TextView) convertView.findViewById(R.id.itemName);
            itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            itemDesc = (TextView) convertView.findViewById(R.id.itemDesc);
            itemEvent = (TextView) convertView.findViewById(R.id.itemEvent);
        }

        HashMap<String, String> map = list.get(position);
        bcNo.setText(map.get(FIRST_COLUMN));
        itemNo.setText(map.get(SECOND_COLUMN));
        itemName.setText(map.get(THIRD_COLUMN));
        itemPrice.setText(map.get(FOURTH_COLUMN));
        itemDesc.setText(map.get(FIFTH_COLUMN));
        itemEvent.setText(map.get(SIXTH_COLUMN));

        return convertView;
    }
}