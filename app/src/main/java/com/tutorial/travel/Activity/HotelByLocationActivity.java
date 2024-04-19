package com.tutorial.travel.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.Adapter.HotelByLocationAdapter;
import com.tutorial.travel.AdminActivity.HotelDetailActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class HotelByLocationActivity extends AppCompatActivity
        {
    private static final String TAG = "abc";
    private List<HotelModel> hotelLocationList;

    private HotelByLocationAdapter adapter;
    private RecyclerView recyclerView;

   TextView txtLocationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_by_location);


        recyclerView = findViewById(R.id.recyclerview);

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(HotelByLocationActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        hotelLocationList = new ArrayList<>();

//        adapter = new HotelByLocationAdapter(this, hotelLocationList, this);
        adapter = new HotelByLocationAdapter(this, hotelLocationList, new HotelByLocationAdapter.OnHotelClickListener() {
            @Override
            public void onHotelClick(HotelModel hotel) {
                // Chuyển sang DetailActivity và truyền dữ liệu của khách sạn
                Intent intent = new Intent(HotelByLocationActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        TextView txtLocationName = findViewById(R.id.txtLocationName);
        Bundle bundle = getIntent().getExtras();
        String locations = bundle.getString("locations");

        txtLocationName.setText(locations);
        loadHotelByLocation(locations);

    }


    public void loadHotelByLocation(String loca) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.TABLE_HOTEL +
                " WHERE " + DatabaseHelper.COLUMN_LOCATION +
                "=? OR " + DatabaseHelper.COLUMN_HOTEL_NAME +
                "=?", new String[]{loca, loca});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID)); // Sử dụng getColumnIndexOrThrow
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME)); // Sử dụng getColumnIndexOrThrow
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION.toLowerCase())); // Sử dụng getColumnIndexOrThrow
                int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STAR_RATING)); // Sử dụng getColumnIndexOrThrow
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE));
                double minPrice = MainActivity.getMinRoomPriceForHotel(db, id);//Sử dụng getColumnIndexOrThrow
                HotelModel hotellocation = new HotelModel(id, hotelName, location, starRating, image, minPrice);
                hotelLocationList.add(hotellocation);

            } while (cursor.moveToNext());
        }
        else {
            //danh sách trống
            showEmptyListDialog();
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        db.close();

    }
    private void showEmptyListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Không có khách sạn nào ");
        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Đóng dialog khi người dùng nhấn nút Đóng
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    // Handle hotel item click event
    public void onHotelClick(int position) {
        HotelModel clickedHotel = hotelLocationList.get(position);
        Intent intent = new Intent(HotelByLocationActivity.this, HotelDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("hotel_id", clickedHotel.getId());

        intent.putExtras(bundle);
        Log.d(TAG, "onHotelClickccc: "+position);
        Log.d(TAG, "onHotelClickccc: "+clickedHotel.getId());

        startActivity(intent);
    }



}