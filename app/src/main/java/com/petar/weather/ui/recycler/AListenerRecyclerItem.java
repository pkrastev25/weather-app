package com.petar.weather.ui.recycler;

/**
 * Created by User on 20.8.2017 г..
 */

public abstract class AListenerRecyclerItem<TListener extends IListener> {

    private TListener mListener;

    public TListener getListener() {
        return mListener;
    }

    public void setListener(TListener listener) {
        mListener = listener;
    }

    public abstract int getViewType();
}
