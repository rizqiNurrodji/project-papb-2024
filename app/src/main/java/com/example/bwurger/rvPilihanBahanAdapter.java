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

public class rvPilihanBahanAdapter extends RecyclerView.Adapter<rvPilihanBahanAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<pilihanBahanModel> pilihanBahanModelArrayList;

    public rvPilihanBahanAdapter(Context context, ArrayList<pilihanBahanModel> pilihanBahanModelArrayList) {
        this.context = context;
        this.pilihanBahanModelArrayList = pilihanBahanModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Get the current item (roti) from the list
        pilihanBahanModel currentBahan = pilihanBahanModelArrayList.get(position);

        // Use Glide to load the image URL into the ImageButton
        Glide.with(context)
                .load(currentBahan.getGambarUrl())  // URL of the image
                .into(holder.btBahanPilihan);  // Target ImageButton

        // Set the name of the roti
        holder.tvBahanPilihan.setText(currentBahan.getNamaUrl());

        // Set up a click listener for the ImageButton
        holder.btBahanPilihan.setOnClickListener(v -> {
            Toast.makeText(context, "Anda memilih " + currentBahan.getNamaUrl(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return pilihanBahanModelArrayList.size();
    }

    // ViewHolder class to hold views for each item
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageButton btBahanPilihan;
        TextView tvBahanPilihan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views
            btBahanPilihan = itemView.findViewById(R.id.btBahanPilihan);
            tvBahanPilihan = itemView.findViewById(R.id.tvBahanPilihan);
        }
    }
}
