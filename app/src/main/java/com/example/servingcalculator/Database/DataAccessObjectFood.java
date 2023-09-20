package com.example.servingcalculator.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.servingcalculator.Food;

import java.util.List;

@Dao
public interface DataAccessObjectFood {
    @Query("SELECT * FROM Food WHERE nume <> 'Threshold'")
    List<Food> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFoods(Food... Foods);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertFood(Food Food);
    @Update
    public void updateFoods(Food... Foods);
    @Delete
    public void deleteFoods(Food... Foods);
    @Delete
    void delete(Food Food);
    @Query("DELETE FROM Food")
    void deleteAll();
}
