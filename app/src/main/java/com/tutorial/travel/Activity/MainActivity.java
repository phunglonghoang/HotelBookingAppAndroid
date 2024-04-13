package com.tutorial.travel.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
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
import com.tutorial.travel.controller.RegisterActivity;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapterPopular, adapterCat;
    private RecyclerView recyclerViewPopular, recyclerViewCategory, recyclerView3;
    private HotelAdapter adapter;
    private List<HotelModel> hotelList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDataToDatabase();
        recyclerView3 = findViewById(R.id.recyclerview3);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView3.setLayoutManager(horizontalLayoutManager);
        hotelList = new ArrayList<>();
        adapter = new HotelAdapter(this, hotelList);
        recyclerView3.setAdapter(adapter);


        TextView txtUserName = findViewById(R.id.txtUserName);

        Bundle bundle = getIntent().getExtras();

        String username = bundle.getString("username");


        loadHotels();
        initRecyclerView();
        txtUserName.setText(username);
    }

    private void initRecyclerView() {

        // For Popular RecyclerView
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
            values.put(DatabaseHelper.COLUMN_IMAGE, "https://i.redd.it/j6myfqglup501.jpg");
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

        }
        db.close();
    }

    private void loadHotels() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_HOTEL, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID)); // Sử dụng getColumnIndexOrThrow
                String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME)); // Sử dụng getColumnIndexOrThrow
                String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION)); // Sử dụng getColumnIndexOrThrow
                int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STAR_RATING)); // Sử dụng getColumnIndexOrThrow
                String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE)); // Sử dụng getColumnIndexOrThrow
                HotelModel hotel = new HotelModel(id, hotelName, location, starRating, image);
                hotelList.add(hotel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        adapter.notifyDataSetChanged();
        db.close();
    }
    public void onSettingClick(View v){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }
}
