package com.petar.weather.logic;

import com.petar.weather.logic.models.AForecast;
import com.petar.weather.logic.models.ILocation;
import com.petar.weather.logic.models.ILocationForecast;
import com.petar.weather.networking.ApiLogic;

import java.io.IOException;
import java.util.List;

/**
 * Created by User on 29.6.2017 г..
 */

public class DataLogic {

    private static DataLogic sInstance;

    private DataLogic() {

    }

    public static DataLogic getInstance() {
        if (sInstance == null) {
            sInstance = new DataLogic();
        }

        return sInstance;
    }

    public List<? extends ILocation> getLocationQueryResult(String query) throws IOException {
        return ApiLogic.getInstance().getLocationQueryResult(query);
    }

    public List<? extends ILocation> getLocationQueryResultWithCoordinates(String coordinates) throws IOException {
        return ApiLogic.getInstance().getLocationQueryResultWithCoordinates(coordinates);
    }

    public ILocationForecast getLocationForecast(int id) throws IOException {
        return ApiLogic.getInstance().getLocationForecast(id);
    }

    public List<? extends AForecast> getLocationForecastForDate(int id, String data) throws IOException {
        return ApiLogic.getInstance().getLocationForecastForDate(id, data);
    }
}
