package com.tutorial.travel.Activity;

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

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.Hotel;

import java.util.List;

public class SearchHotelAdmin extends AppCompatActivity {
    private EditText editTXTHOTELNAME;
    private Button btnSearch, buttonBackSearchHotelAdmin;
    private ListView listViewSearchRS;
    private DatabaseHelper db;
    private ArrayAdapter<String> searchHotelArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_hotel_admin);

        editTXTHOTELNAME = findViewById(R.id.editTextHotelName);
        btnSearch = findViewById(R.id.buttonSearchHotel);
        listViewSearchRS = findViewById(R.id.listViewSearchResults);
        buttonBackSearchHotelAdmin = findViewById(R.id.buttonBackSearchHotelAdmin);

        db = new DatabaseHelper(this);

        buttonBackSearchHotelAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchHotelAdmin.this, AdminManagementHotel.class));
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hotelnames = editTXTHOTELNAME.getText().toString().trim();
                searchHotel(hotelnames);
            }
        });


        //Phần này đang lỗi, bấm vô thì bị văng ra, có thể lỗi hàm này hoac onCreate bên Modify_Delete lỗi
        listViewSearchRS.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedHotelName = (String) parent.getItemAtPosition(position);

                if (selectedHotelName != null) {
                    Intent intent = new Intent(SearchHotelAdmin.this, Modify_Delete_Hotel.class);
                    intent.putExtra("selectedHotelName", selectedHotelName);
                    startActivity(intent);
                } else {
                    Toast.makeText(SearchHotelAdmin.this, "Error: Selected hotel not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void searchHotel(String hotelnames) {
        if (!TextUtils.isEmpty(hotelnames)) {
            List<String> searchResults = db.searchHotels(hotelnames);
            updateSearchResults(searchResults);
        } else {
            Toast.makeText(this, "Please enter a search keyword", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSearchResults(List<String> searchResults) {
        searchHotelArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, searchResults);
        listViewSearchRS.setAdapter(searchHotelArrayAdapter);
    }
}
