package com.petar.weather.models;

public class AppStateModel {

    private AppStateModel() {

    }

    public static final class LoadingState extends AppStateModel {

        public LoadingState() {

        }
    }

    public static final class DataState<TModel> extends AppStateModel {

        public final TModel data;

        public DataState(final TModel data) {
            this.data = data;
        }
    }

    public static final class ErrorState extends AppStateModel {

        public final Throwable error;

        public ErrorState(final Throwable error) {
            this.error = error;
        }
    }

    public static final class PaginationLoadingState extends AppStateModel {

        public PaginationLoadingState() {

        }
    }

    public static final class PaginationErrorState extends AppStateModel {

        public final Throwable error;

        public PaginationErrorState(final Throwable error) {
            this.error = error;
        }
    }
}
