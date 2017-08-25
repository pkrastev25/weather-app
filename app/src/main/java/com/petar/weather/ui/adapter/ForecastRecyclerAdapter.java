package com.petar.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.BR;
import com.petar.weather.R;
import com.petar.weather.databinding.ForecastBinding;
import com.petar.weather.databinding.ForecastLoadingRecyclerItemBinding;
import com.petar.weather.logic.models.AForecast;
import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<AListenerRecyclerItem> mData;

    public ForecastRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        switch (viewType) {
            case Constants.FORECAST_RECYCLER_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast, parent, false);
                return new ForecastBindingHolder(view);
            case Constants.FORECAST_LOADING_RECYCLER_ITEM:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_loading_recycler_item, parent, false);
                return new ForecastLoadingBindingHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final AListenerRecyclerItem item = mData.get(position);

        switch (holder.getItemViewType()) {
            case Constants.FORECAST_RECYCLER_ITEM:
                ForecastBindingHolder forecastBindingHolder = (ForecastBindingHolder) holder;
                forecastBindingHolder.getBinding().setVariable(BR.item, item);
                forecastBindingHolder.getBinding().executePendingBindings();
                break;
            case Constants.FORECAST_LOADING_RECYCLER_ITEM:
                ForecastLoadingBindingHolder forecastLoadingBindingHolder = (ForecastLoadingBindingHolder) holder;
                forecastLoadingBindingHolder.getBinding().setVariable(BR.item, item);
                forecastLoadingBindingHolder.getBinding().executePendingBindings();
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<? extends AForecast> getForecastData() {
        List<AForecast> forecasts = new ArrayList<>();

        for (AListenerRecyclerItem item: mData) {
            if (item.getViewType() == Constants.FORECAST_RECYCLER_ITEM) {
                forecasts.add((AForecast) item);
            }
        }

        return forecasts;
    }

    public List<? extends AListenerRecyclerItem> getData() {
        return mData;
    }

    public void addItem(AListenerRecyclerItem item) {
        mData.add(item);
    }

    public void removeItem(AListenerRecyclerItem item) {
        mData.remove(item);
    }

    public void setData(List<? extends AListenerRecyclerItem> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    public static class ForecastBindingHolder extends RecyclerView.ViewHolder {
        private ForecastBinding mBinding;

        public ForecastBindingHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }

        public ForecastBinding getBinding() {
            return mBinding;
        }
    }

    public static class ForecastLoadingBindingHolder extends RecyclerView.ViewHolder {
        private ForecastLoadingRecyclerItemBinding mBinding;

        public ForecastLoadingBindingHolder(View itemView) {
            super(itemView);

            mBinding = DataBindingUtil.bind(itemView);
        }

        public ForecastLoadingRecyclerItemBinding getBinding() {
            return mBinding;
        }
    }
}
