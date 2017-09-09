package com.petar.weather.ui.activities;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableParcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastDetailsBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.app.Constants;

public class ForecastDetailsActivity extends AppCompatActivity {

    private ObservableParcelable<AForecast> mForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityForecastDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast_details);
        binding.setView(this);

        mForecast = new ObservableParcelable<>();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            AForecast forecast = bundle.getParcelable(Constants.BUNDLE_FORECAST_DETAILS_KEY);
            mForecast.set(forecast);
        }
    }

    public ObservableField<AForecast> getForecast() {
        return mForecast;
    }
}
