package com.petar.weather.ui.views;

import android.content.Context;
import android.util.AttributeSet;

import com.hannesdorfmann.mosby3.mvp.layout.MvpFrameLayout;
import com.petar.weather.presenters.AStatefulPresenter;
import com.petar.weather.ui.views.interfaces.IStatefulView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

public abstract class AStatefulFrameLayout<TView extends IStatefulView<TStateModel>, TPresenter extends AStatefulPresenter<TView, TStateModel>, TStateModel>
        extends MvpFrameLayout<TView, TPresenter>
        implements IStatefulView<TStateModel> {

    protected final CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected abstract void onPresenterCreated();

    private Unbinder mUnbinder;

    public AStatefulFrameLayout(Context context) {
        super(context);
    }

    public AStatefulFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AStatefulFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AStatefulFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setPresenter(TPresenter presenter) {
        super.setPresenter(presenter);

        onPresenterCreated();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        compositeDisposable.add(
                presenter.getCurrentState()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::renderState)
        );
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        compositeDisposable.dispose();
        mUnbinder.unbind();
    }
}
