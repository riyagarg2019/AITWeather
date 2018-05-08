package com.data;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.io.Serializable;

/**
 * Created by riyagarg on 5/2/18.
 */
@Entity
public class City implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long cityId;

    @ColumnInfo(name = "city_name")
    private String cityName;


    public City(String cityName) {
        this.cityName = cityName;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


}
