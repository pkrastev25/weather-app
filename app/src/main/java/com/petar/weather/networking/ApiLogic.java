package com.petar.weather.networking;

import android.support.annotation.Nullable;

import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.networking.models.NDailyForecast;
import com.petar.weather.app.Constants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Singleton that is responsible for performing API requests.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 22.6.2017
 */
public class ApiLogic {

    private static ApiLogic sInstance;
    private final IApi mApi;

    /**
     * Expose only a single instance of this class. Initialize if null.
     *
     * @return The {@link ApiLogic} instance
     */
    public static ApiLogic getInstance() {
        if (sInstance == null) {
            sInstance = new ApiLogic();
        }
        return sInstance;
    }

    /**
     * Keep private, invoke {@link #getInstance()} to get the instance.
     */
    private ApiLogic() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constants.API_REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .connectTimeout(Constants.API_REQUEST_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .build();

        mApi = new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(
                        GsonConverterFactory.create()
                ).client(client)
                .build().create(IApi.class);
    }

    /**
     * Performs an API request for the given query location.
     *
     * @param query The location query of interest
     * @return {@link List} of {@link NLocation} that match the query, null if the request failed
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<NLocation> getLocationQueryResult(String query) throws IOException {
        Response<List<NLocation>> response = mApi.getLocationQuery(query).execute();

        return response.body();
    }

    /**
     * Performs an API request for the given coordinates of a location.
     *
     * @param coordinates Coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @return {@link List} of {@link NLocation} that match the coordinates, null if the request failed
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<NLocation> getLocationQueryResultWithCoordinates(String coordinates) throws IOException {
        Response<List<NLocation>> response = mApi.getLocationQueryWithCoordinates(coordinates).execute();

        return response.body();
    }

    /**
     * Performs an API request for getting the daily forecast for a given location.
     *
     * @param idWOE 'Where on Earth ID', identifies a location
     * @return The daily forecast for the given location, null if not found
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public NDailyForecast getLocationDailyForecast(int idWOE) throws IOException {
        Response<NDailyForecast> response = mApi.getLocationDailyForecast(idWOE).execute();

        return response.body();
    }

    /**
     * Performs an API request for getting the hourly forecast of a given location for
     * a given date.
     *
     * @param idWOE 'Where on Earth ID', identifies a location
     * @param date  The date which the forecast is for. Must be in the form of {@link Constants#API_DATE_REQUEST_FORMAT}
     * @return The hourly forecast for the given location and date, null if not found
     * @throws IOException Something went wrong with the request
     */
    @Nullable
    public List<NForecast> getLocationForecastForDate(int idWOE, String date) throws IOException {
        Response<List<NForecast>> response = mApi.getLocationHourlyForecastForDate(idWOE, date).execute();

        return response.body();
    }

    /**
     * Construct the URL pointing to specified image resource
     * for the state of the forecast.
     *
     * @param type The type of resource that must be loaded, must be one of {@link com.petar.weather.app.Constants.APIWeatherStateSummary}
     * @return An URL pointing to the image resource
     */
    public String getPNGImageUrl(@Constants.APIWeatherStateSummary String type) {
        return String.format(
                IApi.PNG_IMAGE_URL,
                type
        );
    }
}
