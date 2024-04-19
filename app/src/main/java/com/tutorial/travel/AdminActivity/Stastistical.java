package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

public class Stastistical extends AppCompatActivity {
    private TextView priceCountTextView,userCountTextView, bookingCountTextView, hotelCountTextView, roomCountTextView;
//    private BarChart barChart;
    private DatabaseHelper databaseHelper;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stastistical);

        userCountTextView = findViewById(R.id.userCountTextView);
        bookingCountTextView = findViewById(R.id.bookingCountTextView);
        hotelCountTextView = findViewById(R.id.hotelCountTextView);
        roomCountTextView = findViewById(R.id.roomCountTextView);
        priceCountTextView = findViewById(R.id.priceCountTextView);
        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin thống kê và hiển thị
        displayStatistics();
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Stastistical.this, AdminMainActivity.class));
                finish();
            }
        });
    }

    private void displayStatistics() {
        // Lấy số lượng người dùng
        int userCount = getUserCount();
        userCountTextView.setText("Số lượng người dùng: " + userCount);

        // Lấy số lượng booking
        int bookingCount = getBookingCount();
        bookingCountTextView.setText("Số lượng booking: " + bookingCount);

        // Lấy số lượng khách sạn
        int hotelCount = getHotelCount();
        hotelCountTextView.setText("Số lượng khách sạn: " + hotelCount);

        // Lấy số lượng phòng
        int roomCount = getRoomCount();
        roomCountTextView.setText("Số lượng phòng: " + roomCount);

        double priceCount = sumRoomPrices();
        priceCountTextView.setText("Tổng số tiền kiếm được:" + priceCount + "$");

    }

    private int getUserCount() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_USERS, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }
    private double sumRoomPrices() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseHelper.COLUMN_PRICE + ") FROM " + DatabaseHelper.TABLE_ROOM, null);
        cursor.moveToFirst();
        double sum = cursor.getDouble(0);
        cursor.close();
        db.close();
        return sum;
    }

    private int getBookingCount() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_BOOKING, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    private int getHotelCount() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_HOTEL, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    private int getRoomCount() {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DatabaseHelper.TABLE_ROOM, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

}
