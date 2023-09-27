package com.example.servingcalculator.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.AteFoodsDatabase.AteFoodsDatabase;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;
import com.example.servingcalculator.databinding.FragmentDashboardBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        root = binding.getRoot();

//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void onResume() {
        super.onResume();
        TextView meanTxt = root.findViewById(R.id.weekTxtMeanMain);
        TextView thresholdTxt = root.findViewById(R.id.weekTxtThresholdMain);
        JSONObject foodsMean = new JSONObject();
        JSONObject thresholdJson = new JSONObject();
        AteFoodsDatabase ateFoodsDB = Room.databaseBuilder(getContext(), AteFoodsDatabase.class, "AteFoods-database").fallbackToDestructiveMigration().build();
        AppDatabase thresholdDb = Room.databaseBuilder(getContext(), AppDatabase.class, "Food-database").build();
        new Thread(() -> {
            List<AteFood> AteFoodsList = ateFoodsDB.getData().getAll();
            Food threshold = thresholdDb.getData().getThreshold();
            try {
                if (threshold != null) {
                    thresholdJson.put("Calorii", threshold.getValoareEnergetica());
                    thresholdJson.put("Grasimi", threshold.getGrasimi());
                    thresholdJson.put("Acizi", threshold.getAcizi());
                    thresholdJson.put("Glucide", threshold.getGlucide());
                    thresholdJson.put("Zaharuri", threshold.getZaharuri());
                    thresholdJson.put("Fibre", threshold.getFibre());
                    thresholdJson.put("Proteine", threshold.getProteine());
                    thresholdJson.put("Sare", threshold.getSare());
                }
                String thresholdText = (threshold != null) ? createFormattedText(thresholdJson) : "";
                meanTxt.post(() -> {
                    thresholdTxt.setText(thresholdText);
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            AteFood ateFoodsMean = new AteFood();

            Set<LocalDate> uniqueDates = new HashSet<>(); //keep track of unique dates

            for (AteFood ateFood : AteFoodsList) {
                LocalDateTime ateFoodDate = ateFood.getData();
                if (isThisWeek(ateFoodDate)) {
                    uniqueDates.add(ateFoodDate.toLocalDate());  //add the date to the set
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

            //divide each accumulated value by the number of unique dates to get the daily average for the week
            int daysCount = uniqueDates.size();
            if (daysCount != 0) {
                ateFoodsMean.setValoareEnergetica(ateFoodsMean.getValoareEnergetica() / daysCount);
                ateFoodsMean.setAcizi(ateFoodsMean.getAcizi() / daysCount);
                ateFoodsMean.setGlucide(ateFoodsMean.getGlucide() / daysCount);
                ateFoodsMean.setFibre(ateFoodsMean.getFibre() / daysCount);
                ateFoodsMean.setGrasimi(ateFoodsMean.getGrasimi() / daysCount);
                ateFoodsMean.setZaharuri(ateFoodsMean.getZaharuri() / daysCount);
                ateFoodsMean.setProteine(ateFoodsMean.getProteine() / daysCount);
                ateFoodsMean.setSare(ateFoodsMean.getSare() / daysCount);

                try {
                    foodsMean.put("Calorii", ateFoodsMean.getValoareEnergetica());
                    foodsMean.put("Grasimi", ateFoodsMean.getGrasimi());
                    foodsMean.put("Acizi", ateFoodsMean.getAcizi());
                    foodsMean.put("Glucide", ateFoodsMean.getGlucide());
                    foodsMean.put("Zaharuri", ateFoodsMean.getZaharuri());
                    foodsMean.put("Fibre", ateFoodsMean.getFibre());
                    foodsMean.put("Proteine", ateFoodsMean.getProteine());
                    foodsMean.put("Sare", ateFoodsMean.getSare());

                    String formattedText = createFormattedText(foodsMean);

                    //update UI elements
                    meanTxt.post(() -> {
                        meanTxt.setText(formattedText);
                    });

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

        private String createFormattedText(JSONObject jsonObject) throws JSONException {
            StringBuilder formattedText = new StringBuilder();
            formattedText.append("Calorii: ").append(roundToTwoDecimals(jsonObject.getDouble("Calorii"))).append("\n");
            formattedText.append("Grasimi: ").append(roundToTwoDecimals(jsonObject.getDouble("Grasimi"))).append("\n");
            formattedText.append("Acizi: ").append(roundToTwoDecimals(jsonObject.getDouble("Acizi"))).append("\n");
            formattedText.append("Glucide: ").append(roundToTwoDecimals(jsonObject.getDouble("Glucide"))).append("\n");
            formattedText.append("Zaharuri: ").append(roundToTwoDecimals(jsonObject.getDouble("Zaharuri"))).append("\n");
            formattedText.append("Fibre: ").append(roundToTwoDecimals(jsonObject.getDouble("Fibre"))).append("\n");
            formattedText.append("Proteine: ").append(roundToTwoDecimals(jsonObject.getDouble("Proteine"))).append("\n");
            formattedText.append("Sare: ").append(roundToTwoDecimals(jsonObject.getDouble("Sare")));

            return formattedText.toString();
        }
    public double roundToTwoDecimals(double value) {
        return (double) Math.round(value * 100) / 100;
    }
    public static boolean isThisWeek(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));

        return !dateTime.isBefore(startOfWeek) && !dateTime.isAfter(endOfWeek);
    }
}