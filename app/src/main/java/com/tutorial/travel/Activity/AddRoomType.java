package com.tutorial.travel.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

import java.util.List;


public class AddRoomType extends AppCompatActivity {
    private Spinner spinnerRoomType, spinnerHotel;
    private EditText editTextRoomType, editTextNumBeds, editTextNumPeople;
    private Button buttonAddRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room_type);

        spinnerRoomType = findViewById(R.id.spinnerRoomType);
        editTextNumBeds = findViewById(R.id.editTextNumBeds);
        editTextNumPeople = findViewById(R.id.editTextNumPeople);
        buttonAddRoom = findViewById(R.id.buttonSaveRoom);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.room_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRoomType.setAdapter(adapter);

        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRoomType();
            }
        });


    }
    private void addRoomType(){

        int numBeds = Integer.parseInt(editTextNumBeds.getText().toString().trim());
        int numPeople = Integer.parseInt(editTextNumPeople.getText().toString().trim());
//        String numBeds = editTextNumBeds.getText().toString().trim();
//        String numPeople = editTextNumPeople.getText().toString().trim();
        String roomType = spinnerRoomType.getSelectedItem().toString();

        DatabaseHelper db = new DatabaseHelper(this);
        long roomid = db.addRoomType( roomType,numBeds,numPeople);

        if(roomid != -1){
            Toast.makeText(this, "Room type added successfully with ID: " + roomid, Toast.LENGTH_SHORT).show();
            // Đóng Activity sau khi thêm thành công
            finish();
        } else {
            Toast.makeText(this, "Failed to add room type", Toast.LENGTH_SHORT).show();
        }

    }


}