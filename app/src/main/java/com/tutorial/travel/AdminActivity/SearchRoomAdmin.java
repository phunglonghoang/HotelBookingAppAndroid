package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

import java.util.List;

public class SearchRoomAdmin extends AppCompatActivity {
    private EditText editTXTROOMNAME;
    private Button btnSearchRoom, buttonBackSearchRoomAdmin;
    private ListView listViewSearchRoomRS;
    private DatabaseHelper db;
    private ArrayAdapter<String> searchRoomArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_room_admin);
        editTXTROOMNAME = findViewById(R.id.editTextRoomName);
        btnSearchRoom = findViewById(R.id.buttonSearchRoom);
        listViewSearchRoomRS = findViewById(R.id.listViewSearchRoomResults);
        buttonBackSearchRoomAdmin = findViewById(R.id.buttonBackSearchRoomAdmin);

        db = new DatabaseHelper(this);

        buttonBackSearchRoomAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchRoomAdmin.this, AdminManagementHotel.class));
            }
        });

        btnSearchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rooms = editTXTROOMNAME.getText().toString().trim();
                searchRoomType(rooms);

            }
        });

        listViewSearchRoomRS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedHotelName = (String) parent.getItemAtPosition(position);


                Intent intent = new Intent(SearchRoomAdmin.this, ModifyDeleteRoomAdmin.class);

                intent.putExtra("selectedHotelName", selectedHotelName);

                startActivity(intent);
            }
        });


    }
    private void searchRoomType(String rooms) {
        if (!TextUtils.isEmpty(rooms)) {
            List<String> searchResults = db.searchRooms(rooms);
            updateSearchResults(searchResults);
        } else {
            Toast.makeText(this, "Please enter a search keyword", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSearchResults(List<String> searchResults) {
        searchRoomArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        listViewSearchRoomRS.setAdapter(searchRoomArrayAdapter);
    }
}