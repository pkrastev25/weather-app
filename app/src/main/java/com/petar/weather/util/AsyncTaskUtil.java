package com.petar.weather.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by User on 26.6.2017 г..
 */

public class AsyncTaskUtil extends AsyncTask<Void, Void, Object> {

    private IAsyncTaskHelperListener mListener;
    private Exception mException;

    public AsyncTaskUtil(@NonNull IAsyncTaskHelperListener listener) {
        mListener = listener;
    }

    public static void doInBackground(@NonNull IAsyncTaskHelperListener listener) {
        new AsyncTaskUtil(listener).execute();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (mException != null) {
            mListener.onError(mException);
        } else {
            mListener.onSuccess(result);
        }

        mException = null;
        mListener = null;
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return mListener.onExecuteTask();
        } catch (Exception e) {
            mException = e;
            e.printStackTrace();
        }

        return null;
    }

    public interface IAsyncTaskHelperListener<TResult> {
        TResult onExecuteTask() throws Exception;

        void onSuccess(TResult result);

        void onError(Exception error);
    }
}
