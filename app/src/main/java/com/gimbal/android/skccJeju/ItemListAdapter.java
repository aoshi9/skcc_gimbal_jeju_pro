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


import static com.gimbal.android.skccJeju.Constant.FIRST_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SECOND_COLUMN;
import static com.gimbal.android.skccJeju.Constant.THIRD_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FOURTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.FIFTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SIXTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.SEVENTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.EIGHTH_COLUMN;
import static com.gimbal.android.skccJeju.Constant.NINTH_COLUMN;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.gimbal.android.skccJeju.GimbalEvent.TYPE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ItemListAdapter extends BaseAdapter {

    public ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private Context ctx;
    private Typeface tf;


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
    public long getItemId(int position) { return position;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if(convertView == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_info_list, null);

            holder = new ViewHolder();
            holder.bcNo = (TextView) convertView.findViewById((R.id.bc_no));
            holder.placeName = (TextView) convertView.findViewById(R.id.placeName);
            holder.latitude = (TextView) convertView.findViewById(R.id.latitude);
            holder.longitude = (TextView) convertView.findViewById(R.id.longitude);
            holder.url = (TextView) convertView.findViewById(R.id.url);
            holder.itemName = (TextView) convertView.findViewById(R.id.itemName);
            holder.itemPrice = (TextView) convertView.findViewById(R.id.itemPrice);
            holder.itemNo = (TextView) convertView.findViewById(R.id.item_no);
            holder.basketYn = (TextView) convertView.findViewById(R.id.basketYn);
            //add the holder as a tag to the convertView

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> map = list.get(position);
        holder.bcNo.setText(map.get(FIRST_COLUMN));
        holder.placeName.setText(map.get(SECOND_COLUMN));
        holder.latitude.setText(map.get(THIRD_COLUMN));
        holder.longitude.setText(map.get(FOURTH_COLUMN));
        holder.url.setText(map.get(FIFTH_COLUMN));
        holder.itemName.setText(map.get(SIXTH_COLUMN));
        holder.itemPrice.setText(map.get(SEVENTH_COLUMN));
        holder.itemNo.setText(map.get(EIGHTH_COLUMN));
        holder.basketYn.setText(map.get(NINTH_COLUMN));

        holder.bcNo.setTypeface(tf);
        holder.bcNo.setTextColor(Color.parseColor("#000000"));
        holder.placeName.setTypeface(tf);
        holder.placeName.setTextColor(Color.parseColor("#000000"));
        holder.latitude.setTypeface(tf);
        holder.latitude.setTextColor(Color.parseColor("#000000"));
        holder.longitude.setTypeface(tf);
        holder.longitude.setTextColor(Color.parseColor("#000000"));
        holder.url.setTypeface(tf);
        holder.url.setTextColor(Color.parseColor("#000000"));
        holder.itemName.setTypeface(tf);
        holder.itemName.setTextColor(Color.parseColor("#000000"));
        holder.itemPrice.setTypeface(tf);
        holder.itemPrice.setTextColor(Color.parseColor("#000000"));
        holder.itemNo.setTypeface(tf);
        holder.itemNo.setTextColor(Color.parseColor("#000000"));
        holder.basketYn.setTypeface(tf);
        if(map.get(NINTH_COLUMN).equalsIgnoreCase("Y"))
        {
            holder.basketYn.setTextColor(Color.parseColor("#EC6652"));
            holder.basketYn.setText("V");
        }
        else {
            holder.basketYn.setText(" ");
        }
        return convertView;
    }

    private class ViewHolder {
        TextView placeName;
        TextView latitude;
        TextView longitude;
        TextView url;
        TextView itemName;
        TextView itemPrice;
        TextView bcNo;
        TextView itemNo;
        TextView basketYn;
    }
}