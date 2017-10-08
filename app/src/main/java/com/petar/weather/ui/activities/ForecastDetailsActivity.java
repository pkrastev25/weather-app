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
import com.petar.weather.logic.models.ALocation;

/**
 * Displays in-depth information about the given forecast.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 9.7.2017
 */
public class ForecastDetailsActivity extends AppCompatActivity {

    private ObservableParcelable<AForecast> mForecast;
    private ObservableParcelable<ALocation> mLocation;

    // --------------------------------------------------------
    // GENERAL-ACTIVITY region
    // --------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityForecastDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast_details);
        binding.setView(this);

        mForecast = new ObservableParcelable<>();
        mLocation = new ObservableParcelable<>();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            AForecast forecast = bundle.getParcelable(Constants.BUNDLE_FORECAST_DETAILS_KEY);
            mForecast.set(forecast);

            ALocation location = bundle.getParcelable(Constants.BUNDLE_LOCATION_DETAILS_KEY);
            mLocation.set(location);
        }
    }

    public ObservableField<AForecast> getForecast() {
        return mForecast;
    }

    public ObservableParcelable<ALocation> getLocation() {
        return mLocation;
    }

    // --------------------------------------------------------
    // End of GENERAL-ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // TOOLBAR region
    // --------------------------------------------------------

    /**
     * Called when the user interacts with the "x" icon. Navigates to the
     * {@link ForecastActivity}.
     */
    public void onExit() {
        onBackPressed();
    }

    // --------------------------------------------------------
    // End of TOOLBAR region
    // --------------------------------------------------------
}
