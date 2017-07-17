package com.petar.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.BR;
import com.petar.weather.R;
import com.petar.weather.databinding.ForecastBinding;
import com.petar.weather.logic.models.AForecast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.6.2017 г..
 */

public class ForecastRecyclerAdapter extends RecyclerView.Adapter<ForecastRecyclerAdapter.BindingHolder> {
    private List<? extends AForecast> mData;

    public ForecastRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final AForecast forecast = mData.get(position);
        holder.getBinding().setVariable(BR.item, forecast);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<? extends AForecast> getData() {
        return mData;
    }

    public void setData(List<? extends AForecast> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return mData.isEmpty();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ForecastBinding mBinding;

        public BindingHolder(View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public ForecastBinding getBinding() {
            return mBinding;
        }
    }
}
