package com.tutorial.travel.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.Adapter.HotelAdapter;
import com.tutorial.travel.AdminActivity.HotelDetailActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class AllHotelActivity extends AppCompatActivity {
    private HotelAdapter adapter;
    private List<HotelModel> hotelList;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_hotel);


        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(AllHotelActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        hotelList = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelList,new HotelAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(HotelModel hotel) {
                Intent intent = new Intent(AllHotelActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(adapter);
        loadHotels();
    }
    private void loadHotels() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HOTEL , null);


        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID)); // Sử dụng getColumnIndexOrThrow
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME)); // Sử dụng getColumnIndexOrThrow
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION)); // Sử dụng getColumnIndexOrThrow
                int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STAR_RATING)); // Sử dụng getColumnIndexOrThrow
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE)); // Sử dụng getColumnIndexOrThrow
                double minPrice =MainActivity.getMinRoomPriceForHotel(db, id);
                HotelModel hotel = new HotelModel(id, hotelName, location, starRating, image,minPrice);
                hotelList.add(hotel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        db.close();
    }

}