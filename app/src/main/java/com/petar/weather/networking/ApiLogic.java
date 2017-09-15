package com.petar.weather.networking;

import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.networking.models.NWeeklyForecast;
import com.petar.weather.app.Constants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 22.6.2017 г..
 */

public class ApiLogic {

    private static ApiLogic sInstance;
    private final IApi mApi;

    public static ApiLogic getInstance() {
        if (sInstance == null) {
            sInstance = new ApiLogic();
        }
        return sInstance;
    }

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

    public List<NLocation> getLocationQueryResult(String query) throws IOException {
        Response<List<NLocation>> response = mApi.getLocationQuery(query).execute();
        return response.body();
    }

    public List<NLocation> getLocationQueryResultWithCoordinates(String coordinates) throws IOException {
        Response<List<NLocation>> response = mApi.getLocationQueryWithCoordinates(coordinates).execute();
        return response.body();
    }

    public NWeeklyForecast getLocationForecast(int id) throws IOException {
        Response<NWeeklyForecast> response = mApi.getLocationForecast(id).execute();
        return response.body();
    }

    public List<NForecast> getLocationForecastForDate(int id, String date) throws IOException {
        Response<List<NForecast>> response = mApi.getLocationForecastForDate(id, date).execute();
        return response.body();
    }

    // TODO: Rework this
    public String getPNGImageUrl(@Constants.APIWeatherStateSummary String type) {
        return Constants.API_BASE_URL + String.format(Constants.API_IMAGE_PNG, type);
    }
}
