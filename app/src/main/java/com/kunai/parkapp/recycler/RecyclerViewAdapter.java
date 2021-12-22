package com.kunai.parkapp.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kunai.parkapp.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    ArrayList<String> carPlateList;
    ArrayList<String> carBrandList;


    public RecyclerViewAdapter(ArrayList<String> carPlateList, ArrayList<String> carBrandList) {
        this.carPlateList = carPlateList;
        this.carBrandList = carBrandList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recyclerview_row_option,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.textPlate.setText(carPlateList.get(position));
        holder.textBrand.setText(carBrandList.get(position));
    }

    @Override
    public int getItemCount() {
        return carBrandList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textPlate,textBrand;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textBrand = itemView.findViewById(R.id.textBrand);
            textPlate = itemView.findViewById(R.id.textPlate);
        }
    }
}
