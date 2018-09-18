package com.petar.weather.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.petar.weather.R;
import com.petar.weather.models.AppStateModel;
import com.petar.weather.presenters.DailyForecastViewPresenter;
import com.petar.weather.ui.views.interfaces.IStatefulView;

import butterknife.BindView;

public class DailyForecastView extends AStatefulFrameLayout<IStatefulView<AppStateModel>, DailyForecastViewPresenter, AppStateModel> {

    @BindView(R.id.data_view)
    View dataView;

    public DailyForecastView(Context context) {
        super(context);
    }

    public DailyForecastView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyForecastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DailyForecastView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @NonNull
    @Override
    public DailyForecastViewPresenter createPresenter() {
        return new DailyForecastViewPresenter();
    }

    @Override
    protected void onPresenterCreated() {

    }

    @Override
    public void renderState(AppStateModel state) {
        if (state instanceof AppStateModel.LoadingState) {
            showLoadingView();
        } else if (state instanceof AppStateModel.DataState) {
            showDataView();
        } else if (state instanceof AppStateModel.ErrorState) {
            showErrorView();
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void showLoadingView() {

    }

    private void showDataView() {

    }

    private void showErrorView() {

    }
}
