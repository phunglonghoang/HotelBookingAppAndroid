package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.Activity.UserAdapter;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.User;

import java.util.ArrayList;

public class ListUserAdmin extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private Button buttonBackListUserAdmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_user_admin);

        recyclerView = findViewById(R.id.recyclerViewUserList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lấy danh sách người dùng từ database
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<User> userList = databaseHelper.getAllUsersNO();

        // Thiết lập Adapter cho RecyclerView
        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);
        buttonBackListUserAdmin = findViewById(R.id.buttonBackListUserAdmin);
        buttonBackListUserAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hành động khi nhấn nút back
                startActivity(new Intent(ListUserAdmin.this, AccountManagement.class));
            }
        });
    }
}