package com.tutorial.travel.AdminActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;
public class AddBookingAdmin extends AppCompatActivity {
    EditText checkInEditText, checkOutEditText;
    Spinner roomIdSpinner, userIdSpinner;
    Button addBookingButton, backBookingButton;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_booking_admin);

        databaseHelper = new DatabaseHelper(this);

        roomIdSpinner = findViewById(R.id.roomIdSpinner);
        userIdSpinner = findViewById(R.id.userIdSpinner);
        checkInEditText = findViewById(R.id.checkInEditText);
        checkOutEditText = findViewById(R.id.checkOutEditText);
        addBookingButton = findViewById(R.id.addBookingButton);
        backBookingButton = findViewById(R.id.backBookingButton);

        loadRoomSpinner();
        loadUserSpinner();
        addBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomId = roomIdSpinner.getSelectedItem().toString();
                String userId = userIdSpinner.getSelectedItem().toString();
                String checkIn = checkInEditText.getText().toString().trim();
                String checkOut = checkOutEditText.getText().toString().trim();

                if (!checkIn.isEmpty() && !checkOut.isEmpty()) {
                    addBooking(roomId, userId, checkIn, checkOut);
                } else {
                    Toast.makeText(AddBookingAdmin.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        backBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBookingAdmin.this, Booking_Management.class);
                startActivity(intent);
            }
        });
    }

    private void loadRoomSpinner() {
        List<String> roomList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ROOM_ID + ", " + DatabaseHelper.COLUMN_ROOM_NAME + " FROM " + DatabaseHelper.TABLE_ROOM, null);
        if (cursor.moveToFirst()) {
            do {
                String roomId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_ID));
                String roomName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_NAME));
                roomList.add(roomName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomIdSpinner.setAdapter(adapter);
    }

    private void loadUserSpinner() {
        List<String> userList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + ", " + DatabaseHelper.COLUMN_USERNAME + " FROM " + DatabaseHelper.TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                String userId = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
                userList.add(userName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userIdSpinner.setAdapter(adapter);
    }


    private void addBooking(String roomName, String userName, String checkIn, String checkOut) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Lấy roomId từ tên phòng
        String roomIdQuery = "SELECT " + DatabaseHelper.COLUMN_ROOM_ID + " FROM " + DatabaseHelper.TABLE_ROOM + " WHERE " + DatabaseHelper.COLUMN_ROOM_NAME + " = ?";
        Cursor roomCursor = db.rawQuery(roomIdQuery, new String[]{roomName});
        int roomId = -1;
        if (roomCursor.moveToFirst()) {
            roomId = roomCursor.getInt(roomCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_ID));
        }
        roomCursor.close();

        // Lấy userId từ tên người dùng
        String userIdQuery = "SELECT " + DatabaseHelper.COLUMN_ID + " FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ?";
        Cursor userCursor = db.rawQuery(userIdQuery, new String[]{userName});
        int userId = -1;
        if (userCursor.moveToFirst()) {
            userId = userCursor.getInt(userCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
        }
        userCursor.close();

        if (roomId != -1 && userId != -1) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ROOM_ID_FK, roomId);
            values.put(DatabaseHelper.COLUMN_USER_ID_FK, userId);
            values.put(DatabaseHelper.COLUMN_CHECK_IN_DATE, checkIn);
            values.put(DatabaseHelper.COLUMN_CHECK_OUT_DATE, checkOut);
            values.put(DatabaseHelper.COLUMN_IS_CONFIRMED, 1); // Mặc định là 1

            long newRowId = db.insert(DatabaseHelper.TABLE_BOOKING, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Booking added successfully", Toast.LENGTH_SHORT).show();
                // Thực hiện các hành động khác sau khi thêm booking thành công (nếu cần)
            } else {
                Toast.makeText(this, "Failed to add booking", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Room or User not found", Toast.LENGTH_SHORT).show();
        }
    }

}