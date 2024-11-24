package com.example.bwurger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;

public class fragmentBahanTerpilih extends Fragment {

    private ArrayList<pilihanBahanModel> selectedBahanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private rvBahanTerpilihAdapter adapter;
    private Button btSelesai;
    private bwurgerDatabase db;  // Inisialisasi database

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan_terpilih, container, false);

        recyclerView = view.findViewById(R.id.rvBahanTerpilih);
        btSelesai = view.findViewById(R.id.btSelesai);

        // Set up RecyclerView untuk menampilkan bahan terpilih
        adapter = new rvBahanTerpilihAdapter(getActivity(), selectedBahanList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        // Inisialisasi database Room
        db = Room.databaseBuilder(getActivity(), bwurgerDatabase.class, "bahan_terpilih_database").build();

        // Set listener untuk tombol selesai
        btSelesai.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Bahan selesai dipilih", Toast.LENGTH_SHORT).show();
            saveBahanToDatabase();  // Menyimpan bahan ke database
        });

        return view;
    }

    // Method untuk menambah bahan yang dipilih ke dalam daftar
    public void addSelectedBahan(pilihanBahanModel bahan) {
        selectedBahanList.add(bahan);
        adapter.notifyDataSetChanged();  // Notifikasi perubahan pada adapter
        Toast.makeText(getActivity(), "Bahan Terpilih: " + bahan.getNamaUrl(), Toast.LENGTH_SHORT).show();
    }

    // Method untuk menyimpan semua bahan yang dipilih ke dalam database
    private void saveBahanToDatabase() {
        new Thread(() -> {
            for (pilihanBahanModel bahan : selectedBahanList) {
                bahanTerpilihEntity bahanEntity = new bahanTerpilihEntity(bahan.getNamaUrl(), bahan.getGambarUrl());
                db.bahanTerpilihDao().insert(bahanEntity);  // Menyimpan bahan ke database

                Log.d("BahanDisimpan", "Menyimpan Bahan: " + bahan.getNamaUrl() + ", Gambar URL: " + bahan.getGambarUrl());
            }
            // Menampilkan pesan setelah data disimpan
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Burger Anda telah disimpan!", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }
}
