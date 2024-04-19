package com.tutorial.travel.AdminActivity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.Booking;

public class ListBookingAdmin extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private BookingListAdapter bookingListAdapter;
    private List<Booking> bookingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_booking_admin);

        databaseHelper = new DatabaseHelper(this);

        // Ánh xạ các thành phần giao diện
        ListView bookingListView = findViewById(R.id.bookingListView);

        // Khởi tạo danh sách booking và adapter
        bookingList = new ArrayList<>();
        bookingListAdapter = new BookingListAdapter(this, R.layout.booking_list_item, bookingList);
        bookingListView.setAdapter(bookingListAdapter);

        // Load danh sách booking từ cơ sở dữ liệu
        loadBookingList();
    }

    private void loadBookingList() {
        // Mở cơ sở dữ liệu để đọc
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Truy vấn danh sách booking từ bảng booking
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_BOOKING, null);


        // Duyệt qua các dòng trong kết quả truy vấn
        if (cursor.moveToFirst()) {
            do {
                // Đọc thông tin từ cursor và thêm vào danh sách booking
                Booking booking = new Booking();
                booking.setId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOKING_ID)));
                booking.setRoomId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_ID_FK)));
                booking.setUserId(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USER_ID_FK)));
                booking.setCheckInDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHECK_IN_DATE)));
                booking.setCheckOutDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHECK_OUT_DATE)));
                booking.setIsConfirmed(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IS_CONFIRMED)));

                // Thêm booking vào danh sách
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }

        // Đóng cursor và cơ sở dữ liệu
        cursor.close();
        db.close();

        // Cập nhật adapter sau khi load dữ liệu
        bookingListAdapter.notifyDataSetChanged();
    }
}