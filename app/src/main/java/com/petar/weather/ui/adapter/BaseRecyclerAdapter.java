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
 * Base adapter for the {@link com.petar.weather.app.Constants.RecyclerItems} items.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 5.9.2017
 */
public class BaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AListenerRecyclerItem> mData;

    /**
     * Default constructor.
     */
    public BaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    public List<? extends AListenerRecyclerItem> getData() {
        return mData;
    }

    /**
     * Completely replaces the existing data set with a new one.
     *
     * @param data New data set
     */
    public void setData(List<? extends AListenerRecyclerItem> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * Makes a check if the adapter has data.
     *
     * @return True if the adapter is empty, false otherwise
     */
    public boolean isEmpty() {
        return mData.isEmpty();
    }

    /**
     * Adds a new data set to the already existing one.
     *
     * @param items New data set to be added
     */
    public void addItems(List<? extends AListenerRecyclerItem> items) {
        mData.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Adds a new item to the existing data set.
     *
     * @param item New item to be added
     */
    public void addItem(AListenerRecyclerItem item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    /**
     * Removes the specified element from the data set.
     *
     * @param item Item to be removed
     */
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
