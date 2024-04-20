package com.tutorial.travel.controller;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.BookingModel;

import java.util.List;

public class HistoryBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking);

        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Hiển thị lịch sử đặt phòng của người dùng
        displayBookingHistory(username);
    }

    private void displayBookingHistory(String username) {
        TextView historyTextView = findViewById(R.id.historyTextView);

        // Sử dụng DatabaseHelper để lấy danh sách lịch sử đặt phòng của người dùng
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        List<BookingModel> bookingHistory = dbHelper.getBookingHistoryByUsername(username);

        // Xây dựng chuỗi hiển thị lịch sử đặt phòng
        StringBuilder stringBuilder = new StringBuilder();
        for (BookingModel booking : bookingHistory) {
            stringBuilder.append("Room ID: ").append(booking.getRoomId()).append("\n");
            stringBuilder.append("Hotel Name: ").append(booking.getHotelName()).append("\n");
            stringBuilder.append("Hotel Location: ").append(booking.getHotelLocation()).append("\n");
            stringBuilder.append("Check-in Date: ").append(booking.getCheckInDate()).append("\n");
            stringBuilder.append("Check-out Date: ").append(booking.getCheckOutDate()).append("\n");
            stringBuilder.append("Payment Method: ").append(booking.getPaymentMethod()).append("\n");
            stringBuilder.append("Total Price: ").append(booking.getTotalPrice()).append("\n");
            stringBuilder.append("Is Confirmed: ").append(booking.getIsConfirmed()).append("\n\n");
        }

        // Hiển thị lịch sử đặt phòng trên TextView
        historyTextView.setText(stringBuilder.toString());
    }
}
