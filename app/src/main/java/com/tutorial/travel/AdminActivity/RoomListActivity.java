package com.tutorial.travel.AdminActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    private RoomModelAdapter RoomAdapter;
    private int hotelId;
    private TextView hotelIdTextView;
    private String checkInDate;
    private String checkOutDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        hotelId = getIntent().getIntExtra("hotelId", -1);
        if (hotelId == -1) {
            Toast.makeText(this, "Invalid hotelId", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        checkInDate = getIntent().getStringExtra("checkInDate");
        checkOutDate = getIntent().getStringExtra("checkOutDate");

        TextInputEditText etCheckInDate = findViewById(R.id.etCheckInDate);
        TextInputEditText etCheckOutDate = findViewById(R.id.etCheckOutDate);
        etCheckInDate.setText(checkInDate);
        etCheckOutDate.setText(checkOutDate);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showRoomList();
    }

    private void showRoomList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<RoomModel> roomList = databaseHelper.getRoomsByHotelId(hotelId);
        RoomAdapter = new RoomModelAdapter(this, roomList, checkInDate, checkOutDate, hotelId);
        Log.d("TAG", "showRoomList: id hotel............: "+ hotelId);
        recyclerView.setAdapter(RoomAdapter);
    }

}
