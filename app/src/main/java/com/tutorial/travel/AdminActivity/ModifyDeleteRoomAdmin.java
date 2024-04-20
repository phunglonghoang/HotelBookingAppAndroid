package com.tutorial.travel.AdminActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.model.Room;

import java.util.List;

public class ModifyDeleteRoomAdmin extends AppCompatActivity {
    EditText editTextRoomName, editTextTypeRoom, editTextPrice, editTextImageUrl;
    Button buttonModify, buttonDelete, buttonHome;

    DatabaseHelper dbHelper;
    String selectedRoomName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_delete_room_admin);

        editTextRoomName = findViewById(R.id.admin_RoomName);
        editTextTypeRoom = findViewById(R.id.admin_locationRoomAdmin);
        editTextPrice = findViewById(R.id.admin_PriceRoomAdmin);
        editTextImageUrl = findViewById(R.id.admin_ImgURLRoomAdmin);

        buttonModify = findViewById(R.id.admin_modifyHotelAdmin);
        buttonDelete = findViewById(R.id.admin_deleteHotelAdmin);
        buttonHome = findViewById(R.id.adminViewHomeHotelAdmin);

        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("selectedHotelName")) {
            selectedRoomName = intent.getStringExtra("selectedHotelName");
            loadRoomDetails(selectedRoomName);
        }


        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ModifyDeleteRoomAdmin.this, AdminMainActivity.class));
            }
        });
        buttonModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyRoom();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoom();
            }
        });

    }

    private void loadRoomDetails(String roomname) {
        Room room = dbHelper.hotelViewRoom(roomname);
        if (room != null) {
            editTextRoomName.setText(room.getRoomName());
            editTextTypeRoom.setText(room.getRoomType());
            editTextPrice.setText(room.getPrice());
            editTextImageUrl.setText(room.getImage());
        } else {
            // Handle the case where hotel details are not found
            Toast.makeText(this, "Hotel details not found", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteRoom() {
        if (dbHelper.deleteRoom(selectedRoomName)) {
            Toast.makeText(this, "Room deleted successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity after successful deletion
        } else {
            Toast.makeText(this, "Failed to delete room", Toast.LENGTH_SHORT).show();
        }
    }

    private void modifyRoom() {
        String roomName = editTextRoomName.getText().toString();
        String typeRoom = editTextTypeRoom.getText().toString();
        String price = editTextPrice.getText().toString();
        String imageUrl = editTextImageUrl.getText().toString();

        // Lấy hotelId dựa trên tên phòng
        int roomId = dbHelper.getRoomIdByName(selectedRoomName);

        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyDeleteRoomAdmin.this);
        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Thực hiện cập nhật thông tin của khách sạn
                if (dbHelper.updateRoom(String.valueOf(roomId), roomName, typeRoom, price, imageUrl)) {
                    Toast.makeText(ModifyDeleteRoomAdmin.this, "Room updated successfully", Toast.LENGTH_SHORT).show();
                    // Đóng activity sau khi cập nhật thành công
                } else {
                    Toast.makeText(ModifyDeleteRoomAdmin.this, "Failed to update Room", Toast.LENGTH_SHORT).show();
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

}