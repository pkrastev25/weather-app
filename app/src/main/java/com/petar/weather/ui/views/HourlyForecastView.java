package com.petar.weather.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.petar.weather.models.AppStateModel;
import com.petar.weather.presenters.HourlyForecastViewPresenter;
import com.petar.weather.ui.views.interfaces.IStatefulView;

public class HourlyForecastView extends AStatefulFrameLayout<IStatefulView<AppStateModel>, HourlyForecastViewPresenter, AppStateModel> {

    public HourlyForecastView(Context context) {
        super(context);
    }

    public HourlyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HourlyForecastView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    @Override
    public HourlyForecastViewPresenter createPresenter() {
        return new HourlyForecastViewPresenter();
    }

    @Override
    protected void onPresenterCreated() {

    }

    @Override
    public void renderState(AppStateModel state) {

    }
}
