package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btRoti;
    private ImageButton btBack;
    private FragmentManager fragmentManager;
    private BottomSheetDialog showPilihanRotiActivity;
    private ArrayList<rotiModel> rotiModelArrayList = new ArrayList<>();
    private rvRotiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        // Inisialisasi BottomSheetDialog untuk pilihan roti
        showPilihanRotiActivity = new BottomSheetDialog(MainActivity.this);
        View viewPilihanRoti = LayoutInflater.from(this).inflate(R.layout.bottomsheet_pilihan_roti, null);
        showPilihanRotiActivity.setContentView(viewPilihanRoti);

        // Inisialisasi RecyclerView untuk menampilkan daftar roti di BottomSheet
        RecyclerView recyclerView = viewPilihanRoti.findViewById(R.id.rvPilihanRoti);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new rvRotiAdapter(this, rotiModelArrayList);
        recyclerView.setAdapter(adapter);

        btRoti = findViewById(R.id.btRoti);
        btBack = findViewById(R.id.btBack);

        // Set listener untuk tombol btRoti agar membuka BottomSheet dan berpindah ke fragmentPilihanBahan
        btRoti.setOnClickListener(view -> {
            openPilihanBahanFragment(); // Pindah ke fragmentPilihanBahan
            showPilihanRotiActivity.show(); // Tampilkan BottomSheet dialog
            fetchDataFromServer();// Ambil data dari server
            btRoti.setBackgroundColor(getResources().getColor(R.color.btYellow));
        });

        // Set listener untuk tombol btBack agar kembali ke fragment sebelumnya
        btBack.setOnClickListener(view -> handleBackAction());

        // Load fragmentMain sebagai fragment awal
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new fragmentMain());
        fragmentTransaction.commit();
    }

    private void openPilihanBahanFragment() {
        // Pindah ke fragmentPilihanBahan jika belum tampil
        if (!(fragmentManager.findFragmentById(R.id.fragment_container) instanceof fragmentPilihanBahan)) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new fragmentPilihanBahan());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void handleBackAction() {
        // Kembali ke fragment sebelumnya jika ada di back stack, atau keluar aplikasi
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            finish();
        }
    }

    private void fetchDataFromServer() {
        String url = "http://192.168.1.39/projectPAM/listPilihanRoti.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        rotiModelArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject rotiObj = response.getJSONObject(i);
                                String nama = rotiObj.getString("nama");
                                String gambarUrl = rotiObj.getString("image");
                                rotiModelArrayList.add(new rotiModel(nama, gambarUrl));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged(); // Perbarui adapter setelah data diambil
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest); // Pastikan konteksnya adalah Activity
    }
}