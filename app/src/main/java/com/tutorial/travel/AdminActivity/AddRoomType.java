package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

import java.util.List;


public class AddRoomType extends AppCompatActivity {
    private Spinner spinnerRoomType, spinnerHotel;
    private EditText editTextRoomType, roomname, price, editTextImageUrl, editTextImageUrlRoom;
    private Button buttonAddRoom, buttonBackAddRoom;
    ImageView imageViewHotel;
    Button buttonUploadImageRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room_type);

        editTextImageUrl = findViewById(R.id.editTextImageUrlRoom);
        imageViewHotel = findViewById(R.id.imageViewHotelRoom);

        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        roomname = findViewById(R.id.editTextRoomName);
        price = findViewById(R.id.editTextGia);
        buttonAddRoom = findViewById(R.id.buttonSaveRoom);

        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(roomTypeAdapter);

        // Lấy danh sách loại phòng từ cơ sở dữ liệu và thiết lập cho Adapter
        DatabaseHelper db = new DatabaseHelper(this);
        List<String> roomTypes = db.getAllRoomTypes();
        roomTypeAdapter.addAll(roomTypes);

        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomType();
            }
        });
        buttonBackAddRoom = findViewById(R.id.buttonBackAddRoom);
        buttonBackAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddRoomType.this, AdminManagementHotel.class));
            }
        });
        spinnerHotel = findViewById(R.id.spinnerHotel);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        List<String> hotelNames = db.getAllHotelNames();
        ArrayAdapter<String> hotelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hotelNames);
        hotelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHotel.setAdapter(hotelAdapter);

        buttonUploadImageRoom = findViewById(R.id.buttonUploadImageRoom);
        buttonUploadImageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromUrl(editTextImageUrl.getText().toString().trim());
            }
        });
    }


    private void addRoomType(){
       // String hotelName = spinnerHotel.getSelectedItem().toString();
        String roomNames = roomname.getText().toString().trim();
        String prices = price.getText().toString().trim();
        String status = "Available";
        String roomType = spinnerRoomType.getSelectedItem().toString();
        String hotelName = spinnerHotel.getSelectedItem().toString();
        String imageUrl = editTextImageUrl.getText().toString().trim();

        DatabaseHelper db = new DatabaseHelper(this);


        long hotelId = db.getHotelIdByName(hotelName);
        long roomTypeId = db.getRoomTypeIdByName(roomType);
        long roomId = db.addRoom(roomNames, prices, imageUrl, status, hotelId, roomTypeId);
        if(roomId != -1){
            Toast.makeText(this, "Room added successfully with ID: " + roomId, Toast.LENGTH_SHORT).show();
            // Đóng Activity sau khi thêm thành công
            finish();
        } else {
            Toast.makeText(this, "Failed to add room", Toast.LENGTH_SHORT).show();
        }

    }

    private void loadImageFromUrl(String imageUrl) {
        if (!imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(imageViewHotel);
        }
    }
}