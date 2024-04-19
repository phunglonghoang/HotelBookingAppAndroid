package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.Activity.MyAdapter;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.User;

import java.util.ArrayList;

public class SearchGuestUser extends AppCompatActivity {
    Button search,logout;
    EditText lastName;
    ListView lv_customerList;
    ArrayList<User> arrayList;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_guest_user);

        lastName = findViewById(R.id.admin_lastname);
        search = findViewById(R.id.admin_search);
        lv_customerList = findViewById(R.id.admin_list);

        logout = findViewById(R.id.searchAdminLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SearchGuestUser.this, AccountManagement.class));
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String abc =lastName.getText().toString();
                DatabaseHelper db = new DatabaseHelper(SearchGuestUser.this);
                arrayList = db.getAllUsers(abc);

                myAdapter = new MyAdapter(SearchGuestUser.this,arrayList);
                lv_customerList.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        });


    }
}