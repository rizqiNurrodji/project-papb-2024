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

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btRoti, btDaging, btPelengkap, btSaus, selectedButton;
    private ImageButton btBack;
    private FragmentManager fragmentManager;
    private BottomSheetDialog bottomSheetDialog;
    private ArrayList<pilihanBahanModel> pilihanBahanModelArrayList = new ArrayList<>();
    private rvPilihanBahanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        initBottomSheet();

        btRoti = findViewById(R.id.btRoti);
        btDaging = findViewById(R.id.btDaging);
        btPelengkap = findViewById(R.id.btPelengkap);
        btSaus = findViewById(R.id.btSaus);
        btBack = findViewById(R.id.btBack);

        // Set initial selected button
        selectedButton = null;

        // Set listener untuk tombol bahan
        btRoti.setOnClickListener(view -> handleButtonClick(btRoti, "listPilihanRoti.php"));
        btDaging.setOnClickListener(view -> handleButtonClick(btDaging, "listPilihanDaging.php"));
        btPelengkap.setOnClickListener(view -> handleButtonClick(btPelengkap, "listPilihanPelengkap.php"));
        btSaus.setOnClickListener(view -> handleButtonClick(btSaus, "listPilihanSaus.php"));

        // Set listener untuk tombol kembali
        btBack.setOnClickListener(view -> handleBackAction());

        // Load fragmentMain sebagai fragment awal
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new fragmentMain());
        fragmentTransaction.commit();
    }

    private void initBottomSheet() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottomsheet_pilihan_bahan, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        // Inisialisasi RecyclerView
        RecyclerView recyclerView = bottomSheetView.findViewById(R.id.rvPilihanBahan);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Inisialisasi adapter
        adapter = new rvPilihanBahanAdapter(this, pilihanBahanModelArrayList);

        // Set adapter ke RecyclerView
        recyclerView.setAdapter(adapter);
    }

    private void handleButtonClick(Button button, String endpoint) {
        // Update button colors
        if (selectedButton != null) {
            selectedButton.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        }
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.btYellow));
        selectedButton = button;

        // Pindah ke fragmentPilihanBahan
        if (!(fragmentManager.findFragmentById(R.id.fragment_container) instanceof fragmentPilihanBahan)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new fragmentPilihanBahan());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        // Fetch data and show BottomSheet
        fetchDataFromServer(endpoint);
        bottomSheetDialog.show();

    }

    private void fetchDataFromServer(String endpoint) {
        String baseUrl = "http://192.168.1.27/projectPAM/";
        String url = baseUrl + endpoint;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    pilihanBahanModelArrayList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject item = response.getJSONObject(i);
                            String nama = item.getString("nama");
                            String gambarUrl = item.getString("image");
                            pilihanBahanModelArrayList.add(new pilihanBahanModel(nama, gambarUrl));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                },
                error -> error.printStackTrace());

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

    private void handleBackAction() {
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }
}
