package com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods;

import android.app.AlertDialog;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.DataAccessObjectFood;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    ArrayList<Food> foodsList = new ArrayList<>();
    int selectedPosition = -1;  // Initialize with -1 which means no item is selected

    public Adapter(ArrayList<Food> foodsList) {
        this.foodsList = foodsList;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_foods_item_view, parent, false);
        return new AdapterViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.foodName.setText(foodsList.get(position).getNume());

        if (position == selectedPosition) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);  // Highlight the selected item
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);  // Default appearance
        }
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        private final Adapter adapter;

        public AdapterViewHolder(@NonNull View itemView, Adapter adapter) {
            super(itemView);
            this.adapter = adapter;
            foodName = itemView.findViewById(R.id.SFName);

            itemView.setOnClickListener(view -> {
                int previousSelectedPosition = adapter.selectedPosition;
                if (adapter.selectedPosition == getAdapterPosition()) {
                    adapter.selectedPosition = -1;
                } else {
                    adapter.selectedPosition = getAdapterPosition();
                }
                adapter.notifyItemChanged(previousSelectedPosition);
                adapter.notifyItemChanged(adapter.selectedPosition);
            });

            itemView.findViewById(R.id.deleteBtn).setOnClickListener(view -> {
                Food selectedFood = adapter.foodsList.get(getAdapterPosition());
                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to delete this food?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            AppDatabase db = Room.databaseBuilder(
                                    itemView.getContext(),
                                    AppDatabase.class,
                                    "Food-database"
                            ).build();
                            DataAccessObjectFood FoodDao = db.getData();
                            new Thread(() -> {
                                FoodDao.deleteFood(selectedFood.getNume());
                            }).start();
                            adapter.foodsList.remove(getAdapterPosition());
                            adapter.notifyItemRemoved(getAdapterPosition());
                        })
                        .setNegativeButton("No", null)
                        .show();
            });

            itemView.findViewById(R.id.infoBtn).setOnClickListener(view -> {
                Food selectedFood = adapter.foodsList.get(getAdapterPosition());
                showFoodInfoPopup(selectedFood);
            });
        }

        private void showFoodInfoPopup(Food food) {
            new AlertDialog.Builder(itemView.getContext())
                    .setTitle("Food Info per 100g")
                    .setMessage(
                            "Name: " + food.getNume() + "\n" +
                                    "Calorii: " + food.getValoareEnergetica() + "\n" +
                                    "Grasimi: " + food.getGrasimi() + "\n" +
                                    "Acizi: " + food.getAcizi() + "\n" +
                                    "Glucide: " + food.getGlucide() + "\n" +
                                    "Zaharuri: " + food.getZaharuri() + "\n" +
                                    "Fibre: " + food.getFibre() + "\n" +
                                    "Proteine: " + food.getProteine() + "\n" +
                                    "Sare: " + food.getSare()
                    )
                    .setPositiveButton("Close", null)
                    .show();
        }
    }
}
