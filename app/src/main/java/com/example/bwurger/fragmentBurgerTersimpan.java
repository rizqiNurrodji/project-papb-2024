package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class fragmentBurgerTersimpan extends Fragment {

    private RecyclerView rvBurgerTersimpan;
    private rvBurgerTersimpanAdapter adapter;
    private List<Burger> burgerList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_burger_tersimpan, container, false);

        rvBurgerTersimpan = view.findViewById(R.id.rvBurgerTersimpan);
        rvBurgerTersimpan.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Inisialisasi adapter dengan daftar burger kosong
        adapter = new rvBurgerTersimpanAdapter(getContext(), burgerList);
        rvBurgerTersimpan.setAdapter(adapter); // Set adapter ke RecyclerView

        loadSavedBurgers(); // Muat data burger dari Firebase

        return view;
    }

    private void loadSavedBurgers() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("burgers");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                burgerList.clear(); // Bersihkan daftar sebelum menambahkan yang baru
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Burger burger = snapshot.getValue(Burger.class);
                    if (burger != null) {
                        burgerList.add(burger); // Tambahkan burger ke daftar
                    }
                }
                adapter.notifyDataSetChanged(); // Beritahu adapter bahwa data telah berubah
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
}