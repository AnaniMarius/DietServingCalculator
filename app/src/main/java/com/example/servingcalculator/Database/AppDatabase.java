package com.example.servingcalculator.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Food;

@Database(entities = {Food.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DataAccessObjectFood getData();
    private static AppDatabase instance;

    public static AppDatabase getDatabase(final Context context){
        if(instance!=null){
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Food-database").build();
                }
            }
        }
        return instance;
    }
}
