package com.petar.weather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.BR;
import com.petar.weather.R;
import com.petar.weather.databinding.LocationBinding;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;

/**
 * Created by User on 1.7.2017 г..
 */

public class LocationRecyclerAdapter extends ABaseRecyclerAdapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location, parent, false);
        return new BaseRecyclerViewBindingHolder<LocationBinding>(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AListenerRecyclerItem item = mData.get(position);

        BaseRecyclerViewBindingHolder<LocationBinding> bindingHolder = (BaseRecyclerViewBindingHolder<LocationBinding>) holder;
        bindingHolder.getBinding().setVariable(BR.item, item);
        bindingHolder.getBinding().executePendingBindings();
    }
}
