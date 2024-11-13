package com.example.bwurger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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

public class PilihanBahanActivity extends AppCompatActivity {

    private Button btRoti;
    private BottomSheetDialog showPilihanRotiActivity;
    private ArrayList<rotiModel> rotiModelArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private rvRotiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);

        btRoti = findViewById(R.id.btRoti);
        showPilihanRotiActivity = new BottomSheetDialog(PilihanBahanActivity.this);

        // Inflate the BottomSheet layout and set it to the dialog
        View viewPilihanRoti = LayoutInflater.from(PilihanBahanActivity.this).inflate(R.layout.bottomsheet_pilihan_roti, null);
        showPilihanRotiActivity.setContentView(viewPilihanRoti);

        // Customize the button's appearance
        btRoti.setBackgroundColor(ContextCompat.getColor(PilihanBahanActivity.this, R.color.btYellow));

        // Set up RecyclerView
        recyclerView = viewPilihanRoti.findViewById(R.id.rvPilihanRoti);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Initialize adapter with empty list
        adapter = new rvRotiAdapter(this, rotiModelArrayList);
        recyclerView.setAdapter(adapter);

        btRoti.setOnClickListener(view -> showPilihanRotiActivity.show());

        // Fetch data from server
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {
        String url = "http://192.168.1.39/projectPAM/listPilihanRoti.php";

        // Ensure you are using a valid IP address or server URL
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
                        // Notify adapter after data is fetched
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // Adding request to Volley queue
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}
