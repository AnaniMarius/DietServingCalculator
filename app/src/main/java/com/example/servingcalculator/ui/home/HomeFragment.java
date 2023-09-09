package com.example.servingcalculator.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.servingcalculator.R;
import com.example.servingcalculator.databinding.FragmentHomeBinding;
import com.example.servingcalculator.ui.ButtonActivities.AddFood.ValoareEnergeticaActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Button addFood;
    private Button addSnack;
    private Button addThreshold;
    private Button resetDatabase;
    private Button checkList;
    Intent addFoodActivity;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                addFoodActivity=new Intent(getActivity(), ValoareEnergeticaActivity.class);
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

            }
        });
        resetDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        checkList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        layout=root.findViewById(R.id.relative);
        return root;
    }
//View layout;
//    private void createPopupWindow() {
//        LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View popUpView=inflater.inflate(R.layout.addfoodpopup,null);
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
}