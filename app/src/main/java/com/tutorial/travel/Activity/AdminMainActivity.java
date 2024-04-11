package com.tutorial.travel.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tutorial.travel.R;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);
        CardView homeCardView = findViewById(R.id.homeCardView);

        homeCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AdminMainActivity.this, MainActivity.class );
            startActivity(intent);
            finish();
        });
        CardView accountCardView = findViewById(R.id.accountCardView);
        accountCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang Account
            Intent intent1 = new Intent(AdminMainActivity.this, AccountManagement.class);
            startActivity(intent1);
            finish();
        });
        CardView billCardView = findViewById(R.id.billCardView);
        billCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang bill
            Intent intent2 = new Intent(AdminMainActivity.this, BillAdmin.class);
            startActivity(intent2);
            finish();
        });

        CardView bookingCardView = findViewById(R.id.bookingCardView);
        bookingCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang booking
            Intent intent3 = new Intent(AdminMainActivity.this, BookingAdmin.class);
            startActivity(intent3);
            finish();
        });

        CardView statisticalCardView = findViewById(R.id.statisticalCardView);
        statisticalCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang thống kê
            Intent intent4 = new Intent(AdminMainActivity.this, Stastistical.class);
            startActivity(intent4);
            finish();
        });

        CardView hotelCardView = findViewById(R.id.hotelCardView);
        hotelCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang hotel
            Intent intent5 = new Intent(AdminMainActivity.this, HotelAdmin.class);

            startActivity(intent5);
            finish();
        });
    }
}