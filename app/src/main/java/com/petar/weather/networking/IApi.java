package com.petar.weather.networking;

import com.petar.weather.app.Constants;
import com.petar.weather.networking.models.NForecast;
import com.petar.weather.networking.models.NLocation;
import com.petar.weather.networking.models.NDailyForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Contains all the API endpoints.
 *
 * @author Petar Krastev
 * @version 1.0
 * @since 22.6.2017
 */
public interface IApi {

    /**
     * Realizes a GET request for the location query.
     *
     * @param query The location query of interest
     * @return The response of the request
     */
    @GET("/api/location/search")
    Call<List<NLocation>> getLocationQuery(
            @Query("query") String query
    );

    /**
     * Realizes a GET request for the location query in the form of
     * coordinates.
     *
     * @param coordinates Coordinates in the form of {@link Constants#FORMAT_COORDINATES}
     * @return The response of the request
     */
    @GET("/api/location/search")
    Call<List<NLocation>> getLocationQueryWithCoordinates(
            @Query("lattlong") String coordinates
    );

    /**
     * Realizes a GET request for the daily forecast for the specified
     * location.
     *
     * @param idWOE 'Where on Earth ID', identifies a location
     * @return The response of the request
     */
    @GET("/api/location/{woeid}/")
    Call<NDailyForecast> getLocationDailyForecast(
            @Path("woeid") int idWOE
    );

    /**
     * Realizes a GET request for the hourly forecast for the specified
     * location and date.
     *
     * @param idWOE 'Where on Earth ID', identifies a location
     * @param date  The date which the forecast is for. Must be in the form of {@link Constants#API_DATE_REQUEST_FORMAT}
     * @return The response of the request
     */
    @GET("api/location/{woeid}/{date}")
    Call<List<NForecast>> getLocationHourlyForecastForDate(
            @Path("woeid") int idWOE, @Path("date") String date
    );

    /**
     * Contains the API endpoint for PNG image resources. The param must be of
     * type {@link com.petar.weather.app.Constants.APIWeatherStateSummary}.
     */
    String PNG_IMAGE_URL = Constants.API_BASE_URL + "/static/img/weather/png/%s.png";
}
