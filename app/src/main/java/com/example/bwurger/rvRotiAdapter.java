package com.example.bwurger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class rvRotiAdapter extends RecyclerView.Adapter<rvRotiAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<rotiModel> rotiModelArrayList;

    public rvRotiAdapter(Context context, ArrayList<rotiModel> rotiModelArrayList) {
        this.context = context;
        this.rotiModelArrayList = rotiModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_roti_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the current item (roti) from the list
        rotiModel currentRoti = rotiModelArrayList.get(position);

        // Use Glide to load the image URL into the ImageButton
        Glide.with(context)
                .load(currentRoti.getGambarUrl())  // URL of the image
                .into(holder.btRotiPilihan);  // Target ImageButton

        // Set the name of the roti
        holder.tvRotiPilihan.setText(currentRoti.getNamaUrl());

        // Set up a click listener for the ImageButton
        holder.btRotiPilihan.setOnClickListener(v -> {
            Toast.makeText(context, "Anda memilih " + currentRoti.getNamaUrl(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return rotiModelArrayList.size();
    }

    // ViewHolder class to hold views for each item
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton btRotiPilihan;
        TextView tvRotiPilihan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            btRotiPilihan = itemView.findViewById(R.id.btRotiPilihan);
            tvRotiPilihan = itemView.findViewById(R.id.tvNamaBun);
        }
    }
}
