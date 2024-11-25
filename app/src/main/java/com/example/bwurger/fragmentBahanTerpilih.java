package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragmentBahanTerpilih extends Fragment {

    private ArrayList<pilihanBahanModel> selectedBahanList = new ArrayList<>();
    private RecyclerView recyclerView;
    private rvBahanTerpilihAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bahan_terpilih, container, false);

        recyclerView = view.findViewById(R.id.rvBahanTerpilih);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new rvBahanTerpilihAdapter(getContext(), selectedBahanList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void updateSelectedBahanList(ArrayList<pilihanBahanModel> selectedBahanList) {
        this.selectedBahanList.clear(); // Bersihkan list sebelumnya
        this.selectedBahanList.addAll(selectedBahanList); // Tambahkan data baru
        adapter.notifyDataSetChanged(); // Beritahu adapter tentang perubahan
    }

}
