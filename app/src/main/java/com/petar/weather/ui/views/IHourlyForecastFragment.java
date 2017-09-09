package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.List;

/**
 * Created by User on 1.7.2017 г..
 */

public interface IHourlyForecastFragment extends MvpLceView<List<? extends AListenerRecyclerItem>> {

    void showMessage(String message);

    void addNextForecast(List<? extends AListenerRecyclerItem> data);

    void scrollToCurrentForecast();

    void setLoadingRecyclerItem();

    void removeLoadingRecyclerItem();
}
