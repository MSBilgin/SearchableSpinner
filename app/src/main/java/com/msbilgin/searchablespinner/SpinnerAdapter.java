package com.msbilgin.searchablespinner;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private final List<String> list;
    private final Context context;
    private final static Object LOCK = new Object();

    public SpinnerAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText(getItem(position));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(20);
        return textView;
    }
}

