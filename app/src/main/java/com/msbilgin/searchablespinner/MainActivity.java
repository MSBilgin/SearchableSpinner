package com.msbilgin.searchablespinner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.msbilgin.searchablespinnerlib.SearchableSpinner;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchableSpinner searchableSpinner = findViewById(R.id.searchableSpinner);
        searchableSpinner.setTitle("BAŞLIK");
        searchableSpinner.setCloseButtonText("KAPAT");

        List<String> values = Arrays.asList("mehmet selim", "selimbilgin", "bilgin");
        SpinnerAdapter spinnerAdaptor = new SpinnerAdapter(this, values);
        searchableSpinner.setAdapter(spinnerAdaptor);
    }


}