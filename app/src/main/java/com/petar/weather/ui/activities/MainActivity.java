package com.petar.weather.ui.activities;

import android.support.annotation.NonNull;
import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;
import com.petar.weather.R;
import com.petar.weather.presenters.MainActivityPresenter;
import com.petar.weather.ui.views.IMainActivity;

public class MainActivity extends MvpActivity<IMainActivity, MainActivityPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }
}
