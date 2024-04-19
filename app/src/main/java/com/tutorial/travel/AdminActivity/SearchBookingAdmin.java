package com.tutorial.travel.AdminActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.Booking;

public class SearchBookingAdmin extends AppCompatActivity {
    EditText dateEditText;
    Button searchButton;
    ListView bookingListView;
    Spinner userSpinner, roomSpinner; // Changed the name to roomSpinner
    DatabaseHelper databaseHelper;
    BookingListAdapter bookingListAdapter;
    List<Booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_booking_admin);

        databaseHelper = new DatabaseHelper(this);

        userSpinner = findViewById(R.id.userSpinner);
        roomSpinner = findViewById(R.id.roomSpinner); // Changed the variable name
        dateEditText = findViewById(R.id.dateEditText);

        searchButton = findViewById(R.id.searchButton);
        bookingListView = findViewById(R.id.bookingListView);

        bookingList = new ArrayList<>();
        bookingListAdapter = new BookingListAdapter(this, R.layout.booking_list_item, bookingList);
        bookingListView.setAdapter(bookingListAdapter);

        ArrayAdapter<String> userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getUserList());
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getRoomList());
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = "";
                String selectedRoomName = "";
                String date = dateEditText.getText().toString().trim();

                // Kiểm tra xem danh sách người dùng có phần tử nào không
                if (userSpinner.getSelectedItem() != null) {
                    userName = userSpinner.getSelectedItem().toString();
                }

                // Kiểm tra xem danh sách phòng có phần tử nào không
                if (roomSpinner.getSelectedItem() != null) {
                    selectedRoomName = roomSpinner.getSelectedItem().toString();
                }

                searchBookings(userName, date, selectedRoomName);
            }
        });
    }

    private void searchBookings(String userId, String date, String roomId) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_BOOKING;

        if (!userId.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_USER_ID_FK + " IN (SELECT " + DatabaseHelper.COLUMN_ID +
                    " FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = '" + userId + "')";
        }

        if (!date.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_CHECK_IN_DATE + " = '" + date + "'";
        }

        if (!roomId.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_ROOM_ID_FK + " IN (SELECT " + DatabaseHelper.COLUMN_ROOM_ID +
                    " FROM " + DatabaseHelper.TABLE_ROOM + " WHERE " + DatabaseHelper.COLUMN_ROOM_NAME + " = '" + roomId + "')";
        }

        Cursor cursor = db.rawQuery(query, null);
        bookingList.clear();

        if (cursor.moveToFirst()) {
            do {
                // Đọc thông tin booking từ cursor và thêm vào danh sách bookingList
                Booking booking = new Booking();
                // Đọc thông tin từ cursor và gán vào booking
                bookingList.add(booking);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        if (bookingList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy booking nào", Toast.LENGTH_SHORT).show();
        }

        bookingListAdapter.notifyDataSetChanged();
    }

    private List<String> getUserList() {
        List<String> userList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_USERNAME + " FROM " + DatabaseHelper.TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME));
                userList.add(username);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    private List<String> getRoomList() {
        List<String> roomList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ROOM_NAME + " FROM " + DatabaseHelper.TABLE_ROOM, null);
        if (cursor.moveToFirst()) {
            do {
                String roomName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_NAME));
                roomList.add(roomName);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }
}
