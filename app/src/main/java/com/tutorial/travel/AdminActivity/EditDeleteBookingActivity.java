package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.model.Booking;

public class EditDeleteBookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_booking);

        // Nhận dữ liệu của booking được chọn từ Intent
        Intent intent = getIntent();
        Booking selectedBooking = (Booking) intent.getSerializableExtra("SELECTED_BOOKING");

        // Hiển thị thông tin của booking trên giao diện
        if (selectedBooking != null) {
            TextView textViewBookingInfo = findViewById(R.id.textViewBookingInfo);
            TextView textViewBookingDate = findViewById(R.id.textViewBookingDate);

            String bookingInfo = "Booking ID: " + selectedBooking.getId() + "\nUser ID: " + selectedBooking.getUserId() + "\nRoom ID: " + selectedBooking.getRoomId();
            textViewBookingInfo.setText(bookingInfo);

            String bookingDate = "Check-in: " + selectedBooking.getCheckInDate() + "\nCheck-out: " + selectedBooking.getCheckOutDate();
            textViewBookingDate.setText(bookingDate);
        }
    }
}
