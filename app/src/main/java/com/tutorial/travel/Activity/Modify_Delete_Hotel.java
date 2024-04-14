package com.tutorial.travel.Activity;

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
import com.tutorial.travel.model.Hotel;
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

        sharedpreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        adminViewHomeHotelAdmin = findViewById(R.id.adminViewHomeHotelAdmin);
        admin_modifyHotelAdmin = findViewById(R.id.admin_modifyHotelAdmin);
        admin_deleteHotelAdmin = findViewById(R.id.admin_deleteHotelAdmin);

        admin_HotelName = findViewById(R.id.admin_HotelName);
        admin_locationHotelAdmin = findViewById(R.id.admin_locationHotelAdmin);
        admin_StarRatingHotelAdmin = findViewById(R.id.admin_StarRatingHotelAdmin);
        admin_ImgURLHotelAdmin = findViewById(R.id.admin_ImgURLHotelAdmin);

        adminViewHomeHotelAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Modify_Delete_Hotel.this, AdminManagementHotel.class));
            }
        });

        final String keyHotelName = sharedpreferences.getString(MyAdapter.Key_un, "");
        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(Modify_Delete_Hotel.this);
        Hotel hotel = dbDatabaseHelper.adminViewHotel(keyHotelName);

        adminViewHomeHotelAdmin.setText(hotel.getHotelName());

        admin_HotelName.setFocusableInTouchMode(true);
        admin_locationHotelAdmin.setFocusableInTouchMode(true);
        admin_StarRatingHotelAdmin.setFocusableInTouchMode(true);
        admin_ImgURLHotelAdmin.setFocusableInTouchMode(true);

        admin_modifyHotelAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_HotelName.setFocusableInTouchMode(true);
                admin_locationHotelAdmin.setFocusableInTouchMode(true);
                admin_StarRatingHotelAdmin.setFocusableInTouchMode(true);
                admin_ImgURLHotelAdmin.setFocusableInTouchMode(true);

                DatabaseHelper db = new DatabaseHelper(Modify_Delete_Hotel.this);
                Hotel hotel1 = new Hotel(admin_HotelName.getText().toString(), admin_locationHotelAdmin.getText().toString(), Integer.parseInt(admin_StarRatingHotelAdmin.getText().toString()), admin_ImgURLHotelAdmin.getText().toString());
                final boolean updateResult = db.adminUpdateHotelpf(hotel1,keyHotelName);
                AlertDialog.Builder builder = new AlertDialog.Builder(Modify_Delete_Hotel.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if(updateResult)
                        {

                            startActivity(new Intent(Modify_Delete_Hotel.this,adminViewGuestManager.class));
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        admin_deleteHotelAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Modify_Delete_Hotel.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        DatabaseHelper dbManager = new DatabaseHelper(Modify_Delete_Hotel.this);
                        final boolean res = dbManager.deteleHotel(keyHotelName);

                        if(res)
                        {
                            startActivity(new Intent(Modify_Delete_Hotel.this,SearchHotelAdmin.class));
                        }

                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }
}