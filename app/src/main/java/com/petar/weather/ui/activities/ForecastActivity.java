package com.petar.weather.ui.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hannesdorfmann.mosby3.mvp.lce.MvpLceActivity;
import com.petar.weather.R;
import com.petar.weather.databinding.ActivityForecastBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.presenters.ForecastActivityPresenter;
import com.petar.weather.ui.adapter.ForecastRecyclerAdapter;
import com.petar.weather.ui.views.IForecastActivity;
import com.petar.weather.util.ForecastUtil;

import java.util.List;

public class ForecastActivity extends MvpLceActivity<RecyclerView, List<? extends AForecast>, IForecastActivity, ForecastActivityPresenter>
        implements IForecastActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        ActivityForecastBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_forecast);
        binding.setView(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("woId", -1);
        presenter.loadForecast(id);
    }

    @NonNull
    @Override
    public ForecastActivityPresenter createPresenter() {
        return new ForecastActivityPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return null;
    }

    @Override
    public void setData(List<? extends AForecast> data) {
        List<? extends AForecast> processedData = ForecastUtil.extractData(data);
        loadingView.setVisibility(View.INVISIBLE);

        contentView.setLayoutManager(new LinearLayoutManager(this));
        contentView.setAdapter(new ForecastRecyclerAdapter(processedData));
    }

    @Override
    public void loadData(boolean pullToRefresh) {

    }
}
