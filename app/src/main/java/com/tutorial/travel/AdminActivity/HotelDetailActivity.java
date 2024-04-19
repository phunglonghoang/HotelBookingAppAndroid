package com.tutorial.travel.AdminActivity;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.Adapter.ReviewAdapter;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.ReviewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HotelDetailActivity extends AppCompatActivity {
    TextView startTxt, hotelNameTxt, locationTxt, idHotelTxt, etCheckInDate, etCheckOutDate, priceTxt;
    ImageView imageHotelDetail;
    Button btnSelectRoom, btnreview;
    EditText txtReview;
    RatingBar rt;
    private RecyclerView recyclerView;
    private ReviewModel reviewAdapter;
    private ReviewAdapter adapter;


    DatabaseHelper dbHelper;

    private ReviewAdapter rvAdapter;
    private List<ReviewModel> rvList;


    private double minPrice;
    private int hotelId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hotel_detail);

        startTxt = findViewById(R.id.startTxt);
        hotelNameTxt = findViewById(R.id.hotelNameTxt);
        locationTxt = findViewById(R.id.locationTxt);
        imageHotelDetail = findViewById(R.id.imageHotelDetail);
        idHotelTxt = findViewById(R.id.idHotelTxt);
        etCheckInDate = findViewById(R.id.etCheckInDate);
        etCheckOutDate = findViewById(R.id.etCheckOutDate);
        priceTxt = findViewById(R.id.priceTxt);
        btnSelectRoom = findViewById(R.id.btnSelectRoom);
        btnreview = findViewById(R.id.btnreview);
        txtReview= findViewById(R.id.txtReview);

        rt = (RatingBar) findViewById(R.id.ratingbar);
        LayerDrawable stars=(LayerDrawable)rt.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        showReviewList();


//        hotelIdTextView.setText("Hotel ID: " + hotelId);

        btnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callStarRating();
            }
        });

        setVariable();
        etCheckInDate.setOnClickListener(v -> showDatePickerDialog());

        btnSelectRoom.setOnClickListener(v -> {
            Intent intent = new Intent(HotelDetailActivity.this, RoomListActivity.class);
            intent.putExtra("hotelId", hotelId);
            intent.putExtra("checkInDate", etCheckInDate.getText().toString());
            intent.putExtra("checkOutDate", etCheckOutDate.getText().toString());
            intent.putExtra("HotelName", hotelNameTxt.getText().toString());
            Log.d(TAG, "Hotel Name: " + hotelNameTxt.getText().toString());
            intent.putExtra("Location", locationTxt.getText().toString());
            Log.d(TAG, "Location: " + locationTxt.getText().toString());
            startActivity(intent);
        });

    }

    private void showReviewList() {
        HotelModel hotel = (HotelModel) getIntent().getSerializableExtra("hotel");
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(HotelDetailActivity.this,
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutManager);
        rvList = new ArrayList<>();
        adapter = new ReviewAdapter(this, rvList);
        recyclerView.setAdapter(adapter);
        loadReviewbyHotelId(String.valueOf(hotel.getId()));

    }

    private void setVariable() {
        HotelModel hotel = (HotelModel) getIntent().getSerializableExtra("hotel");

        if (hotel != null) {

            hotelNameTxt.setText(hotel.getHotelName());
            Log.d(TAG, "hotelNameTxt: "+hotelNameTxt);
            locationTxt.setText(hotel.getLocation());
            Log.d(TAG, "locationTxt: "+locationTxt);
            startTxt.setText(String.valueOf(hotel.getStarRating()));
            Log.d("startTxt", "setVariable: startTxt"+ startTxt);
            Picasso.get().load(hotel.getImage()).into(imageHotelDetail);
            Log.d(TAG, "getImage: "+imageHotelDetail);
            hotelId = hotel.getId();
            idHotelTxt.setText(String.valueOf(hotelId));
            minPrice = hotel.getMinRoomPrice();
            Log.d(TAG, "minPrice: "+minPrice);
            priceTxt.setText(String.format(Locale.getDefault(), "%.2f VND", minPrice));

            displayCurrentDate();
        }
    }


    private void displayCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        etCheckInDate.setText(currentDate);

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String nextDay = dateFormat.format(calendar.getTime());
        etCheckOutDate.setText(nextDay);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    // Thiết lập ngày nhận phòng
                    calendar.set(selectedYear, selectedMonth, selectedDayOfMonth);
                    String selectedDate = dateFormat.format(calendar.getTime());
                    etCheckInDate.setText(selectedDate);

                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    String checkOutDate = dateFormat.format(calendar.getTime());
                    etCheckOutDate.setText(checkOutDate);
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public void callStarRating(){
        HotelModel hotel = (HotelModel) getIntent().getSerializableExtra("hotel");
        double rating = rt.getRating(); // Lấy điểm đánh giá từ RatingBar
        String reviewDetail = txtReview.getText().toString().trim();
        int hotelId = hotel.getId();



        if (rating>5 || rating <=0) {
            Toast.makeText(this, "Hãy đánh giá sao", Toast.LENGTH_SHORT).show();

        }
        else if (reviewDetail.isEmpty() ){
            Toast.makeText(this, "Cho chúng tôi biết chi tiết đánh giá của bạn", Toast.LENGTH_SHORT).show();
        }
        else{
            ReviewModel review = new ReviewModel();
            review.setRating(rating);
            review.setReviewDetail(reviewDetail);
            review.setHotel_id(hotelId);
            review.setUser_id(2);
            addReview(review);
            Toast.makeText(this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
        }


    }
    public void addReview(ReviewModel review) {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);


        databaseHelper.addReview(review);

//        showReviewList();
    }

    public void loadReviewbyHotelId(String hotel_id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Log.d(TAG, "loadReviewbyHotelId: " +hotel_id);
        Cursor cursor = db.rawQuery("SELECT * FROM " +
                DatabaseHelper.TABLE_REVIEW +
                " WHERE " + DatabaseHelper.COLUMN_HOTEL_ID +
                "=?", new String[]{hotel_id});
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REVIEW_ID)); // Sử dụng getColumnIndexOrThrow
                String rvdetail = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REVIEW_DETAIL)); // Sử dụng getColumnIndexOrThrow
                double rating = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RATING));
                int hotelId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_HOTEL_ID));
                int user_id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                ReviewModel rv = new ReviewModel(id, rvdetail,rating,hotelId,user_id  );
                rvList.add(rv);

            } while (cursor.moveToNext());
        }


        cursor.close();
        adapter.notifyDataSetChanged();
        db.close();

    }
}