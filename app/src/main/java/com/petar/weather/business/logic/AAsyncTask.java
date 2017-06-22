package com.petar.weather.business.logic;

import android.os.AsyncTask;

/**
 * Created by User on 22.6.2017 г..
 */

public abstract class AAsyncTask<TParams, TResult> extends AsyncTask<TParams, Void, TResult> {

    protected Exception mException;
    protected AAsyncTaskListener mListener;

    public AAsyncTask(AAsyncTaskListener listener) {
        mListener = listener;
    }

    public interface AAsyncTaskListener<TResult> {
        void onLoadFinished(TResult result);

        void onError();
    }
}
