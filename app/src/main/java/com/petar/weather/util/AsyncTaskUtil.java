package com.petar.weather.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * Created by User on 26.6.2017 г..
 */

public class AsyncTaskUtil extends AsyncTask<Void, Void, Object> {

    private static IAsyncTaskHelperListener sListener;
    private static AsyncTaskUtil sInstance;
    private Exception mException;

    public AsyncTaskUtil(@NonNull IAsyncTaskHelperListener listener) {
        sListener = listener;
    }

    public static void doInBackground(@NonNull IAsyncTaskHelperListener listener) {
        if (sInstance == null) {
            sInstance = new AsyncTaskUtil(listener);
        }

        sInstance.execute();
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);

        if (mException != null) {
            sListener.onError(mException);
        } else {
            sListener.onSuccess(result);
        }

        clear();
    }

    private void clear() {
        mException = null;
        sListener = null;
        sInstance = null;
    }

    @Override
    protected Object doInBackground(Void... params) {
        try {
            return sListener.onExecuteTask();
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
