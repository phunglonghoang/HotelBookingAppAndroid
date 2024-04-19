package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tutorial.travel.R;

public class AdminManagementHotel extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_management_hotel);

        CardView homeCardView = findViewById(R.id.homeCardView);
        homeCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagementHotel.this, AdminMainActivity.class );
            startActivity(intent);
            finish();
        });

        CardView themKSVIew = findViewById(R.id.themKSView);
        themKSVIew.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagementHotel.this, ThemKhachSan.class );
            startActivity(intent);
            finish();
        });

        CardView listHotelCardView = findViewById(R.id.listHotelCardView);
        listHotelCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagementHotel.this, Admin_View_RoomList.class );
            startActivity(intent);
            finish();
        });

        CardView addNewRoomTypeCardView = findViewById(R.id.addNewRoomTypeCardView);
        addNewRoomTypeCardView.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagementHotel.this, AddRoomType.class );
            startActivity(intent);
            finish();
        });
        CardView searchHotelAdmin = findViewById(R.id.SearchHotelCardView);
        searchHotelAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(AdminManagementHotel.this, SearchHotelAdmin.class );
            startActivity(intent);
            finish();
        });
    }
}