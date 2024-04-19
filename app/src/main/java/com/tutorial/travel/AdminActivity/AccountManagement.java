package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tutorial.travel.R;

public class AccountManagement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_management);
        CardView homeCardView = findViewById(R.id.homeCardView);
        homeCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManagement.this, AdminMainActivity.class );
            startActivity(intent);
            finish();
        });

        CardView listUsersCardView;
        listUsersCardView = findViewById(R.id.listUserCardView);
        listUsersCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManagement.this, SearchGuestUser.class );
            startActivity(intent);
            finish();
        });

        CardView AdminaddUser;
        AdminaddUser = findViewById(R.id.AdminaddUser);
        AdminaddUser.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManagement.this, addUser.class );
            startActivity(intent);
            finish();
        });

        CardView BookingAdmin;
        BookingAdmin = findViewById(R.id.bookingAdminCardView);
        BookingAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(AccountManagement.this, ListUserAdmin.class );
            startActivity(intent);
            finish();
        });
    }
}