package com.example.servingcalculator.ui.ButtonActivities.AddFood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

public class GrasimiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grasimi);
        EditText editText = findViewById(R.id.grasimiET);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        editText.requestFocus();
        Intent intent = new Intent(getApplicationContext(), AciziGrasiSaturatiActivity.class);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Food FoodObject=  getIntent().getParcelableExtra("Food");
                    FoodObject.setGrasimi(Double.parseDouble(editText.getText().toString()));
                    intent.putExtra("Food", (Parcelable) FoodObject);
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
    }
}