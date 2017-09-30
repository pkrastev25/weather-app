package com.petar.weather.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.databinding.FragmentSettingsBinding;
import com.petar.weather.util.AlarmManagerUtil;

/**
 * A {@link Fragment} used to display the settings for this application.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public class SettingsFragment
        extends Fragment
        implements CompoundButton.OnCheckedChangeListener {

    private ToggleButton mToggleButton;

    // --------------------------------------------------------
    // GENERAL-ACTIVITY region
    // --------------------------------------------------------

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        binding.setView(this);
        mToggleButton = binding.notificationsToggleButton;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences preferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        boolean areNotificationsEnabled = preferences.getBoolean(Constants.SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY, false);

        mToggleButton.setChecked(areNotificationsEnabled);
        mToggleButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mToggleButton.setOnCheckedChangeListener(null);
        mToggleButton = null;
    }

    // --------------------------------------------------------
    // End of GENERAL-ACTIVITY region
    // --------------------------------------------------------

    // --------------------------------------------------------
    // TOGGLE-BUTTON region
    // --------------------------------------------------------

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SharedPreferences preferences = getContext().getSharedPreferences(getContext().getPackageName(), Context.MODE_PRIVATE);
        preferences.edit().putBoolean(Constants.SHARED_PREFERENCES_NOTIFICATIONS_ACTIVE_KEY, isChecked).apply();

        if (isChecked) {
            AlarmManagerUtil.startAlarm(getContext());
        } else {
            AlarmManagerUtil.stopAlarm(getContext());
        }
    }

    // --------------------------------------------------------
    // End of TOGGLE-BUTTON region
    // --------------------------------------------------------
}
