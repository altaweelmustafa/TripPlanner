package com.example.assone;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TripAdapter adapter;
    ArrayList<Trip> tripList = new ArrayList<>();
    FloatingActionButton fabAdd;

    private ActivityResultLauncher<Intent> addTripLauncher;
    private ActivityResultLauncher<Intent> editTripLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerTrips);
        fabAdd = findViewById(R.id.fabAddTrip);

        loadTrips();

        adapter = new TripAdapter(this, tripList, new TripAdapter.OnTripClick() {
            @Override
            public void onEdit(Trip trip, int position) {
                Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
                intent.putExtra("isEdit", true);
                intent.putExtra("trip", trip);
                intent.putExtra("position", position);
                editTripLauncher.launch(intent);
            }

            @Override
            public void onLongClick(int position) {
                tripList.remove(position);
                adapter.notifyItemRemoved(position);
                saveTrips();
            }

            @Override
            public void onClick(Trip trip) {
                // Open TodoActivity
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTripActivity.class);
            addTripLauncher.launch(intent);
        });

        // Register ActivityResultLaunchers
        addTripLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleTripResult(result, false)
        );

        editTripLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> handleTripResult(result, true)
        );
    }

    private void handleTripResult(ActivityResult result, boolean isEdit) {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Trip trip = (Trip) result.getData().getSerializableExtra("trip");

            if (!isEdit) {
                tripList.add(trip);
                adapter.notifyItemInserted(tripList.size() - 1);
            } else {
                int position = result.getData().getIntExtra("position", -1);
                if (position >= 0) {
                    tripList.set(position, trip);
                    adapter.notifyItemChanged(position);
                }
            }
            saveTrips();
        }
    }

    private void saveTrips() {
        SharedPreferences prefs = getSharedPreferences("trips", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("trip_list", new Gson().toJson(tripList));
        editor.apply();
    }

    private void loadTrips() {
        SharedPreferences prefs = getSharedPreferences("trips", MODE_PRIVATE);
        String json = prefs.getString("trip_list", null);
        if (json != null) {
            Type type = new TypeToken<ArrayList<Trip>>() {}.getType();
            tripList = new Gson().fromJson(json, type);
        }
    }
}
