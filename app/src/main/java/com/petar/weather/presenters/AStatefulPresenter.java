package com.petar.weather.presenters;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.petar.weather.ui.views.interfaces.IStatefulView;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public abstract class AStatefulPresenter<TView extends IStatefulView, TStateModel>
        extends MvpBasePresenter<TView> {

    protected final BehaviorSubject<TStateModel> currentState = BehaviorSubject.create();

    public final Observable<TStateModel> getCurrentState() {
        return currentState;
    }
}
