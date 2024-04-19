package com.tutorial.travel.AdminActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;

public class ModifyDeleteHotelAdmin extends AppCompatActivity {

    EditText editTextHotelName, editTextLocation, editTextStarRating, editTextImageUrl;
    Button buttonModify, buttonDelete, buttonHome;
    DatabaseHelper dbHelper;
    String selectedHotelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_delete_hotel_admin);

        editTextHotelName = findViewById(R.id.admin_HotelName);
        editTextLocation = findViewById(R.id.admin_locationHotelAdmin);
        editTextStarRating = findViewById(R.id.admin_StarRatingHotelAdmin);
        editTextImageUrl = findViewById(R.id.admin_ImgURLHotelAdmin);

        buttonModify = findViewById(R.id.admin_modifyHotelAdmin);
        buttonDelete = findViewById(R.id.admin_deleteHotelAdmin);
        buttonHome = findViewById(R.id.adminViewHomeHotelAdmin);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedHotelName")) {
            selectedHotelName = intent.getStringExtra("selectedHotelName");
            loadHotelDetails(selectedHotelName);
        }

        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyHotel();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHotel();
            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAdminActivity();
            }
        });
    }

    private void loadHotelDetails(String hotelName) {
        HotelModel hotel = dbHelper.hotelViewHotel(hotelName);
        if (hotel != null) {
            editTextHotelName.setText(hotel.getHotelName());
            editTextLocation.setText(hotel.getLocation());
            editTextStarRating.setText(String.valueOf(hotel.getStarRating()));
            editTextImageUrl.setText(hotel.getImage());
        } else {
            // Handle the case where hotel details are not found
            Toast.makeText(this, "Hotel details not found", Toast.LENGTH_SHORT).show();
        }
    }


    private void modifyHotel() {
        String hotelName = editTextHotelName.getText().toString();
        String location = editTextLocation.getText().toString();
        int starRating = Integer.parseInt(editTextStarRating.getText().toString());
        String imageUrl = editTextImageUrl.getText().toString();

        // Lấy hotelId dựa trên tên khách sạn
        int hotelId = dbHelper.getHotelIdByName(selectedHotelName);

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyDeleteHotelAdmin.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện cập nhật thông tin của khách sạn
                if (dbHelper.updateHotel(String.valueOf(hotelId), hotelName, location, starRating, imageUrl)) {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Hotel updated successfully", Toast.LENGTH_SHORT).show();
                    // Đóng activity sau khi cập nhật thành công
                } else {
                    Toast.makeText(ModifyDeleteHotelAdmin.this, "Failed to update hotel", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Không làm gì khi người dùng chọn NO
                dialog.dismiss();
            }
        });
        builder.show();
    }




    private void deleteHotel() {
        if (dbHelper.deleteHotel(selectedHotelName)) {
            Toast.makeText(this, "Hotel deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after successful deletion
        } else {
            Toast.makeText(this, "Failed to delete hotel", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAdminActivity() {
        Intent intent = new Intent(ModifyDeleteHotelAdmin.this, AdminMainActivity.class);
        startActivity(intent);
        finish(); // Close the current activity to prevent user from returning to it
    }
}
