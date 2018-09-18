package com.petar.weather.ui.fragments;

import android.os.Bundle;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;
import com.petar.weather.presenters.AStatefulPresenter;
import com.petar.weather.ui.views.interfaces.IStatefulView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public abstract class AStatefulFragment<TView extends IStatefulView, TPresenter extends AStatefulPresenter<TView, TStateModel>, TStateModel>
        extends MvpFragment<TView, TPresenter>
        implements IStatefulView<TStateModel> {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        compositeDisposable.add(
                presenter.getCurrentState()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::renderState)
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        compositeDisposable.dispose();
    }
}
