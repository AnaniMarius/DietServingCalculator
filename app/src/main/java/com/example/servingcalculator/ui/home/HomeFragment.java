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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.AteFoodsDatabase.AteFoodsDatabase;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.MainActivity;
import com.example.servingcalculator.R;
import com.example.servingcalculator.databinding.FragmentHomeBinding;
import com.example.servingcalculator.ui.ButtonActivities.AddFood.NumeActivity;
import com.example.servingcalculator.ui.ButtonActivities.AddFood.ValoareEnergeticaActivity;
import com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods.Adapter;
import com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods.RecyclerViewInterface;
import com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods.SelectFromSavedFoodsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private FragmentHomeBinding binding;
    private Button addFood;
    private Button addSnack;
    private Button addThreshold;
    private Button resetDatabase;
    private Button resetEatenFoodsDatabase;
    private Button checkList;
    Intent addFoodActivity;
    Intent addThresholdActivity;
    Intent checkListActivity;
    View root;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AppDatabase db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "Food-database").build();
        AteFoodsDatabase ateFoodsDb = Room.databaseBuilder(getContext(),
                AteFoodsDatabase.class, "AteFoods-database").build();
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        addFood=root.findViewById(R.id.addFoodBtn);
        addSnack=root.findViewById(R.id.addSnackBtn);
        addThreshold=root.findViewById(R.id.addThresholdBtn);
        resetDatabase=root.findViewById(R.id.resetDbBtn);
        resetEatenFoodsDatabase=root.findViewById(R.id.resetAteDB);
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
                        .setMessage("Are you sure you want to delete the saved food?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                db.getData().deleteAll();
                            }).start();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
        resetEatenFoodsDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete all eaten foods?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            new Thread(() -> {
                                ateFoodsDb.getData().deleteAll();
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

    private View rootView;
    @Override
    public void onResume() {
        super.onResume();
        TextView meanTxt=root.findViewById(R.id.txtMeanMain);
        JSONObject foodsMean = new JSONObject();
        AteFoodsDatabase ateFoodsDB = Room.databaseBuilder(getContext(), AteFoodsDatabase.class, "AteFoods-database").build();
        new Thread(() -> {
            List<AteFood> AteFoodsList = ateFoodsDB.getData().getAll();
            AteFood ateFoodsMean = new AteFood();
            ateFoodsMean.setData(LocalDateTime.now());
            for (AteFood ateFood : AteFoodsList) {
                LocalDate ateFoodDate = ateFood.getData().toLocalDate();
                if (ateFoodDate.equals(LocalDate.now())) {
                    ateFoodsMean.setValoareEnergetica(ateFoodsMean.getValoareEnergetica() + ateFood.getValoareEnergetica());
                    ateFoodsMean.setAcizi(ateFoodsMean.getAcizi() + ateFood.getAcizi());
                    ateFoodsMean.setGlucide(ateFoodsMean.getGlucide() + ateFood.getGlucide());
                    ateFoodsMean.setFibre(ateFoodsMean.getFibre() + ateFood.getFibre());
                    ateFoodsMean.setGrasimi(ateFoodsMean.getGrasimi() + ateFood.getGrasimi());
                    ateFoodsMean.setZaharuri(ateFoodsMean.getZaharuri() + ateFood.getZaharuri());
                    ateFoodsMean.setProteine(ateFoodsMean.getProteine() + ateFood.getProteine());
                    ateFoodsMean.setSare(ateFoodsMean.getSare() + ateFood.getSare());
                }
            }
            try {
                foodsMean.put("Calorii", ateFoodsMean.getValoareEnergetica());
                foodsMean.put("Grasimi", ateFoodsMean.getGrasimi());
                foodsMean.put("Acizi", ateFoodsMean.getAcizi());
                foodsMean.put("Glucide", ateFoodsMean.getGlucide());
                foodsMean.put("Zaharuri", ateFoodsMean.getZaharuri());
                foodsMean.put("Fibre", ateFoodsMean.getFibre());
                foodsMean.put("Proteine", ateFoodsMean.getProteine());
                foodsMean.put("Sare", ateFoodsMean.getSare());

                String formattedText = "";
                try {
                    formattedText += "Calorii: " + foodsMean.getDouble("Calorii") + "\n";
                    formattedText += "Grasimi: " + foodsMean.getDouble("Grasimi") + "\n";
                    formattedText += "Acizi: " + foodsMean.getDouble("Acizi") + "\n";
                    formattedText += "Glucide: " + foodsMean.getDouble("Glucide") + "\n";
                    formattedText += "Zaharuri: " + foodsMean.getDouble("Zaharuri") + "\n";
                    formattedText += "Fibre: " + foodsMean.getDouble("Fibre") + "\n";
                    formattedText += "Proteine: " + foodsMean.getDouble("Proteine") + "\n";
                    formattedText += "Sare: " + foodsMean.getDouble("Sare");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                meanTxt.setText(formattedText);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}