package com.tutorial.travel.AdminActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.tutorial.travel.model.User;

public class Modify_Delete_Hotel extends AppCompatActivity {
    SharedPreferences sharedpreferences;
    public static final String SHARED_PREF_NAME = "mypref";
    Button admin_modifyHotelAdmin, admin_deleteHotelAdmin, adminViewHomeHotelAdmin;
    EditText admin_HotelName, admin_locationHotelAdmin, admin_StarRatingHotelAdmin, admin_ImgURLHotelAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_modify_delete_hotel);


        //Nút về trang Search_Hotel
//        adminViewHomeHotelAdmin.findViewById(R.id.admin_locationHotelAdmin);
//        adminViewHomeHotelAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Modify_Delete_Hotel.this, SearchHotelAdmin.class));
//            }
//        });

//        admin_deleteHotelAdmin.findViewById(R.id.admin_deleteHotelAdmin);
//        admin_deleteHotelAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

//        admin_modifyHotelAdmin.findViewById(R.id.admin_modifyHotelAdmin);
//        admin_modifyHotelAdmin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


    }
}