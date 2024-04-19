package com.tutorial.travel.AdminActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.controller.LoginActivity;
import com.tutorial.travel.model.Booking;
import com.tutorial.travel.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class reservation_summary_manager extends AppCompatActivity {
    SQLiteDatabase sqLiteDatabaseObj;
    EditText editTextDate;
    Button buttonLogOut, buttonHome, buttonListView;
    ListView listView;
    BookingListAdapter adapter;
    DatabaseHelper databaseHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_summary_manager);

        buttonLogOut = findViewById(R.id.button_logout);
        buttonHome = findViewById(R.id.button_home);
        buttonListView = findViewById(R.id.buttonListView);
        editTextDate = findViewById(R.id.editTextDate);
        listView = findViewById(R.id.listViewBookingAdmin);

        databaseHelper = new DatabaseHelper(this);

        buttonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = editTextDate.getText().toString().trim();
                if (date.isEmpty()) {
                    Toast.makeText(reservation_summary_manager.this, "Please fill the reservation date!", Toast.LENGTH_LONG).show();
                    return;
                }
                loadBookingsByDate(date);
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reservation_summary_manager.this, Booking_Management.class);
                startActivity(intent);
            }
        });

        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(reservation_summary_manager.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadBookingsByDate(String date) {
        // Load bookings from the database for the given date
        List<Booking> bookings = databaseHelper.getBookingsByDate(date);

        // Check if there are any bookings
        if (bookings.isEmpty()) {
            Toast.makeText(reservation_summary_manager.this, "No bookings found for the selected date!", Toast.LENGTH_LONG).show();
            return;
        }

        // Initialize the adapter with the list of bookings
        adapter = new BookingListAdapter(this, R.layout.list_item_layout, bookings);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);
    }
}
