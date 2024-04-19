package com.tutorial.travel.AdminActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tutorial.travel.controller.LoginActivity;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.Booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tutorial.travel.R;
import java.util.List;

public class BookingListAdmin extends AppCompatActivity {
    Button userHome, logoutHotel;
    ListView listViewBookings;
    DatabaseHelper databaseHelper;
    ArrayAdapter<Booking> bookingListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_list_admin);

        userHome = findViewById(R.id.userHome);
        logoutHotel = findViewById(R.id.logoutHotel);

        userHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingListAdmin.this, AdminManagementHotel.class));
            }
        });

        logoutHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BookingListAdmin.this, LoginActivity.class));
            }
        });


        listViewBookings = findViewById(R.id.booking_listView);
        databaseHelper = new DatabaseHelper(this);

        List<Booking> bookings = databaseHelper.getAllBookings();

        bookingListAdapter = new ArrayAdapter<Booking>(this, android.R.layout.simple_list_item_2, android.R.id.text1, bookings){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(android.R.layout.simple_list_item_2, parent, false);
                }

                // Lấy thông tin của booking tại vị trí hiện tại
                Booking booking = getItem(position);

                // Hiển thị thông tin booking
                TextView textViewBookingInfo = convertView.findViewById(android.R.id.text1);
                TextView textViewBookingDate = convertView.findViewById(android.R.id.text2);

                if (booking != null) {
                    // Thay đổi cách hiển thị thông tin booking tại đây
                    String bookingInfo = "Booking ID: " + booking.getId() + "\nUser ID: " + booking.getUserId() + "\nRoom ID: " + booking.getRoomId();
                    textViewBookingInfo.setText(bookingInfo);
                    textViewBookingDate.setText("Check-in: " + booking.getCheckInDate() + "\nCheck-out: " + booking.getCheckOutDate());
                }

                return convertView;
            }
        };

        // Đặt adapter cho ListView
        listViewBookings.setAdapter(bookingListAdapter);
    }
}