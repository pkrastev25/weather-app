package com.petar.weather.ui.views;

import android.databinding.ObservableField;
import android.widget.SearchView;

/**
 * Created by User on 8.7.2017 г..
 */

public interface IToolbarView extends SearchView.OnQueryTextListener,
        SearchView.OnClickListener,
        SearchView.OnCloseListener {

    ObservableField<String> getCurrentLocation();

    void onCurrentLocationClick();
}
