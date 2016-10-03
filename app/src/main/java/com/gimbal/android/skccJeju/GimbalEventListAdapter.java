/**
 * Copyright (C) 2015 Gimbal, Inc. All rights reserved.
 * <p/>
 * This software is the confidential and proprietary information of Gimbal, Inc.
 * <p/>
 * The following sample code illustrates various aspects of the Gimbal SDK.
 * <p/>
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gimbal.android.skccJeju.GimbalEvent.TYPE;

public class GimbalEventListAdapter extends BaseAdapter {
    private Activity activity;
    private List<GimbalEvent> events = new ArrayList<GimbalEvent>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy, hh:mm:ss a", Locale.getDefault());

    public GimbalEventListAdapter(Activity activity) {
        Log.v("tempLog  :  ", "GimbalEventListAdapter Instance!  ");
        this.activity = activity;
    }

    public void setEvents(List<GimbalEvent> events) {
        this.events.clear();
        this.events.addAll(events);
        notifyDataSetChanged();
    }

    public void addEvent(GimbalEvent event) {
        events.add(event);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GimbalEvent event = events.get(position);
        View view = convertView;
        if (view == null) {
            view = activity.getLayoutInflater().inflate(R.layout.list_item, null);
        }

        // TODO: Use wrapper
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        Integer iconRes = iconRes(event.getType());
        if (iconRes != null) {
            icon.setImageResource(iconRes);
        } else {
            icon.setImageDrawable(null);
        }

        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(event.getType().toString());

        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(event.getTitle());

        TextView subtitle = (TextView) view.findViewById(R.id.date);
        subtitle.setText(dateFormat.format(event.getDate()));

        TextView url = (TextView) view.findViewById(R.id.url);
        url.setText(event.getUrl());

        TextView latitude = (TextView) view.findViewById(R.id.latitude);
        latitude.setText(event.getLatitude().toString());

        TextView longitude = (TextView) view.findViewById(R.id.longitude);
        longitude.setText(event.getLongitude());

        TextView beaconNo = (TextView) view.findViewById(R.id.beaconNo);
        beaconNo.setText(event.getBeaconNo());

        TextView place = (TextView) view.findViewById(R.id.place);
        place.setText(event.getPlace());

        TextView placeEvent = (TextView) view.findViewById(R.id.placeEvent);
        placeEvent.setText(event.getEvent());


        return view;
    }

    private int iconRes(TYPE type) {
        switch (type) {
            case PLACE_ENTER:
                return R.drawable.place_enter;
            case PLACE_EXIT:
                return R.drawable.place_exit;
            case COMMUNICATION_PRESENTED:
                return R.drawable.comm_presented;
            case COMMUNICATION_ENTER:
                return R.drawable.comm_enter;
            case COMMUNICATION_EXIT:
                return R.drawable.comm_exit;
            case COMMUNICATION_INSTANT_PUSH:
                return R.drawable.comm_presented;
            case COMMUNICATION_TIME_PUSH:
                return R.drawable.comm_presented;
            case NOTIFICATION_CLICKED:
                return R.drawable.comm_enter;
            case EXPERIENCE_ACTION_DELIVERED:
                return R.drawable.exp_action_delivered;
            case EXPERIENCE_FRAGMENT_PRESENTED:
                return R.drawable.exp_fragment_presented;
            case EXPERIENCE_NOTIFICATION_PREPARED:
                return R.drawable.exp_notification_prepared;
            case EXPERIENCE_NOTIFICATION_CLICKED:
                return R.drawable.exp_notification_clicked;
            default:
                return R.drawable.place_enter;
        }
    }

}
