package com.petar.weather.ui.views;

import android.databinding.ObservableField;

/**
 * Created by User on 8.7.2017 г..
 */

public interface IToolbarView {

    ObservableField<String> getCurrentLocationTitle();

    void onFindCurrentLocationClick();

    void onCurrentLocationClick();

    void onSearchClick();
}
