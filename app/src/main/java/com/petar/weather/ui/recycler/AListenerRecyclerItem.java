package com.petar.weather.ui.recycler;

import android.os.Parcelable;

import com.petar.weather.app.Constants;

/**
 * A base class for all recycler items. Provides a {@link IListener} to react
 * on user interaction.
 *
 * @param <TListener> The type of listener for this item
 * @author Petar Krastev
 * @version 1.0
 * @since 20.8.2017
 */
public abstract class AListenerRecyclerItem<TListener extends IListener> implements Parcelable {

    private TListener mListener;

    public TListener getListener() {
        return mListener;
    }

    public void setListener(TListener listener) {
        mListener = listener;
    }

    /**
     * Used to identify what kind of object the current {@link AListenerRecyclerItem} is.
     *
     * @return One of type {@link com.petar.weather.app.Constants.RecyclerItems}
     */
    @Constants.RecyclerItems
    public abstract int getViewType();
}
