package com.tutorial.travel.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.R;
import com.tutorial.travel.model.HotelModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HotelDetailActivity extends AppCompatActivity {
    TextView startTxt, hotelNameTxt, locationTxt, idHotelTxt, etCheckInDate, etCheckOutDate, priceTxt;
    ImageView imageHotelDetail;
    Button btnSelectRoom;

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

        setVariable();
        etCheckInDate.setOnClickListener(v -> showDatePickerDialog());

        btnSelectRoom.setOnClickListener(v -> {
            Intent intent = new Intent(HotelDetailActivity.this, RoomListActivity.class);
            intent.putExtra("hotelId", hotelId);
            intent.putExtra("checkInDate", etCheckInDate.getText().toString());
            intent.putExtra("checkOutDate", etCheckOutDate.getText().toString());
            startActivity(intent);
        });

    }

    private void setVariable() {
        HotelModel hotel = (HotelModel) getIntent().getSerializableExtra("hotel");
        if (hotel != null) {
            hotelNameTxt.setText(hotel.getHotelName());
            locationTxt.setText(hotel.getLocation());
            startTxt.setText(String.valueOf(hotel.getStarRating()));
            Picasso.get().load(hotel.getImage()).into(imageHotelDetail);
            hotelId = hotel.getId();
            idHotelTxt.setText(String.valueOf(hotelId));
            minPrice = hotel.getMinRoomPrice();
            priceTxt.setText(String.format(Locale.getDefault(), "%.2f VND", minPrice));

            displayCurrentDate();
        }
    }


    private void displayCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        etCheckInDate.setText(currentDate);

        // Thiết lập ngày trả phòng là ngày tiếp theo của ngày nhận phòng
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

                    // Tăng ngày lên 1 để lấy ngày trả phòng
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    String checkOutDate = dateFormat.format(calendar.getTime());
                    etCheckOutDate.setText(checkOutDate);
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
}
