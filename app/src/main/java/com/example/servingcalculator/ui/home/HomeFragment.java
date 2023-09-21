package com.example.servingcalculator.ui.home;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;
import com.example.servingcalculator.databinding.FragmentHomeBinding;
import com.example.servingcalculator.ui.ButtonActivities.AddFood.NumeActivity;
import com.example.servingcalculator.ui.ButtonActivities.AddFood.ValoareEnergeticaActivity;
import com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods.RecyclerViewInterface;
import com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods.SelectFromSavedFoodsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private FragmentHomeBinding binding;
    private Button addFood;
    private Button addSnack;
    private Button addThreshold;
    private Button resetDatabase;
    private Button checkList;
    Intent addFoodActivity;
    Intent addThresholdActivity;
    Intent checkListActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "Food-database").build();
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addFood=root.findViewById(R.id.addFoodBtn);
        addSnack=root.findViewById(R.id.addSnackBtn);
        addThreshold=root.findViewById(R.id.addThresholdBtn);
        resetDatabase=root.findViewById(R.id.resetDbBtn);
        checkList=root.findViewById(R.id.checkListBtn);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodActivity=new Intent(getActivity(), NumeActivity.class);
                Food FoodObject=new Food();
                addFoodActivity.putExtra("Food", (Parcelable) FoodObject);
                startActivity(addFoodActivity);
//                createPopupWindow();
            }
        });
        addSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        addThreshold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addThresholdActivity=new Intent(getActivity(), ValoareEnergeticaActivity.class);
                Food threshold=new Food();
                threshold.setNume("Threshold");
                addThresholdActivity.putExtra("Food", (Parcelable) threshold);
                startActivity(addThresholdActivity);
            }
        });
        resetDatabase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this food?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                db.getData().deleteAll();
                            }).start();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkListActivity=new Intent(getActivity(), SelectFromSavedFoodsActivity.class);
                startActivity(checkListActivity);
            }
        });

//        layout=root.findViewById(R.id.relative);
        return root;
    }
//View layout;
//    private void createPopupWindow() {
//        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View popUpView=inflater.inflate(R.layout.addFoodpopup,null);
//        int width=ViewGroup.LayoutParams.MATCH_PARENT;
//        int height=ViewGroup.LayoutParams.WRAP_CONTENT;
//        boolean focusable=true;
//        PopupWindow popupWindow=new PopupWindow(popUpView,width,height,focusable);
//        layout.post(new Runnable() {
//            @Override
//            public void run() {
//                popupWindow.showAtLocation(layout, Gravity.CENTER,0,0);
//
//            }
//        });
//        popUpView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                popupWindow.dismiss();
//                return true;
//            }
//        });
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(int position) {

    }
}