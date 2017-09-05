package com.petar.weather.ui.recycler;

import android.os.Parcel;

import com.petar.weather.app.Constants;

/**
 * Created by User on 20.8.2017 г..
 */

public class LoadingRecyclerItem extends AListenerRecyclerItem {

    @Override
    public int getViewType() {
        return Constants.RecyclerItems.LOADING_ITEM;
    }

    /**
     * Based on https://stackoverflow.com/questions/4076946/parcelable-where-when-is-describecontents-used
     *
     * @return
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
