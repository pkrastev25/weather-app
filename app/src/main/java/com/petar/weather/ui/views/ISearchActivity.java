package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.List;

/**
 * Created by User on 27.8.2017 г..
 */

public interface ISearchActivity extends MvpLceView<List<? extends AListenerRecyclerItem>> {

    void showMessage(String message);
}
