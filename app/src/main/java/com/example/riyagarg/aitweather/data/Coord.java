package com.example.riyagarg.aitweather.data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by riyagarg on 5/2/18.
 */

public class Coord {


    @SerializedName("lon")
    @Expose
    public Double lon;
    @SerializedName("lat")
    @Expose
    public Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}

