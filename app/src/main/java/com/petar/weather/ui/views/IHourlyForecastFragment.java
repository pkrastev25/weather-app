package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.List;

/**
 * The mediator between {@link com.petar.weather.ui.fragments.HourlyForecastFragment} and
 * {@link com.petar.weather.presenters.HourlyForecastFragmentPresenter}. All methods here
 * are exposed to the presenter.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 1.7.2017
 */
public interface IHourlyForecastFragment extends MvpLceView<List<? extends AListenerRecyclerItem>> {

    /**
     * Shows a message to the user in the form of a {@link android.widget.Toast}.
     *
     * @param message The message to be shown
     */
    void showToastMessage(String message);

    /**
     * Includes the forecast for the next day to the already existing forecasts
     * within the {@link android.support.v7.widget.RecyclerView}.
     *
     * @param data The forecast for the next day
     */
    void addNextDayForecast(List<? extends AListenerRecyclerItem> data);

    /**
     * Scrolls to the forecast which is most relevant to the current time.
     */
    void scrollToCurrentForecast();

    /**
     * Adds a {@link com.petar.weather.ui.recycler.LoadingRecyclerItem} to the existing
     * items withing the {@link android.support.v7.widget.RecyclerView}.
     */
    void setLoadingRecyclerItem();

    /**
     * Removes a {@link com.petar.weather.ui.recycler.LoadingRecyclerItem} to the existing
     * items withing the {@link android.support.v7.widget.RecyclerView}.
     */
    void removeLoadingRecyclerItem();
}
