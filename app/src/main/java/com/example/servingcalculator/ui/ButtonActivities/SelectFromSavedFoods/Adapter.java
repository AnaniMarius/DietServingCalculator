package com.example.servingcalculator.ui.ButtonActivities.SelectFromSavedFoods;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.servingcalculator.AteFood;
import com.example.servingcalculator.Database.AppDatabase;
import com.example.servingcalculator.Database.AteFoodsDatabase.AteFoodsDAO;
import com.example.servingcalculator.Database.AteFoodsDatabase.AteFoodsDatabase;
import com.example.servingcalculator.Database.DataAccessObjectFood;
import com.example.servingcalculator.Food;
import com.example.servingcalculator.R;

import java.time.LocalDateTime;
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

                // Create an EditText widget with numeric input type
                EditText input = new EditText(itemView.getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("Quantity in grams");

                new AlertDialog.Builder(itemView.getContext())
                        .setTitle("Input Value")
                        .setMessage("Please enter the quantity of eaten food:")
                        .setView(input)  // Set the EditText as the view of the AlertDialog
                        .setPositiveButton("Yes", (dialog, which) -> {

                            // Get the numeric value from the EditText
                            int numberValue;
                            try {
                                numberValue = Integer.parseInt(input.getText().toString());
                            } catch (NumberFormatException e) {
                                numberValue = 0;  // Default value if user does not enter a valid number
                            }

                            AteFoodsDatabase db = Room.databaseBuilder(
                                    itemView.getContext(),
                                    AteFoodsDatabase.class,
                                    "AteFoods-database"
                            ).build();

                            AteFoodsDAO FoodDao = db.getData();
                            AteFood ateFood=new AteFood(adapter.foodsList.get(getAdapterPosition()).getNume(),adapter.foodsList.get(getAdapterPosition()).getValoareEnergetica(),adapter.foodsList.get(getAdapterPosition()).getGrasimi(),adapter.foodsList.get(getAdapterPosition()).getAcizi(),adapter.foodsList.get(getAdapterPosition()).getGlucide(),adapter.foodsList.get(getAdapterPosition()).getZaharuri(),adapter.foodsList.get(getAdapterPosition()).getFibre(),adapter.foodsList.get(getAdapterPosition()).getProteine(),adapter.foodsList.get(getAdapterPosition()).getSare());
                            //ateFood = (AteFood) adapter.foodsList.get(getAdapterPosition()).clone();
                            ateFood.setCantitate(numberValue);
                            ateFood.setData(LocalDateTime.now());

                            ateFood.setValoareEnergetica((numberValue*ateFood.getValoareEnergetica())/100);
                            ateFood.setGrasimi((numberValue*ateFood.getGrasimi())/100);
                            ateFood.setAcizi((numberValue*ateFood.getAcizi())/100);
                            ateFood.setGlucide((numberValue*ateFood.getGlucide())/100);
                            ateFood.setZaharuri((numberValue*ateFood.getZaharuri())/100);
                            ateFood.setFibre((numberValue*ateFood.getFibre())/100);
                            ateFood.setProteine((numberValue*ateFood.getProteine())/100);
                            ateFood.setSare((numberValue*ateFood.getSare())/100);

                            AteFood finalAteFood = ateFood;

                            new AlertDialog.Builder(itemView.getContext())
                                    .setTitle("Food Info per "+numberValue+"g")
                                    .setMessage(
                                            "Name: " + finalAteFood.getNume() + "\n" +
                                                    "Calorii: " + finalAteFood.getValoareEnergetica() + "\n" +
                                                    "Grasimi: " + finalAteFood.getGrasimi() + "\n" +
                                                    "Acizi: " + finalAteFood.getAcizi() + "\n" +
                                                    "Glucide: " + finalAteFood.getGlucide() + "\n" +
                                                    "Zaharuri: " + finalAteFood.getZaharuri() + "\n" +
                                                    "Fibre: " + finalAteFood.getFibre() + "\n" +
                                                    "Proteine: " + finalAteFood.getProteine() + "\n" +
                                                    "Sare: " + finalAteFood.getSare()
                                    )
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            new Thread(() -> {
                                                FoodDao.insertAteFood(finalAteFood);
                                            }).start();
                                        }
                                    })
                                    .setNegativeButton("No",null)
                                    .show();
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
