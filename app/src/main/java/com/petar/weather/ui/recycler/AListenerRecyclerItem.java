package com.petar.weather.ui.recycler;

import android.os.Parcelable;

import com.petar.weather.app.Constants;

/**
 * Created by User on 20.8.2017 г..
 */

public abstract class AListenerRecyclerItem<TListener extends IListener> implements Parcelable {

    private TListener mListener;

    public TListener getListener() {
        return mListener;
    }

    public void setListener(TListener listener) {
        mListener = listener;
    }

    @Constants.RecyclerItems
    public abstract int getViewType();
}
