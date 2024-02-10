package com.msbilgin.searchablespinnerlib;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

class DropdownAdaptor extends ArrayAdapter<String> {
    private final List<String> list;
    private List<String> listFiltered;
    private final Context context;
    private final static Object LOCK = new Object();

    public DropdownAdaptor(Context context, List<String> objects) {
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
        textView.setMinHeight(dp2px(context, 40));
        textView.setTextSize(15);
        textView.setGravity(Gravity.LEFT | Gravity.CENTER);
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

                if (charSequence == null || charSequence.length() == 0) {//boş değer
                    synchronized (LOCK) {
                        listFiltered = null;
                    }

                    results.values = list;
                    results.count = list.size();
                } else {
                    synchronized (LOCK) {
                        listFiltered = new ArrayList<>();
                    }

                    String str = charSequence.toString().trim().toLowerCase();
                    String[] arr = str.split(" ");
                    for (String i : list) {
                        String iLowerCase = i.toLowerCase();
                        boolean allContains = true;

                        for (String j : arr) {
                            if (!iLowerCase.contains(j)) {
                                allContains = false;
                                break;
                            }
                        }

                        if (allContains) {
                            listFiltered.add(i);
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
