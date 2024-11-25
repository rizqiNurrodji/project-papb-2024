package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btRoti, btDaging, btPelengkap, btSaus;
    private ImageButton btBack;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList<pilihanBahanModel> pilihanBahanModelArrayList = new ArrayList<>();
    private rvPilihanBahanAdapter adapter;
    private Button lastSelectedButton; // Menyimpan tombol terakhir yang dipilih
    private ArrayList<pilihanBahanModel> selectedBahanList = new ArrayList<>(); // Daftar bahan yang dipilih
    private fragmentBahanTerpilih fragmentBahanTerpilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btRoti = findViewById(R.id.btRoti);
        btDaging = findViewById(R.id.btDaging);
        btPelengkap = findViewById(R.id.btPelengkap);
        btSaus = findViewById(R.id.btSaus);
        btBack = findViewById(R.id.btBack);

        // Inisialisasi adapter
        adapter = new rvPilihanBahanAdapter(this, pilihanBahanModelArrayList, new rvPilihanBahanAdapter.OnBahanClickListener() {
            @Override
            public void onBahanClick(pilihanBahanModel bahan) {
                addBahanToSelected(bahan);
            }
        });

        // Load fragment awal
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new fragmentMain());
        fragmentTransaction.commit();

        // Klik button untuk memuat data kategori tertentu
        btRoti.setOnClickListener(view -> handleButtonClick(btRoti, "Roti"));
        btDaging.setOnClickListener(view -> handleButtonClick(btDaging, "Daging"));
        btPelengkap.setOnClickListener(view -> handleButtonClick(btPelengkap, "Pelengkap"));
        btSaus.setOnClickListener(view -> handleButtonClick(btSaus, "Saus"));
    }

    private void handleButtonClick(Button selectedButton, String kategori) {
        // Ubah warna tombol terakhir ke default jika ada
        if (lastSelectedButton != null) {
            lastSelectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.botBarGreen));
        }

        // Ubah warna tombol yang dipilih ke btYellow
        selectedButton.setBackgroundColor(ContextCompat.getColor(this, R.color.btYellow));
        lastSelectedButton = selectedButton; // Simpan tombol yang baru saja dipilih

        // Load kategori bahan
        loadKategori(kategori);

        // Tampilkan fragment_bahan_terpilih dan BottomSheet
        loadFragmentBahanTerpilih();
        showBottomSheet();
    }

    private void loadKategori(String kategori) {
        pilihanBahanModelArrayList.clear();

        switch (kategori) {
            case "Roti":
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Roti Brioche", "bun_brioche"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Roti Normal", "bun_normal"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Roti Pretzel", "bun_pretzel"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Roti Gandum", "bun_wheat"));
                break;
            case "Daging":
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Patty Ayam", "patty_chicken"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Patty Vegan", "patty_vegan"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Patty Normal", "patty_normal"));
                break;
            case "Pelengkap":
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Lettuce", "pelengkap_lettuce"));
                break;
            case "Saus":
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Saus Mustard", "sauce_mustard"));
                pilihanBahanModelArrayList.add(new pilihanBahanModel("Saus Ketchup", "sauce_ketchup"));
                break;
        }

        adapter.notifyDataSetChanged();
    }

    private void loadFragmentBahanTerpilih() {
        if (fragmentBahanTerpilih == null) {
            fragmentBahanTerpilih = new fragmentBahanTerpilih();
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragmentBahanTerpilih);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void showBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View sheetView = LayoutInflater.from(this).inflate(R.layout.bottomsheet_pilihan_bahan, null);
        bottomSheetDialog.setContentView(sheetView);

        RecyclerView recyclerView = sheetView.findViewById(R.id.rvPilihanBahan);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);

        bottomSheetDialog.show();
    }

    // Method untuk menambah bahan yang dipilih
    public void addBahanToSelected(pilihanBahanModel bahan) {
        selectedBahanList.add(bahan);
        if (fragmentBahanTerpilih != null) {
            fragmentBahanTerpilih.updateSelectedBahanList(selectedBahanList);
        }
    }

}
