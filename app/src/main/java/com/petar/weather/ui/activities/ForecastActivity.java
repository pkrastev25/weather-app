package com.petar.weather.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;

public class ForecastActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ActivityForecastBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setView(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("woId", -1);
    }
}
