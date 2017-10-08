package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.List;

/**
 * The mediator between {@link com.petar.weather.ui.fragments.DailyForecastFragment} and
 * {@link com.petar.weather.presenters.DailyForecastFragmentPresenter}. All methods here
 * are exposed to the presenter.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 8.7.2017
 */
public interface IDailyForecastFragment extends MvpLceView<List<? extends AListenerRecyclerItem>> {

    /**
     * Shows a message to the user in the form of a {@link android.widget.Toast}.
     *
     * @param message The message to be shown
     */
    void showToastMessage(String message);
}
