package com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class SelectFromSavedFoodsActivity extends AppCompatActivity implements RecyclerViewInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_saved_foods);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "Food-database").build();

        AtomicReference<List<Food>> FoodsList = new AtomicReference<>();
        new Thread(() -> {
            FoodsList.set(db.getData().getAll());
            for (Food Food : FoodsList.get()) {
                Log.d("DATABASE_CONTENT", Food.toString());
            }
            // Convert atomic to arraylist inside the thread, after data fetching is complete
            ArrayList<Food> arrayListOfFoods;
            List<Food> foodListFromAtomicRef = FoodsList.get();

            if (foodListFromAtomicRef instanceof ArrayList) {
                arrayListOfFoods = (ArrayList<Food>) foodListFromAtomicRef;
            } else {
                arrayListOfFoods = new ArrayList<>(foodListFromAtomicRef);
            }

            // Update the UI on the main thread
            runOnUiThread(() -> {
                //populate the recyclerview with the fetched list of foods
                RecyclerView recyclerView = findViewById(R.id.savedFoodsRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                Adapter adapter=new Adapter(arrayListOfFoods, (RecyclerViewInterface) this);
                recyclerView.setAdapter(adapter);
            });

        }).start();
    }

    @Override
    public void onItemClick(int position) {

    }
}