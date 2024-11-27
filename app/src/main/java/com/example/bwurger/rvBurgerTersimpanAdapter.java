package com.example.bwurger;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class rvBurgerTersimpanAdapter extends RecyclerView.Adapter<rvBurgerTersimpanAdapter.ViewHolder> {

    private Context context;
    private List<Burger> burgerList;

    public rvBurgerTersimpanAdapter(Context context, List<Burger> burgerList) {
        this.context = context;
        this.burgerList = burgerList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_burger_tersimpan_rv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Burger burger = burgerList.get(position);
        holder.tvBurgerName.setText(burger.getName());
        holder.ivBurgerImage.setImageResource(burger.getImageResId());

        // Set listener for edit button
        holder.btEdit.setOnClickListener(v -> {
            // Logic to edit the burger
            editBurger(burger);
        });

        // Set listener for delete button
        holder.btHapus.setOnClickListener(v -> {
            // Logic to delete the burger
            deleteBurger(burger);
        });
    }

    @Override
    public int getItemCount() {
        return burgerList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBurgerName;
        ImageView ivBurgerImage;
        Button btEdit; // Tambahkan ini
        Button btHapus; // Tambahkan ini

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBurgerName = itemView.findViewById(R.id.tvNamaBurgerTersimpan);
            ivBurgerImage = itemView.findViewById(R.id.btBurgerTersimpan);
            btEdit = itemView.findViewById(R.id.btEdit); // Inisialisasi tombol edit
            btHapus = itemView.findViewById(R.id.btHapus); // Inisialisasi tombol hapus
        }
    }

    private void editBurger(Burger burger) {
    }


    private void deleteBurger(Burger burger) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("burgers");
        databaseReference.orderByChild("name").equalTo(burger.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue(); // Menghapus burger dari database
                }
                // Setelah menghapus dari database, Anda mungkin ingin memperbarui tampilan atau daftar.
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}