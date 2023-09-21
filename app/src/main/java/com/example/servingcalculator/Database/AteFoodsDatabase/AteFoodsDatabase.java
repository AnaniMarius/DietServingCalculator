package com.example.servingcalculator.Database.AteFoodsDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.DataAccessObjectFood;
import com.example.servingcalculator.Food;

@Database(entities = {AteFood.class}, version = 1)
public abstract class AteFoodsDatabase extends RoomDatabase {
    public abstract AteFoodsDAO getData();
    private static AteFoodsDatabase instance;

    public static AteFoodsDatabase getDatabase(final Context context){
        if(instance!=null){
            synchronized (AteFoodsDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(), AteFoodsDatabase.class, "AteFoods-database").build();
                }
            }
        }
        return instance;
    }
}
