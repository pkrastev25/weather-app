package com.petar.weather.ui.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.petar.weather.R;
import com.petar.weather.app.Constants;
import com.petar.weather.databinding.FragmentSettingsBinding;
import com.petar.weather.util.AlarmManagerUtil;
import com.petar.weather.util.TextUtil;

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

    private SwitchCompat mToggleButton;

    // --------------------------------------------------------
    // GENERAL-ACTIVITY region
    // --------------------------------------------------------

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
         * This transforms a Fragment into a “RetainingFragment” which means only
         * the Fragment’s GUI (the android.view.View returned from onCreateView())
         * get’s destroyed an newly created but all referenced objects (like ViewState)
         * will still be there after screen orientation changes.
         */
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        binding.setView(this);
        mToggleButton = binding.notificationsToggleButton;

        TextUtil.linkifyText(
                binding.creditsApiTextView,
                getString(R.string.settings_credits_api_text),
                Constants.CREDITS_API_PROVIDER,
                Constants.CREDITS_API_PROVIDER_URL
        );

        TextUtil.linkifyText(
                binding.creditsAppIconTextView,
                getString(R.string.settings_credits_app_icon_text),
                Constants.CREDITS_APP_ICON_PROVIDER,
                Constants.CREDITS_APP_ICON_PROVIDER_URL
        );

        TextUtil.linkifyText(
                binding.licenseTextView,
                TextUtil.convertHTMLToText(getString(R.string.license_html))
        );

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

        if (mToggleButton != null) {
            mToggleButton.setOnCheckedChangeListener(null);
        }

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
