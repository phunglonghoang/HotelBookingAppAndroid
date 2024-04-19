package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.User;

public class addUser extends AppCompatActivity {
    Button add,home;
    DatabaseHelper dbHelper;
    EditText admin_username, admin_pwd, admin_email, admin_phone, admin_DOB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user);

        admin_username = findViewById(R.id.admin_username);
        admin_pwd = findViewById(R.id.admin_pwd);
        admin_email = findViewById(R.id.admin_email);
        admin_phone = findViewById(R.id.admin_phone);
        admin_DOB = findViewById(R.id.admin_DOB);

        add =findViewById(R.id.admin_add);
        home = findViewById(R.id.adminHome);
        dbHelper = new DatabaseHelper(this);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(addUser.this, AccountManagement.class));

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = admin_username.getText().toString().trim();
                String password = admin_pwd.getText().toString().trim();
                String email = admin_email.getText().toString().trim();
                String phone = admin_phone.getText().toString().trim();
                String sanhnhat = admin_DOB.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    User user = new User();
                    user.setUsername(username);
                    user.setEmail(email);
                    user.setPhone(phone);
                    user.setPassword(password);
                    user.setDOB(sanhnhat);
                    long userId = dbHelper.addUser(user);

                    if (userId != -1) {
                        Toast.makeText(addUser.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(addUser.this, addUser.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(addUser.this, "Đã xảy ra lỗi khi đăng ký người dùng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(addUser.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}