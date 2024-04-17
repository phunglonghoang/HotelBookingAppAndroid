package com.tutorial.travel.Activity;

import android.os.Bundle;
import android.widget.TextView;
<<<<<<< HEAD
import android.widget.Toast;
=======
>>>>>>> 2fb2364bcd5ccd522dad07ac6841669b4da0bf5e

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.textfield.TextInputEditText;



import com.tutorial.travel.Adapter.RoomModelAdapter;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.RoomModel;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    private RoomAdapter roomAdapter;
    private int hotelId;
    private TextView hotelIdTextView;
    private String checkInDate;
    private String checkOutDate;


    private RoomModelAdapter roomAdapter;
    private int hotelId;
    TextView hotelIdTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        hotelId = getIntent().getIntExtra("hotelId", -1);

        if (hotelId == -1) {
            // Xử lý trường hợp hotelId không hợp lệ
            Toast.makeText(this, "Invalid hotelId", Toast.LENGTH_SHORT).show();
            finish(); // Kết thúc activity hiện tại
            return;
        }

        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        TextInputEditText etCheckInDate = findViewById(R.id.etCheckInDate);
        TextInputEditText etCheckOutDate = findViewById(R.id.etCheckOutDate);
        etCheckInDate.setText(checkInDate);
        etCheckOutDate.setText(checkOutDate);


        TextView hotelIdTextView = findViewById(R.id.hotelIdTextView);
        hotelIdTextView.setText("Hotel ID: " + hotelId);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showRoomList();
    }

    private void showRoomList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<RoomModel> roomList = databaseHelper.getRoomsByHotelId(hotelId);

        roomAdapter = new RoomAdapter(this, roomList, checkInDate, checkOutDate);


        // Khởi tạo và thiết lập adapter cho RecyclerView
        roomAdapter = new RoomModelAdapter(this, roomList);

        recyclerView.setAdapter(roomAdapter);
    }
}
