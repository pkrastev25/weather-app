package com.petar.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.petar.weather.BR;
import com.petar.weather.R;
import com.petar.weather.databinding.LocationBinding;
import com.petar.weather.logic.models.ALocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 1.7.2017 г..
 */

public class LocationRecyclerAdapter extends RecyclerView.Adapter<LocationRecyclerAdapter.BindingHolder> {
    private List<? extends ALocation> mData;

    public LocationRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location, parent, false);
        return new BindingHolder(view);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final ALocation location = mData.get(position);
        holder.getBinding().setVariable(BR.item, location);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public List<? extends ALocation> getData() {
        return mData;
    }

    public void setData(List<? extends ALocation> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder {
        private LocationBinding mBinding;

        public BindingHolder(final View itemView) {
            super(itemView);
            mBinding = DataBindingUtil.bind(itemView);
        }

        public LocationBinding getBinding() {
            return mBinding;
        }
    }
}
