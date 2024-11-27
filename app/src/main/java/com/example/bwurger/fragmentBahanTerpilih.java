package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class fragmentBahanTerpilih extends Fragment {

    private static final String DBURL = "https://bwurger-p6-default-rtdb.asia-southeast1.firebasedatabase.app"; // URL database
    private Button btSelesai;
    private ArrayList<pilihanBahanModel> selectedBahanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private rvBahanTerpilihAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan_terpilih, container, false);

        btSelesai = view.findViewById(R.id.btSelesai);
        recyclerView = view.findViewById(R.id.rvBahanTerpilih);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new rvBahanTerpilihAdapter(getContext(), selectedBahanList);
        recyclerView.setAdapter(adapter);

        btSelesai.setOnClickListener(v -> saveBurgerToDatabase()); // Menyimpan burger saat tombol diklik

        return view;
    }

    public void updateSelectedBahanList(ArrayList<pilihanBahanModel> selectedBahanList) {
        this.selectedBahanList.clear();
        this.selectedBahanList.addAll(selectedBahanList);
        adapter.notifyDataSetChanged();
    }

    public void saveBurgerToDatabase() {
        if (!selectedBahanList.isEmpty()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance(DBURL).getReference("burgers");

            String burgerName = "Burger Custom " + System.currentTimeMillis(); // Menggunakan timestamp untuk unik

            // Buat daftar bahan yang dipilih
            ArrayList<String> ingredients = new ArrayList<>();
            for (pilihanBahanModel bahan : selectedBahanList) {
                ingredients.add(bahan.getGambar());
            }

            // Tentukan ID gambar burger dari drawable
            int imageResId = R.drawable.ic_burger;

            // Simpan ke database
            Burger burger = new Burger(burgerName, ingredients, imageResId);
            databaseReference.push().setValue(burger)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(getContext(), "Burger berhasil disimpan", Toast.LENGTH_SHORT).show();// Perbarui RecyclerView
                        selectedBahanList.clear();
                        adapter.notifyDataSetChanged();
                    });
        }
    }
}