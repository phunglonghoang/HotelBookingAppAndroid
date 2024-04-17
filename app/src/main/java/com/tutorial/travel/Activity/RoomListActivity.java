package com.tutorial.travel.Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tutorial.travel.Adapter.RoomModelAdapter;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.RoomModel;

import java.util.List;

public class RoomListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RoomModelAdapter roomAdapter;
    private int hotelId;
    TextView hotelIdTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        hotelId = getIntent().getIntExtra("hotelId", -1);
        TextView hotelIdTextView = findViewById(R.id.hotelIdTextView);
        hotelIdTextView.setText("Hotel ID: " + hotelId);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showRoomList();
    }

    private void showRoomList() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<RoomModel> roomList = databaseHelper.getRoomsByHotelId(hotelId);

        // Khởi tạo và thiết lập adapter cho RecyclerView
        roomAdapter = new RoomModelAdapter(this, roomList);
        recyclerView.setAdapter(roomAdapter);
    }
}
