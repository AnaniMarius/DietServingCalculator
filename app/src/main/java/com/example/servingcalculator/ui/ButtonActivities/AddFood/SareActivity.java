package com.example.servingcalculator.ui.ButtonActivities.AddFood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.DataAccessObjectFood;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

public class SareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sare);
        EditText editText = findViewById(R.id.sareET);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.requestFocus();
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Food FoodObject=  getIntent().getParcelableExtra("Food");
                    FoodObject.setSare(Double.parseDouble(editText.getText().toString()));
                    //add ingredient to database logic
                    AppDatabase db = Room.databaseBuilder(
                            getApplicationContext(),
                            AppDatabase.class,
                            "Food-database"
                    ).build();
                    DataAccessObjectFood FoodDao = db.getData();
                    new Thread(() -> {
                        FoodDao.insertFood(FoodObject);
                    }).start();
                    finish();
                }
                return false;
            }
        });
    }
}