package com.petar.weather.logic.models;

/**
 * Created by User on 29.6.2017 г..
 */

public interface ILocation extends Comparable<ILocation> {

    Integer getDistance();

    Integer getId();
}