package com.tutorial.travel.Activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.R;
import com.tutorial.travel.model.HotelModel;

public class HotelDetailActivity extends AppCompatActivity {
    TextView startTxt,hotelNameTxt,locationTxt,idHotelTxt;
    ImageView imageHotelDetail;
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
        setVariable();

    }
    private void setVariable() {
        HotelModel hotel = (HotelModel) getIntent().getSerializableExtra("hotel");
        if (hotel != null) {
        hotelNameTxt.setText(hotel.getHotelName());
        locationTxt.setText(hotel.getLocation());
        startTxt.setText(String.valueOf(hotel.getStarRating()));
        Picasso.get().load(hotel.getImage()).into(imageHotelDetail);
        idHotelTxt.setText(String.valueOf(hotel.getId()));
        }
}}