package com.petar.weather.util;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Helper class for a {@link AsyncTask} which handles workload off the main working thread.
 * Provides an interface for usage which helps identify the result of the asynchronous operation.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 26.6.2017
 */
public class AsyncTaskUtil extends AsyncTask<Void, Void, Object> {

    private IAsyncTaskHelperListener mListener;
    private Exception mException;

    /**
     * Create an instance of {@link AsyncTaskUtil}. An explicit call to {@link #execute(Object[])}
     * is needed to start the asynchronous task.
     *
     * @param listener Instance of {@link IAsyncTaskHelperListener}
     */
    public AsyncTaskUtil(@NonNull IAsyncTaskHelperListener listener) {
        mListener = listener;
    }

    /**
     * Create an instance of {@link AsyncTaskUtil}. The task is automatically started.
     *
     * @param listener Instance of {@link IAsyncTaskHelperListener}
     */
    public static void doInBackground(@NonNull IAsyncTaskHelperListener listener) {
        new AsyncTaskUtil(listener).execute();
    }

    /**
     * Called after the asynchronous task has finished work. If no exception was thrown,
     * {@link IAsyncTaskHelperListener#onSuccess(Object)} is called,
     * {@link IAsyncTaskHelperListener#onError(Exception)} otherwise.
     *
     * @param result The result of the asynchronous task
     */
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

    /**
     * Called right after {@link #execute(Object[])} is started. Performs the request and
     * catches any errors during execution.
     *
     * @param params Ignored, {@link IAsyncTaskHelperListener#onExecuteTask()} takes care of invoking the request
     * @return The result of the asynchronous task, null if the task failed to complete
     */
    @Nullable
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

    /**
     * Helper interface, must be implemented by all classes who wish to use the
     * {@link AsyncTaskUtil}.
     *
     * @param <TResult> The result type of the API request
     */
    public interface IAsyncTaskHelperListener<TResult> {
        /**
         * Provides an API request.
         *
         * @return Result of the API request
         * @throws Exception Something went wrong with the request
         */
        TResult onExecuteTask() throws Exception;

        /**
         * Provide logic for dealing with the result of the API request.
         *
         * @param result The result of the API request
         */
        void onSuccess(TResult result);

        /**
         * Provide logic for dealing with errors occurred during the
         * execution of the asynchronous task!
         *
         * @param error Exception occurred during the execution of the asynchronous task
         */
        void onError(Exception error);
    }
}
