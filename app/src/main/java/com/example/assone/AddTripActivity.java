package com.example.assone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class AddTripActivity extends AppCompatActivity {

    EditText edtName, edtLocation,edtPrice, edtDate;
    Button btnSave;

    boolean isEdit = false;
    Trip currentTrip;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        edtName = findViewById(R.id.edtTripName);
        edtPrice = findViewById(R.id.edtTripPrice);
        edtLocation = findViewById(R.id.edtTripLocation);
        edtDate = findViewById(R.id.edtTripDate);
        btnSave = findViewById(R.id.btnSaveTrip);

        // CHECK IF EDIT
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);

        if (isEdit) {
            currentTrip = (Trip) intent.getSerializableExtra("trip");
            position = intent.getIntExtra("position", -1);

            edtName.setText(currentTrip.name);
            edtLocation.setText(currentTrip.location);
            edtDate.setText(currentTrip.date);
        }

        btnSave.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String location = edtLocation.getText().toString();
            double price = Double.parseDouble(edtPrice.getText().toString());
            String date = edtDate.getText().toString();

            Trip trip = new Trip(name, location, price, date);

            Intent result = new Intent();
            result.putExtra("trip", trip);
            if (isEdit) result.putExtra("position", position);
            setResult(RESULT_OK, result);
            finish();

        });
    }
}
