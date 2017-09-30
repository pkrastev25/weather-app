package com.petar.weather.ui.recycler;

import android.os.Parcel;

import com.petar.weather.app.Constants;

/**
 * A simple loading item.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 20.8.2017
 */
public class LoadingRecyclerItem extends AListenerRecyclerItem {

    @Override
    public int getViewType() {
        return Constants.RecyclerItems.LOADING_ITEM;
    }

    // --------------------------------------------------------
    // PARCELABLE region
    // --------------------------------------------------------

    /**
     * @return #CONTENTS_FILE_DESCRIPTOR
     * @see <a href="https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used">https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used</a>
     */
    @Override
    public int describeContents() {
        return CONTENTS_FILE_DESCRIPTOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public LoadingRecyclerItem() {
    }

    protected LoadingRecyclerItem(Parcel in) {
    }

    public static final Creator<LoadingRecyclerItem> CREATOR = new Creator<LoadingRecyclerItem>() {
        @Override
        public LoadingRecyclerItem createFromParcel(Parcel source) {
            return new LoadingRecyclerItem(source);
        }

        @Override
        public LoadingRecyclerItem[] newArray(int size) {
            return new LoadingRecyclerItem[size];
        }
    };
}
