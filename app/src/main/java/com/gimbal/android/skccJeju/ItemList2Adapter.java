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
import android.graphics.Color;
import android.graphics.Typeface;
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
    private Context ctx;
    private Typeface tf;

    public ItemList2Adapter(Activity activity, ArrayList<HashMap<String, String>> list,Context ctx) {
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

        ViewHolder holder;
        if(convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.list_item2, null);

            holder = new ViewHolder();
            holder.bcNo = (TextView) convertView.findViewById((R.id.bc_no));
            holder.itemNo = (TextView) convertView.findViewById(R.id.itemNo);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            holder.itemDesc = (TextView) convertView.findViewById(R.id.itemDesc);
            holder.itemEvent = (TextView) convertView.findViewById(R.id.itemEvent);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = list.get(position);
        holder.bcNo.setText(map.get(FIRST_COLUMN));
        holder.itemNo.setText(map.get(SECOND_COLUMN));
        holder.itemName.setText(map.get(THIRD_COLUMN));
        holder.itemPrice.setText(map.get(FOURTH_COLUMN));
        holder.itemDesc.setText(map.get(FIFTH_COLUMN));
        holder.itemEvent.setText(map.get(SIXTH_COLUMN));

        holder.bcNo.setTypeface(tf);
        holder.itemNo.setTypeface(tf);
        holder.itemName.setTypeface(tf);
        holder.itemPrice.setTypeface(tf);
        holder.itemDesc.setTypeface(tf);
        holder.itemEvent.setTypeface(tf);

        holder.bcNo.setTextColor(Color.parseColor("#000000"));
        holder.itemNo.setTextColor(Color.parseColor("#000000"));
        holder.itemName.setTextColor(Color.parseColor("#000000"));
        holder.itemPrice.setTextColor(Color.parseColor("#000000"));
        holder.itemDesc.setTextColor(Color.parseColor("#000000"));
        holder.itemEvent.setTextColor(Color.parseColor("#000000"));

        return convertView;
    }

    private class ViewHolder {
        TextView itemName;
        TextView itemNo;
        TextView itemPrice;
        TextView bcNo;
        TextView itemDesc;
        TextView itemEvent;
    }
}