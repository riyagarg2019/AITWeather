package com.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by riyagarg on 5/2/18.
 */


    @Database(entities = {City.class}, version = 1) //says Todo class is the only data class @Database(entities = {Todo.class, student---, blah, blah}, version = 1)
    public abstract class AppDatabase extends RoomDatabase { //name of class is AppDatabase, extends from Room

        private static com.data.AppDatabase INSTANCE; //instance of class which is private static,

        public abstract CityDAO cityDao(); //constructer, private is same

        public static com.data.AppDatabase getAppDatabase(Context context) { //getInstance method, create new one if its null, otherwise everyone gets this one
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        com.data.AppDatabase.class, "city-database").build();
            }
            return INSTANCE;
        }

        public static void destroyInstance() {
            INSTANCE = null;
        }
    }

