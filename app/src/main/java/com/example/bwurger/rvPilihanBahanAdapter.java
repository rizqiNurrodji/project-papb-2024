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
    private OnBahanClickListener onBahanClickListener;

    // Interface untuk mendengarkan klik bahan
    public interface OnBahanClickListener {
        void onBahanClick(pilihanBahanModel bahan);
    }

    public rvPilihanBahanAdapter(Context context, ArrayList<pilihanBahanModel> pilihanBahanModelArrayList, OnBahanClickListener onBahanClickListener) {
        this.context = context;
        this.pilihanBahanModelArrayList = pilihanBahanModelArrayList;
        this.onBahanClickListener = onBahanClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        pilihanBahanModel currentBahan = pilihanBahanModelArrayList.get(position);

        // Use Glide to load image into the ImageButton
        Glide.with(context)
                .load(currentBahan.getGambarUrl())
                .into(holder.btBahanPilihan);

        holder.tvBahanPilihan.setText(currentBahan.getNamaUrl());

        // Set up the click listener for the ImageButton
        holder.btBahanPilihan.setOnClickListener(v -> {
            if (onBahanClickListener != null) {
                onBahanClickListener.onBahanClick(currentBahan);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pilihanBahanModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageButton btBahanPilihan;
        TextView tvBahanPilihan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            btBahanPilihan = itemView.findViewById(R.id.btBahanPilihan);
            tvBahanPilihan = itemView.findViewById(R.id.tvBahanPilihan);
        }
    }
}
