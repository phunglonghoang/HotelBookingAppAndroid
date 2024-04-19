package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tutorial.travel.R;
import com.tutorial.travel.controller.LoginActivity;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main);
        CardView homeCardView = findViewById(R.id.homeCardView);

        homeCardView.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AdminMainActivity.this);

            builder.setTitle("Xác nhận đăng xuất");
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
            builder.setPositiveButton("Đăng xuất ", (dialog, which) -> {
                Intent intent = new Intent(AdminMainActivity.this, LoginActivity.class );
                startActivity(intent);
                finish();
            });

            builder.setNegativeButton("Hủy", (dialog, which) -> {
               dialog.dismiss();
            });

            AlertDialog dialog = builder.create();
            dialog.show();


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
            Intent intent2 = new Intent(AdminMainActivity.this, DonateAdmin.class);
            startActivity(intent2);
            finish();
        });

        CardView bookingCardView = findViewById(R.id.bookingCardView);
        bookingCardView.setOnClickListener(v -> {
            // Chuyển hướng đến trang booking
            Intent intent3 = new Intent(AdminMainActivity.this, Booking_Management.class);
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
            Intent intent5 = new Intent(AdminMainActivity.this, AdminManagementHotel.class);

            startActivity(intent5);
            finish();
        });
    }
}