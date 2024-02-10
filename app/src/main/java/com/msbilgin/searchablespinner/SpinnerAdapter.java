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
    private List<String> listFiltered;
    private final Context context;
    private final static Object LOCK = new Object();

    public SpinnerAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        this.context = context;
        this.list = objects;
    }

    @Override
    public int getCount() {
        return listFiltered == null ? list.size() : listFiltered.size();
    }

    @Override
    public String getItem(int position) {
        return listFiltered == null ? list.get(position) : listFiltered.get(position);
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

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

        TextView textView = new TextView(context);
        textView.setLayoutParams(params);
        textView.setText(getItem(position));
        textView.setMinHeight(dp2px(context, 40));
        textView.setTextSize(dp2px(context, 6));
        textView.setGravity(Gravity.LEFT | Gravity.CENTER);
        textView.setPadding(15,0,0,0);
        return textView;
    }

    private static int dp2px(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults results = new FilterResults();

                if (charSequence == null || charSequence.length() == 0) {
                    synchronized (LOCK) {
                        listFiltered = null;
                    }

                    results.values = list;
                    results.count = list.size();
                } else {
                    synchronized (LOCK) {
                        listFiltered = new ArrayList<>();
                    }

                    final String charSequenceString = charSequence.toString().toLowerCase();
                    for (String s : list) {
                        if (s.toLowerCase().contains(charSequenceString)) {
                            listFiltered.add(s);
                        }
                    }

                    results.values = listFiltered;
                    results.count = listFiltered.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if (filterResults.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}

