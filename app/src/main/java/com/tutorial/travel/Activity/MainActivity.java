package com.tutorial.travel.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.Adapter.CategoryAdapter;
import com.tutorial.travel.Adapter.HotelAdapter;
import com.tutorial.travel.Adapter.PopularAdapter;
import com.tutorial.travel.Domain.CategoryDomain;
import com.tutorial.travel.Domain.PopularDomain;
import com.tutorial.travel.R;
import com.tutorial.travel.controller.UserProfileActivity;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterPopular, adapterCat;
    private RecyclerView recyclerViewPopular, recyclerViewCategory, recyclerView3;
    private HotelAdapter adapter;
    private List<HotelModel> hotelList;

    ImageButton profileImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDataToDatabase();
        recyclerView3 = findViewById(R.id.recyclerview3);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(horizontalLayoutManager);
        hotelList = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelList, new HotelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HotelModel hotel) {
                // Chuyển sang DetailActivity và truyền dữ liệu của khách sạn
                Intent intent = new Intent(MainActivity.this, HotelDetailActivity.class);
                intent.putExtra("hotel", hotel);
                startActivity(intent);
            }
        });
        recyclerView3.setAdapter(adapter);


        TextView txtUserName = findViewById(R.id.userNameTxt);
        Bundle bundle = getIntent().getExtras();
        String username = bundle.getString("username");


        profileImg = findViewById(R.id.profileImg);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, UserProfileActivity.class);
                intent1.putExtra("USERNAME", username);
                startActivity(intent1);
            }
        });
        loadHotels();
       addRoomToHotel();
        initRecyclerView();
        txtUserName.setText(username);

    }

    private void initRecyclerView() {

        ArrayList<PopularDomain> items = new ArrayList<>();
        String str = getString(R.string.description);
        items.add(new PopularDomain("Mar caible avendia lago", "Miami Beach", str , 2, true, 4.9,"pic1", true, 1000));
        items.add(new PopularDomain("Passo Rolle, TN", "Hawaii Beach", str, 2, false, 5.0,"pic2", false, 2500));
        items.add(new PopularDomain("Mar caible avendia lago", "Miami Beach", str, 2, true, 4.3,"pic3", true, 30000));

        recyclerViewPopular = findViewById(R.id.recyclerview1);
        recyclerViewPopular.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        ));
        adapterPopular = new PopularAdapter(items);
        recyclerViewPopular.setAdapter(adapterPopular);

        // For Category RecyclerView
        ArrayList<CategoryDomain> catsList = new ArrayList<>();
        catsList.add(new CategoryDomain("Beaches", "cat1"));
        catsList.add(new CategoryDomain("Camps", "cat2"));
        catsList.add(new CategoryDomain("Forest", "cat3"));
        catsList.add(new CategoryDomain("Desert", "cat4"));
        catsList.add(new CategoryDomain("Mountain", "cat5"));

        recyclerViewCategory = findViewById(R.id.recyclerview2);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
        ));

        adapterCat = new CategoryAdapter(catsList);
        recyclerViewCategory.setAdapter(adapterCat);
    }
    private void addDataToDatabase() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HOTEL, null);
        if (cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_HOTEL_NAME, "Hữu Nghị");
            values.put(DatabaseHelper.COLUMN_LOCATION, "Viet Nam");
            values.put(DatabaseHelper.COLUMN_STAR_RATING, 5);
            values.put(DatabaseHelper.COLUMN_IMAGE, "https://i.redd.it/j6myfqglup501.jpg");
            db.insert(DatabaseHelper.TABLE_HOTEL, null, values);

            values.clear();
            values.put(DatabaseHelper.COLUMN_HOTEL_NAME, "Mường Thanh");
            values.put(DatabaseHelper.COLUMN_LOCATION, "Hồ Chí Minh");
            values.put(DatabaseHelper.COLUMN_STAR_RATING, 4);
            values.put(DatabaseHelper.COLUMN_IMAGE, "https://tse3.mm.bing.net/th?id=OIP.kb-80cNd4JIUyfm0mje2SAHaE7&pid=Api&P=0&h=220");
            db.insert(DatabaseHelper.TABLE_HOTEL, null, values);


            values.clear();
            values.put(DatabaseHelper.COLUMN_HOTEL_NAME, "Thanh Bình");
            values.put(DatabaseHelper.COLUMN_LOCATION, "Hà Nội");
            values.put(DatabaseHelper.COLUMN_STAR_RATING, 2);
            values.put(DatabaseHelper.COLUMN_IMAGE, "https://i.redd.it/j6myfqglup501.jpg");
            db.insert(DatabaseHelper.TABLE_HOTEL, null, values);


            values.put(DatabaseHelper.COLUMN_HOTEL_NAME, "Phúc Long");
            values.put(DatabaseHelper.COLUMN_LOCATION, "Đà Lạt");
            values.put(DatabaseHelper.COLUMN_STAR_RATING, 1);
            values.put(DatabaseHelper.COLUMN_IMAGE, "https://i.redd.it/j6myfqglup501.jpg");
            db.insert(DatabaseHelper.TABLE_HOTEL, null, values);

            ContentValues values1 = new ContentValues();
            values1.put(DatabaseHelper.COLUMN_ROOM_TYPE_NAME, "Standard");
            values1.put(DatabaseHelper.COLUMN_ROOMTYPE_DESCRIPTIONS, "Phòng tiêu chuẩn");
            long roomType1Id = db.insert(DatabaseHelper.TABLE_ROOM_TYPE, null, values1);

            ContentValues values2 = new ContentValues();
            values2.put(DatabaseHelper.COLUMN_ROOM_TYPE_NAME, "Deluxe");
            values2.put(DatabaseHelper.COLUMN_ROOMTYPE_DESCRIPTIONS, "Phòng sang trọng");
            long roomType2Id = db.insert(DatabaseHelper.TABLE_ROOM_TYPE, null, values2);

            ContentValues values3 = new ContentValues();
            values3.put(DatabaseHelper.COLUMN_ROOM_TYPE_NAME, "Suite");
            values3.put(DatabaseHelper.COLUMN_ROOMTYPE_DESCRIPTIONS, "Phòng hạng sang");
            long roomType3Id = db.insert(DatabaseHelper.TABLE_ROOM_TYPE, null, values3);

        }

        db.close();
    }

    private void loadHotels() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HOTEL+" LIMIT 3 ", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID)); // Sử dụng getColumnIndexOrThrow
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME)); // Sử dụng getColumnIndexOrThrow
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION)); // Sử dụng getColumnIndexOrThrow
                int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STAR_RATING)); // Sử dụng getColumnIndexOrThrow
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE)); // Sử dụng getColumnIndexOrThrow
                double minPrice = getMinRoomPriceForHotel(db, id);
                HotelModel hotel = new HotelModel(id, hotelName, location, starRating, image,minPrice);
                hotelList.add(hotel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        db.close();
    }

    private int getRoomTypeIdByName(String roomTypeName) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ROOM_TYPE_ID + " FROM " + DatabaseHelper.TABLE_ROOM_TYPE +
                " WHERE " + DatabaseHelper.COLUMN_ROOM_TYPE_NAME + "=?", new String[]{roomTypeName});

        int roomTypeId = -1;
        if (cursor.moveToFirst()) {
            try {
                roomTypeId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ROOM_TYPE_ID));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        cursor.close();
        db.close();
        return roomTypeId;
    }
    private void addRoomToHotel() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Lấy ID của các loại phòng từ cơ sở dữ liệu
        int standardRoomTypeId = getRoomTypeIdByName("Standard");
        int deluxeRoomTypeId = getRoomTypeIdByName("Deluxe");
        int suiteRoomTypeId = getRoomTypeIdByName("Suite");

        // Thêm phòng cho loại phòng "Standard"
        addRoomForRoomType(db, standardRoomTypeId, "P01", 100, "https://example.com/room_image.jpg", "Available", 2);

        // Thêm phòng cho loại phòng "Deluxe"
        addRoomForRoomType(db, deluxeRoomTypeId, "P02", 150, "https://example.com/room_image.jpg", "Available", 2);

        // Thêm phòng cho loại phòng "Suite"
        addRoomForRoomType(db, suiteRoomTypeId, "P03", 200, "https://example.com/room_image.jpg", "Available", 2);

        db.close();
    }

    private void addRoomForRoomType(SQLiteDatabase db, int roomTypeId, String roomName, int price, String roomImage, String roomStatus, int hotelId) {
        if (roomTypeId != -1) {
            if (!isRoomExist(db, roomName)) {
                ContentValues values = new ContentValues();
                values.put(DatabaseHelper.COLUMN_ROOM_NAME, roomName);
                values.put(DatabaseHelper.COLUMN_PRICE, price);
                values.put(DatabaseHelper.COLUMN_ROOM_IMAGE, roomImage);
                values.put(DatabaseHelper.COLUMN_ROOM_STATUS, roomStatus);
                values.put(DatabaseHelper.COLUMN_HOTEL_ID_FK, hotelId);
                values.put(DatabaseHelper.COLUMN_ROOM_TYPE_ID_FK, roomTypeId);

                long newRowId = db.insert(DatabaseHelper.TABLE_ROOM, null, values);

                if (newRowId != -1) {
                } else {
                }
            } else {
            }
        }
    }

    private boolean isRoomExist(SQLiteDatabase db, String roomName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ROOM +
                " WHERE " + DatabaseHelper.COLUMN_ROOM_NAME + "=?", new String[]{roomName});

        boolean exist = cursor.getCount() > 0;

        cursor.close();

        return exist;
    }

    private double getMinRoomPriceForHotel(SQLiteDatabase db, int hotelId) {
        double minPrice = -1;

        Cursor cursor = db.rawQuery("SELECT MIN(" + DatabaseHelper.COLUMN_PRICE + ") AS min_price FROM " +
                DatabaseHelper.TABLE_ROOM + " WHERE " + DatabaseHelper.COLUMN_HOTEL_ID_FK + "=?", new String[]{String.valueOf(hotelId)});

        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow("min_price");
            minPrice = cursor.getDouble(columnIndex);
        }

        cursor.close();
        return minPrice;
    }


}