package com.tutorial.travel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
        Log.d("", "onCreate: ma khach sạn" + hotelId);
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

        RadioGroup paymentMethodRadioGroup = findViewById(R.id.paymentMethodRadioGroup);
        int selectedId = paymentMethodRadioGroup.getCheckedRadioButtonId();

        Button bookbtn = findViewById(R.id.bookbtn);
        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton directPaymentRadioButton = findViewById(R.id.directPaymentRadioButton);
                if (directPaymentRadioButton.isChecked()) {
                    saveBookingData(roomId, username, hotelName, hotelLocation, checkInDate, checkOutDate, "Thanh toán trực tiếp", totalPrice);
                    Toast.makeText(BookingActivity.this, "Đặt phòng thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BookingActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(BookingActivity.this, "Vui lòng chọn phương thức thanh toán trực tiếp", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveBookingData(int roomId, String username, String hotelName, String hotelLocation, String checkInDate, String checkOutDate, String paymentMethod, double totalPrice) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_ROOM_ID_FK, roomId);
        values.put(DatabaseHelper.COLUMN_USER_ID_FK, username); // Thay userId bằng giá trị tương ứng
        values.put(DatabaseHelper.COLUMN_HOTEL_NAME_BOOKING, hotelName);
        values.put(DatabaseHelper.COLUMN_HOTEL_LOCATION_BOOKING, hotelLocation);
        values.put(DatabaseHelper.COLUMN_CHECK_IN_DATE, checkInDate);
        values.put(DatabaseHelper.COLUMN_CHECK_OUT_DATE, checkOutDate);
        values.put(DatabaseHelper.COLUMN_PAYMENT_METHOD, paymentMethod);
        values.put(DatabaseHelper.COLUMN_TOTAL_AMOUNT, totalPrice);
        values.put(DatabaseHelper.COLUMN_IS_CONFIRMED, 0); // Giả sử đặt phòng chưa được xác nhận

        // Chèn dữ liệu vào bảng booking
        long newRowId = db.insert(DatabaseHelper.TABLE_BOOKING, null, values);

        db.close();
    }

    private void getHotelData(int hotelId) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

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

                updateUI();
            } catch (IllegalArgumentException e) {
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

        hotelNameTxt.setText(hotelName);
        locationTxt.setText(hotelLocation);
    }
}
