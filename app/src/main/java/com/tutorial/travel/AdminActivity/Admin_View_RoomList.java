package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tutorial.travel.controller.LoginActivity;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import com.tutorial.travel.R;
import java.util.List;


public class Admin_View_RoomList extends AppCompatActivity {
    Button userHome, logoutHotel;
    ListView listViewHotels;
    DatabaseHelper databaseHelper;
    ArrayAdapter<HotelModel> hotelListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_view_room_list);

//        ListView listView = findViewById(R.id.room_listView);
//        List<Room> roomList = new ArrayList<>();
//
//        RoomAdapter adapter = new RoomAdapter(this, R.layout.item_room,roomList);
//
//        listView.setAdapter(adapter);
        userHome = findViewById(R.id.userHome);
        logoutHotel = findViewById(R.id.logoutHotel);

        userHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_View_RoomList.this, AdminManagementHotel.class));
            }
        });

        logoutHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin_View_RoomList.this, LoginActivity.class));
            }
        });

        listViewHotels = findViewById(R.id.room_listView);
        databaseHelper = new DatabaseHelper(this);

//        List<String> hotelNames = databaseHelper.getAllHotelNames();
        List<HotelModel> hotels = databaseHelper.getAllHotelNames1();

        hotelListAdapter = new ArrayAdapter<HotelModel>(this, R.layout.list_item_hotel, R.id.textViewHotelName,hotels){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View itemView = super.getView(position, convertView, parent);

                // Lấy thông tin của khách sạn tại vị trí hiện tại
                HotelModel hotel = getItem(position);

                // Hiển thị thông tin tên khách sạn và địa điểm
                TextView textViewHotelName = itemView.findViewById(R.id.textViewHotelName);
                TextView textViewHotelLocation = itemView.findViewById(R.id.textViewHotelLocation);

                if (hotel != null) {
                    textViewHotelName.setText(hotel.getHotelName());
                    textViewHotelLocation.setText(hotel.getLocation());
                }

                return itemView;
            }
        };
        // Đặt adapter cho ListView
        listViewHotels.setAdapter(hotelListAdapter);
    }
}



