package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.Activity.MainActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

import java.util.ArrayList;

public class List_of_Reservations extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView listView;
    Button ButtonLogout, ButtonHome;
    Intent myIntent;
    ArrayList<String> ID_Array;
    ArrayList<String> Reservation_Date_Array;
    ArrayList<String> Room_Type_Array;
    ArrayList<String> Number_of_Room_Array;
    ArrayList<String> CheckIn_Array;
    ArrayList<String> CheckOut_Array;
    ArrayList<String> First_Name_Array;
    ArrayList<String> Last_Name_Array;
    ArrayList<String> Number_Of_Adults_Array;
    ArrayList<String> Number_Of_Children_Array;
    ArrayList<String> Total_Price_Array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_of_reservations);

        ButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_of_Reservations.this, MainActivity.class));
            }
        });



        ButtonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(List_of_Reservations.this, AdminMainActivity.class));
            }
        });
    }
}