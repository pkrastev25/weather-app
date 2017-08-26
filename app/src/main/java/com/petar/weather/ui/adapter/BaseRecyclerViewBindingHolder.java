package com.petar.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by User on 26.8.2017 г..
 */

public class BaseRecyclerViewBindingHolder<TBinding extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private TBinding mBinding;

    public BaseRecyclerViewBindingHolder(View itemView) {
        super(itemView);

        mBinding = DataBindingUtil.bind(itemView);
    }

    public TBinding getBinding() {
        return mBinding;
    }
}