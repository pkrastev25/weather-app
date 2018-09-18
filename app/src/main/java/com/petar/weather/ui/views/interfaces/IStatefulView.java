package com.petar.weather.ui.views.interfaces;

import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface IStatefulView<TStateModel> extends MvpView {

    void renderState(final TStateModel state);
}
