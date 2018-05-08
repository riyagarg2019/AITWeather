package com.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by riyagarg on 5/2/18.
 */
@Dao
public interface CityDAO {

    @Query("SELECT * FROM city")
    List<City> getAll();

    @Insert
    long insertCity(City city);

    @Delete
    void delete(City city);

    @Update
    void update(City city);
}