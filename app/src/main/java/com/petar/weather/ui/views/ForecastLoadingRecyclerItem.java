package com.petar.weather.ui.views;

import android.os.Parcel;

import com.petar.weather.ui.recycler.AListenerRecyclerItem;
import com.petar.weather.util.Constants;

/**
 * Created by User on 20.8.2017 г..
 */

public class ForecastLoadingRecyclerItem extends AListenerRecyclerItem {

    @Override
    public int getViewType() {
        return Constants.FORECAST_LOADING_RECYCLER_ITEM;
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

    public ForecastLoadingRecyclerItem() {
    }

    protected ForecastLoadingRecyclerItem(Parcel in) {
    }

    public static final Creator<ForecastLoadingRecyclerItem> CREATOR = new Creator<ForecastLoadingRecyclerItem>() {
        @Override
        public ForecastLoadingRecyclerItem createFromParcel(Parcel source) {
            return new ForecastLoadingRecyclerItem(source);
        }

        @Override
        public ForecastLoadingRecyclerItem[] newArray(int size) {
            return new ForecastLoadingRecyclerItem[size];
        }
    };
}
