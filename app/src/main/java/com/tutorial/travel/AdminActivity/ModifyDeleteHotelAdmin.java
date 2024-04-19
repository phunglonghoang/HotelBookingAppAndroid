package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

public class ModifyDeleteHotelAdmin extends AppCompatActivity {
    private EditText editTextHotelName, editTextLocation, editTextStarRating, editTextImgURL;
    private Button buttonModify, buttonDelete, buttonHome;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_delete_hotel_admin);

        // Kết nối giao diện XML với mã Java
        editTextHotelName = findViewById(R.id.admin_HotelName);
        editTextLocation = findViewById(R.id.admin_locationHotelAdmin);
        editTextStarRating = findViewById(R.id.admin_StarRatingHotelAdmin);
        editTextImgURL = findViewById(R.id.admin_ImgURLHotelAdmin);
        buttonModify = findViewById(R.id.admin_modifyHotelAdmin);
        buttonDelete = findViewById(R.id.admin_deleteHotelAdmin);
        buttonHome = findViewById(R.id.adminViewHomeHotelAdmin);

        db = new DatabaseHelper(this);

        // Lấy thông tin hotel hiện tại và hiển thị lên trang sửa hotel
        String selectedHotelName = getIntent().getStringExtra("selectedHotelName");
        displayHotelInfo(selectedHotelName);

        // Xử lý sự kiện khi nhấn nút sửa hotel
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin đã chỉnh sửa từ người dùng
                String hotelName = editTextHotelName.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();
                String starRating = editTextStarRating.getText().toString().trim();
                String imgUrl = editTextImgURL.getText().toString().trim();

                // Cập nhật thông tin của hotel vào cơ sở dữ liệu
                assert selectedHotelName != null;
                boolean isUpdated = db.updateHotel(Long.parseLong(selectedHotelName), hotelName, location, Float.parseFloat(starRating), imgUrl);
                if (isUpdated) {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Hotel information updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Failed to update hotel information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút xóa hotel
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa hotel khỏi cơ sở dữ liệu
                boolean isDeleted = db.deleteHotel(selectedHotelName);
                if (isDeleted) {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Hotel deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Failed to delete hotel", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi nhấn nút HOME
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyDeleteHotelAdmin.this, AdminMainActivity.class));
                finish();
            }
        });
    }

    private void displayHotelInfo(String selectedHotelName) {
        // Lấy thông tin của hotel từ cơ sở dữ liệu và hiển thị lên trang sửa hotel
        HotelModel hotel = db.getHotelByName(selectedHotelName);
        editTextHotelName.setText(hotel.getHotelName());
        editTextLocation.setText(hotel.getLocation());
        editTextStarRating.setText(String.valueOf(hotel.getStarRating()));
        editTextImgURL.setText(hotel.getImage());
    }
}
