package com.petar.weather.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 26.8.2017 г..
 */

public abstract class ABaseRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<AListenerRecyclerItem> mData;

    public ABaseRecyclerAdapter() {
        mData = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
    }

    public void removeItem(AListenerRecyclerItem item) {
        mData.remove(item);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getViewType();
    }

    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
