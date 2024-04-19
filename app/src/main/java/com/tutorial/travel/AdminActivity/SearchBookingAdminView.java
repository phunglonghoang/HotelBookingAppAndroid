package com.tutorial.travel.AdminActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.Booking;

import java.util.ArrayList;
import java.util.List;

public class SearchBookingAdminView extends AppCompatActivity {

    EditText userNameEditText, dateEditText, roomNameEditText;
    Button searchButton;
    ListView bookingListView;

    DatabaseHelper databaseHelper;
    BookingListAdapter bookingListAdapter;
    List<Booking> bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_booking_admin_view);

        databaseHelper = new DatabaseHelper(this);

        userNameEditText = findViewById(R.id.userNameEditText);
        dateEditText = findViewById(R.id.dateEditText);
        roomNameEditText = findViewById(R.id.roomNameEditText);
        searchButton = findViewById(R.id.searchButton);
        bookingListView = findViewById(R.id.bookingListView);

        bookingList = new ArrayList<>();
        bookingListAdapter = new BookingListAdapter(this, R.layout.booking_list_item, bookingList);
        bookingListView.setAdapter(bookingListAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditText.getText().toString().trim();
                String date = dateEditText.getText().toString().trim();
                String roomName = roomNameEditText.getText().toString().trim();

                searchBookings(userName, date, roomName);
            }
        });
    }

    private void searchBookings(String userName, String date, String roomName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT * FROM " + DatabaseHelper.TABLE_BOOKING + " WHERE 1=1";

        if (!userName.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_USER_ID_FK + " = (SELECT " + DatabaseHelper.COLUMN_ID +
                    " FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = '" + userName + "')";
        }

        // Sửa thành tìm theo userID trực tiếp
        if (!userName.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_USER_ID_FK + " = " + getUserId(userName);
        }

        // Sửa thành tìm theo roomID trực tiếp
        if (!roomName.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_ROOM_ID_FK + " = " + getRoomId(roomName);
        }

        if (!date.isEmpty()) {
            query += " AND " + DatabaseHelper.COLUMN_CHECK_IN_DATE + " = '" + date + "'";
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

    private int getUserId(String userName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT " + DatabaseHelper.COLUMN_ID + " FROM " + DatabaseHelper.TABLE_USERS +
                " WHERE " + DatabaseHelper.COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{userName});
        int userId = -1; // Giả sử userID không tồn tại
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }

    private int getRoomId(String roomName) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String query = "SELECT " + DatabaseHelper.COLUMN_ROOM_ID + " FROM " + DatabaseHelper.TABLE_ROOM +
                " WHERE " + DatabaseHelper.COLUMN_ROOM_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{roomName});
        int roomId = -1; // Giả sử roomID không tồn tại
        if (cursor.moveToFirst()) {
            roomId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_ID));
        }
        cursor.close();
        db.close();
        return roomId;
    }
}
