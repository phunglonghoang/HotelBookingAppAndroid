package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tutorial.travel.R;

public class Booking_Management extends AppCompatActivity {
    CardView homeBookingCardView;
    CardView listBookingCardView;
    CardView adminAddBookingCardView;
    CardView bookingAdminCardView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_booking_management);
        homeBookingCardView = findViewById(R.id.homeBookingCardView);
        listBookingCardView = findViewById(R.id.listBookingCardView);
        adminAddBookingCardView = findViewById(R.id.AdminaddBooking);
        bookingAdminCardView = findViewById(R.id.bookingAdminCardView);
        homeBookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking_Management.this, AdminMainActivity.class);
                startActivity(intent);
            }
        });

        listBookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking_Management.this, BookingListAdmin.class);
                startActivity(intent);
            }
        });

        adminAddBookingCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking_Management.this, AddBookingAdmin.class);
                startActivity(intent);
            }
        });

        bookingAdminCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Booking_Management.this, SearchBookingAdminView.class);
                startActivity(intent);
            }
        });
    }
}