package com.tutorial.travel.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tutorial.travel.AdminActivity.AdminMainActivity;
import com.tutorial.travel.Activity.MainActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {
    Handler handler;
    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        dbHelper = new DatabaseHelper(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập tên người dùng và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                int roleId = dbHelper.getUserRoleId(username, password);

                // Kiểm tra kết quả trả về từ getUserRoleId
                if (roleId == -1) {
                    Toast.makeText(LoginActivity.this, "Tên người dùng hoặc mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                switch (roleId) {
                    case 1: // Admin
                        Intent adminIntent = new Intent(LoginActivity.this, AdminMainActivity.class);
                        startActivity(adminIntent);
                        break;

                    case 2: // Customer

//                        // Lưu tên người dùng vào SharedPreferences
//                        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = preferences.edit();
//                        editor.putString("username", username);
//                        editor.apply();
//
//                        // Chuyển đến MainActivity và gửi tên người dùng dùng SharedPreferences
//
//
//                        Intent customerIntent = new Intent(LoginActivity.this,
//                                MainActivity.class);
//
//                        Bundle bundle = new Bundle();
//                        bundle.putString("username", username);
//                        customerIntent.putExtras(bundle);
//
//                        startActivity(customerIntent);
                        // Lưu tên người dùng vào SharedPreferences
                        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", username);
                        editor.apply();

                        // Chuyển đến MainActivity và gửi tên người dùng dùng SharedPreferences
                        Intent customerIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(customerIntent);
                        break;
                    default:
                        // Xử lý trường hợp không chính xác
                        Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        break;
                }
                finish();
            }
        });
    }
    public void onRegisterClick(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
