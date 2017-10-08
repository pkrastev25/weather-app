package com.petar.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Base class for {@link android.support.v7.widget.RecyclerView.ViewHolder}.
 *
 * @param <TBinding> The binding type of the view
 * @author Petar Krastev
 * @version 1.0
 * @since 26.8.2017
 */
public class BaseRecyclerViewBindingHolder<TBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private TBinding mBinding;

    /**
     * Initializes the {@link BaseRecyclerViewBindingHolder}. Creates a binding
     * for the view.
     *
     * @param itemView The type of view
     */
    public BaseRecyclerViewBindingHolder(View itemView) {
        super(itemView);

        mBinding = DataBindingUtil.bind(itemView);
    }

    public TBinding getBinding() {
        return mBinding;
    }
}