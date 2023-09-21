package com.example.servingcalculator.Database.AteFoodsDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Food;

import java.util.List;
@Dao
public interface AteFoodsDAO {
    @Query("SELECT * FROM AteFood WHERE nume <> 'Threshold'")
    List<AteFood> getAll();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAteFoods(AteFood... AteFoods);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAteFood(AteFood AteFoods);
    @Update
    public void updateFoods(AteFood... AteFoods);
    @Delete
    public void deleteFoods(AteFood... AteFoods);
    @Query("SELECT * FROM AteFood WHERE nume == :Nume")
    List<Food> getAteFoodInfo(String Nume);
    @Delete
    void delete(AteFood AteFood);
    @Query("DELETE FROM AteFood WHERE nume == :Nume")
    void deleteAteFood(String Nume);
    @Query("DELETE FROM AteFood")
    void deleteAll();
}
