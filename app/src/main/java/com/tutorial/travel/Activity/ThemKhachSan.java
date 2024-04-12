package com.tutorial.travel.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

public class ThemKhachSan extends AppCompatActivity {
    EditText editTextImageUrl;
    ImageView imageViewHotel;
    EditText editTextHotelName, editTextLocation, editTextStarRating;
    Button buttonAddRoom, buttonUploadImage, buttonSaveHotel;
    boolean isHotelSaved = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_them_khach_san);

        editTextImageUrl = findViewById(R.id.editTextImageUrl);
        imageViewHotel = findViewById(R.id.imageViewHotel);

        editTextHotelName = findViewById(R.id.editTextHotelName);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextStarRating = findViewById(R.id.editTextStarRating);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);
        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonSaveHotel = findViewById(R.id.buttonSaveHotel);

//        editTextImageUrl.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    loadImageFromUrl(editTextImageUrl.getText().toString().trim());
//                    return true;
//                }
//                return false;
//            }
//        });

        buttonUploadImage = findViewById(R.id.buttonUploadImage);
        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromUrl(editTextImageUrl.getText().toString().trim());
            }
        });
        buttonSaveHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveHotel(); 
            }


        });
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHotelSaved) {
                    // Nếu khách sạn đã được lưu thành công, chuyển sang trang thêm loại phòng
                    startActivity(new Intent(ThemKhachSan.this, AddRoomType.class));
                } else {
                    // Nếu khách sạn chưa được lưu, hiển thị thông báo
                    Toast.makeText(ThemKhachSan.this, "Please save the hotel first", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void saveHotel() {
        String hotelName = editTextHotelName.getText().toString().trim();
        String location = editTextLocation.getText().toString().trim();
        int rating = Integer.parseInt(editTextStarRating.getText().toString().trim());
        String imageUrl = editTextImageUrl.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);
        long hotelId = db.addHotel(hotelName, location, rating, imageUrl);

        if(hotelId != -1){
            Intent intent = new Intent(ThemKhachSan.this, AddRoomType.class);
            Toast.makeText(this, "Hotel added successfully with ID: " + hotelId, Toast.LENGTH_SHORT).show();
            isHotelSaved = true;
        } else {
            Toast.makeText(this, "Failed to add hotel", Toast.LENGTH_SHORT).show();
        }

    }
    private void loadImageFromUrl(String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageViewHotel);
        }
    }
}