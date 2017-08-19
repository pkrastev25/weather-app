package com.petar.weather.networking;

import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.networking.models.NWeeklyForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by User on 22.6.2017 г..
 */

public interface IApi {
    @GET("/api/location/search")
    Call<List<NLocation>> getLocationQuery(
            @Query("query") String query
    );

    @GET("/api/location/search")
    Call<List<NLocation>> getLocationQueryWithCoordinates(
            @Query("lattlong") String coordinates
    );

    @GET("/api/location/{woeid}/")
    Call<NWeeklyForecast> getLocationForecast(
            @Path("woeid") int id
    );

    @GET("api/location/{woeid}/{date}")
    Call<List<NForecast>> getLocationForecastForDate(
            @Path("woeid") int id, @Path("date") String date
    );
}
