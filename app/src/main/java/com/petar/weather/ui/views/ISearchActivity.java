package com.petar.weather.ui.views;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceView;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.List;

/**
 * The mediator between {@link com.petar.weather.ui.activities.SearchActivity} and
 * {@link com.petar.weather.presenters.SearchActivityPresenter}. All methods here
 * are exposed to the presenter.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 27.8.2017
 */
public interface ISearchActivity extends MvpLceView<List<? extends AListenerRecyclerItem>> {

}
