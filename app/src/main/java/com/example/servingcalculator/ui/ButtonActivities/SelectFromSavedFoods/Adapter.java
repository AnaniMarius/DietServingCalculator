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

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.DataAccessObjectFood;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    ArrayList<Food> foodsList = new ArrayList<>();

    public Adapter(ArrayList<Food> foodsList, RecyclerViewInterface recyclerViewInterface) {
        this.foodsList = foodsList;
        this.recyclerViewInterface=recyclerViewInterface;
    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_foods_item_view, parent, false);
        return new AdapterViewHolder(view, this, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {
        holder.foodName.setText(foodsList.get(position).getNume());

//        if (position == selectedPosition) {
//            holder.itemView.setBackgroundColor(Color.LTGRAY);  // Highlight the selected item
//        } else {
//            holder.itemView.setBackgroundColor(Color.TRANSPARENT);  // Default appearance
//        }
    }

    @Override
    public int getItemCount() {
        return foodsList.size();
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder {
        TextView foodName;
        private final Adapter adapter;

        public AdapterViewHolder(@NonNull View itemView, Adapter adapter, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            this.adapter = adapter;
            foodName = itemView.findViewById(R.id.SFName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
            itemView.setOnClickListener(view -> {

                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle("Confirmation")
                            .setMessage("Are you sure you want to eat this food?")
                            .setPositiveButton("Yes", (dialog, which) -> {
//                                AppDatabase db = Room.databaseBuilder(
//                                        itemView.getContext(),
//                                        AppDatabase.class,
//                                        "Food-database"
//                                ).build();
//                                DataAccessObjectFood FoodDao = db.getData();
//                                AteFood ateFood=new AteFood();
//                                ateFood = (AteFood) adapter.foodsList.get(getAdapterPosition()).clone();
//                                AteFood finalAteFood = ateFood;
//                                new Thread(() -> {
//                                    FoodDao.insertAteFood(finalAteFood);
//                                }).start();
                            })
                            .setNegativeButton("No", null)
                            .show();

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
