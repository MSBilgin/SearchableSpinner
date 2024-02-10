package com.msbilgin.searchablespinnerlib;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.List;

public class SearchableSpinner extends AppCompatSpinner {
    private AlertDialog alertDialog;

    private String closeButtonText = "CLOSE";

    private String title = "TITLE";

    private DropdownAdaptor dropdownAdaptor;

    public SearchableSpinner(@NonNull Context context) {
        super(context);
    }

    public SearchableSpinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (alertDialog == null || !alertDialog.isShowing()) {
            LayoutInflater layoutInflater = ((AppCompatActivity) this.getContext()).getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.searchable_list_dialog, null);
            alertDialog = new AlertDialog.Builder(this.getContext())
                    .setTitle(title)
                    .setView(view)
                    .setPositiveButton(closeButtonText, (dialogInterface, i) -> alertDialog.dismiss())
                    .create();


            ListView listView = view.findViewById(R.id.listView);
            listView.setAdapter(dropdownAdaptor);
            listView.setOnItemClickListener((adapterView, view1, i, l) -> {
                String str = ((TextView) view1).getText().toString();
                for (int j = 0; j < getAdapter().getCount(); j++) {
                    if (str.equals(getAdapter().getItem(j).toString())) {
                        setSelection(j);
                    }
                }
                alertDialog.dismiss();
                dropdownAdaptor.getFilter().filter(null);
            });

            EditText editText = view.findViewById(R.id.editText);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    CharSequence cs = TextUtils.isEmpty(charSequence) ? null : charSequence;
                    dropdownAdaptor.getFilter().filter(cs);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            alertDialog.show();

        }
        return true;
    }

    public void setCloseButtonText(String s) {
        this.closeButtonText = s;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        super.setAdapter(adapter);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < adapter.getCount(); i++) {
            list.add(adapter.getItem(i).toString());
        }

        this.dropdownAdaptor = new DropdownAdaptor(getContext(), list);
    }
}