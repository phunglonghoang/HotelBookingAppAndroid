package com.tutorial.travel.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;

public class BookingActivity extends AppCompatActivity {
    TextView checkInTxt, checkOutTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Hiển thị dữ liệu trên giao diện
        TextView userNameTxt = findViewById(R.id.userNameTxt);
        TextView checkInTxt = findViewById(R.id.checkInTxt);
        TextView checkOutTxt = findViewById(R.id.checkOutTxt);
        TextView roomNameTxt = findViewById(R.id.roomNameTxt);
        TextView priceTxt = findViewById(R.id.priceTxt);

        // Nhận dữ liệu từ Intent (Nếu bạn cần)
        int roomId = getIntent().getIntExtra("roomId", -1);
        int hotelId = getIntent().getIntExtra("hotelId", -1);
        double price = getIntent().getDoubleExtra("price", 0.0);
        String roomName = getIntent().getStringExtra("roomName");

        // Đặt giá trị cho TextView của tên người dùng từ SharedPreferences
        userNameTxt.setText(username);

        String checkInDate = getIntent().getStringExtra("checkInDate");
        String checkOutDate = getIntent().getStringExtra("checkOutDate");

        checkInTxt = findViewById(R.id.checkInTxt);
        checkOutTxt = findViewById(R.id.checkOutTxt);
        checkInTxt.setText(checkInDate);
        checkOutTxt.setText(checkOutDate);
        roomNameTxt.setText(roomName);

    }
}
