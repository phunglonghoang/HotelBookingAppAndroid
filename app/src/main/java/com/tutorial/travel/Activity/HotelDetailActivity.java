package com.tutorial.travel.Activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.RoomType;

import java.util.List;

public class HotelDetailActivity extends AppCompatActivity {

    private static final String TAG = "concac";
    private List<HotelModel> hotelList;
    private TextView hotelNameTxt, txtRating,Txtlocation;
    private ImageView imgMainDl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_detail_activity);
        addRoomType();

        hotelNameTxt = findViewById(R.id.hotelNameTxt);
        txtRating = findViewById(R.id.txtRating);
        Txtlocation = findViewById(R.id.Txtlocation);
        imgMainDl = findViewById(R.id.imgMainDl);

        Bundle bundle = getIntent().getExtras();

         int hotel_id;
        if (bundle != null) {
            hotel_id = bundle.getInt("hotel_id");
            Log.d(TAG, "onCreate: hotelId" + hotel_id);
            loadHotelName(String.valueOf(hotel_id));


        }



    }
    public void addRoomType(){
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_ROOMTYPE, null);
        if (cursor.getCount()==0){
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Standar");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "100000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "1");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);


            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Vip1");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "200000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "1");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);


            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Vip2");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "300000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "1");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);

            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Standar");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "100000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "2");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);


            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Vip1");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "200000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "3");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);


            values.put(DatabaseHelper.COLUMN_ROOMTYPE_TYPE, "Vip2");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_PRICE, "300000");
            values.put(DatabaseHelper.COLUMN_ROOMTYPE_QUANTITY, "3");
            values.put(DatabaseHelper.COLUMN_HOTEL_ID, "5");
            db.insert(DatabaseHelper.TABLE_ROOMTYPE, null, values);

        }

    }

    public void loadHotelName(String ht_id){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.TABLE_HOTEL +
                " WHERE " + DatabaseHelper.COLUMN_HOTEL_ID + "=?" ,new String[]{ht_id});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID)); // Sử dụng getColumnIndexOrThrow
            String hotelName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_NAME)); // Sử dụng getColumnIndexOrThrow
            String location = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LOCATION)); // Sử dụng getColumnIndexOrThrow
            int starRating = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_STAR_RATING)); // Sử dụng getColumnIndexOrThrow
            String image = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGE)); // Sử dụng getColumnIndexOrThrow
            hotelNameTxt.setText(hotelName);
            Txtlocation.setText(location);
            txtRating.setText(String.valueOf(starRating));
            Picasso.get().load(image).into(imgMainDl);

        }
        cursor.close();
        db.close();

//        imgMainDl.setImageResource(imgMainDl);


        }



}
