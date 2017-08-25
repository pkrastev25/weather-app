package com.petar.weather.ui.views;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.util.Constants;

/**
 * Created by User on 20.8.2017 г..
 */

public class ForecastLoadingRecyclerItem extends AListenerRecyclerItem {

    @Override
    public int getViewType() {
        return Constants.FORECAST_LOADING_RECYCLER_ITEM;
    }
}
