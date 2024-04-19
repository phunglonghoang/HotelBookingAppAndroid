package com.tutorial.travel.Activity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

public class BookingActivity extends AppCompatActivity {
    TextView checkInTxt, checkOutTxt, countPriceTxt;
    private String hotelName;
    private String hotelLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Lấy tên người dùng từ SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        // Lấy reference của các TextView từ layout
        TextView userNameTxt = findViewById(R.id.userNameTxt);
        checkInTxt = findViewById(R.id.checkInTxt);
        checkOutTxt = findViewById(R.id.checkOutTxt);
        TextView roomNameTxt = findViewById(R.id.roomNameTxt);
        TextView priceTxt = findViewById(R.id.priceTxt);
        countPriceTxt = findViewById(R.id.countPriceTxt); // TextView để hiển thị tổng giá tiền

        // Nhận dữ liệu từ Intent
        int roomId = getIntent().getIntExtra("roomId", -1);
        int hotelId = getIntent().getIntExtra("hotelId", -1);
        Log.d("", "onCreate: ma khach sạn"+ hotelId);
        double price = getIntent().getDoubleExtra("price", 0.0);
        String roomName = getIntent().getStringExtra("roomName");
        String checkInDate = getIntent().getStringExtra("checkInDate");
        String checkOutDate = getIntent().getStringExtra("checkOutDate");

        // Đặt giá trị cho TextView của tên người dùng từ SharedPreferences
        userNameTxt.setText(username);

        // Đặt giá trị cho các TextView hiển thị thông tin đặt phòng
        checkInTxt.setText(checkInDate);
        checkOutTxt.setText(checkOutDate);
        roomNameTxt.setText(roomName);
        priceTxt.setText(String.format("%s VND", price));

        // Tính số ngày giữa hai ngày nhận và trả phòng
        int numberOfDays = DateUtils.calculateNumberOfDays(checkInDate, checkOutDate);

        // Tính tổng giá tiền
        double totalPrice = price * numberOfDays;

        // Đặt giá trị cho TextView hiển thị tổng giá tiền
        countPriceTxt.setText(String.format("%s VND", totalPrice));
        getHotelData(hotelId);

    }
    private void getHotelData(int hotelId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Truy vấn cơ sở dữ liệu để lấy thông tin của khách sạn
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HOTEL +
                " WHERE " + DatabaseHelper.COLUMN_HOTEL_ID + " = ?", new String[]{String.valueOf(hotelId)});

        // Kiểm tra xem có dữ liệu trả về không
        if (cursor != null && cursor.moveToFirst()) {
            try {
                // Lấy chỉ số của cột trong Cursor
                int hotelNameIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME);
                int locationIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION);

                // Lấy dữ liệu từ Cursor
                hotelName = cursor.getString(hotelNameIndex);
                hotelLocation = cursor.getString(locationIndex);

                // Gọi phương thức để cập nhật giao diện với thông tin của khách sạn
                updateUI();
            } catch (IllegalArgumentException e) {
                // Xử lý ngoại lệ nếu cột không tồn tại trong Cursor
                e.printStackTrace();
            }
        }

        // Đóng Cursor và Database khi đã sử dụng xong
        cursor.close();
        db.close();
    }


    private void updateUI() {
        // Ánh xạ các TextView từ layout
        TextView hotelNameTxt = findViewById(R.id.hotelNameTxt);
        TextView locationTxt = findViewById(R.id.locationTxt);

        // Đặt giá trị cho TextView hiển thị tên và vị trí của khách sạn
        hotelNameTxt.setText(hotelName);
        locationTxt.setText(hotelLocation);
    }
}
