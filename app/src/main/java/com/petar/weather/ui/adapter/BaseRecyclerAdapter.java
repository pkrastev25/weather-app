package com.petar.weather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.BR;
import com.petar.weather.R;
import com.petar.weather.databinding.ForecastRecyclerItemBinding;
import com.petar.weather.databinding.LoadingRecyclerItemBinding;
import com.petar.weather.databinding.LocationRecyclerItemBinding;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.app.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5.9.2017 г..
 */

public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AListenerRecyclerItem> mData;

    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public List<? extends AListenerRecyclerItem> getData() {
        return mData;
    }

    public void setData(List<? extends AListenerRecyclerItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    public void addItems(List<? extends AListenerRecyclerItem> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(AListenerRecyclerItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(AListenerRecyclerItem item) {
        mData.remove(item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Constants.RecyclerItems
    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, @Constants.RecyclerItems int viewType) {
        View view;

        switch (viewType) {
            case Constants.RecyclerItems.FORECAST_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_recycler_item, parent, false);
                return new BaseRecyclerViewBindingHolder<ForecastRecyclerItemBinding>(view);
            case Constants.RecyclerItems.LOCATION_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recycler_item, parent, false);
                return new BaseRecyclerViewBindingHolder<LocationRecyclerItemBinding>(view);
            case Constants.RecyclerItems.LOADING_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_recycler_item, parent, false);
                return new BaseRecyclerViewBindingHolder<LoadingRecyclerItemBinding>(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AListenerRecyclerItem item = mData.get(position);

        switch (holder.getItemViewType()) {
            case Constants.RecyclerItems.FORECAST_ITEM:
                BaseRecyclerViewBindingHolder<ForecastRecyclerItemBinding> forecastBindingBaseRecyclerViewBindingHolder = (BaseRecyclerViewBindingHolder<ForecastRecyclerItemBinding>) holder;
                forecastBindingBaseRecyclerViewBindingHolder.getBinding().setVariable(BR.item, item);
                forecastBindingBaseRecyclerViewBindingHolder.getBinding().executePendingBindings();
                break;
            case Constants.RecyclerItems.LOCATION_ITEM:
                BaseRecyclerViewBindingHolder<LocationRecyclerItemBinding> locationBindingBaseRecyclerViewBindingHolder = (BaseRecyclerViewBindingHolder<LocationRecyclerItemBinding>) holder;
                locationBindingBaseRecyclerViewBindingHolder.getBinding().setVariable(BR.item, item);
                locationBindingBaseRecyclerViewBindingHolder.getBinding().executePendingBindings();
                break;
            case Constants.RecyclerItems.LOADING_ITEM:
                BaseRecyclerViewBindingHolder<LoadingRecyclerItemBinding> loadingRecyclerItemBindingBaseRecyclerViewBindingHolder = (BaseRecyclerViewBindingHolder<LoadingRecyclerItemBinding>) holder;
                loadingRecyclerItemBindingBaseRecyclerViewBindingHolder.getBinding().setVariable(BR.item, item);
                loadingRecyclerItemBindingBaseRecyclerViewBindingHolder.getBinding().executePendingBindings();
                break;
            default:
                break;
        }
    }
}
