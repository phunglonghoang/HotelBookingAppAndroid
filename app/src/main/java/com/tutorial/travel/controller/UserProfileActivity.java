package com.tutorial.travel.controller;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tutorial.travel.Activity.MainActivity;
import com.tutorial.travel.Activity.PasswordUtils;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;

public class UserProfileActivity extends AppCompatActivity {
    EditText edtUsername,edtMail,edtPhone, edtDOB, edtPassword;
    Button btnChangeProfile,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtUsername = findViewById(R.id.edtUsername);
        edtMail = findViewById(R.id.edtMail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtDOB = findViewById(R.id.edtDOB);
        btnChangeProfile = findViewById(R.id.btnChangeProfile);
        btnCancel = findViewById(R.id.btnCancel);

        btnChangeProfile.setOnClickListener(view -> saveChanges());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String username = extras.getString("USERNAME");
            loadUser(username);
        }
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void loadUser(String username) {
        // Khởi tạo DatabaseHelper và SQLiteDatabase
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_USERS + " WHERE " + DatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username});
        if (cursor.moveToFirst()) {
            String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DOB));

            edtUsername.setText(username);
            edtMail.setText(email);
            edtPhone.setText(phone);
            edtDOB.setText(dob);
        }
        cursor.close();
        db.close();
    }
    private void saveChanges() {
        String username = edtUsername.getText().toString().trim();
        String email = edtMail.getText().toString().trim();
        String phone = edtPhone.getText().toString().trim();
        String dob = edtDOB.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_EMAIL, email);
        values.put(DatabaseHelper.COLUMN_PHONE, phone);
        values.put(DatabaseHelper.COLUMN_DOB, dob);
        values.put(DatabaseHelper.COLUMN_PASSWORD, PasswordUtils.hashPassword(password));
        int rowsAffected = db.update(DatabaseHelper.TABLE_USERS, values, DatabaseHelper.COLUMN_USERNAME + "=?", new String[]{username});
        db.close();
        if (rowsAffected > 0) {
            Toast.makeText(UserProfileActivity.this, "Thông tin của bạn đã được cập nhật thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(UserProfileActivity.this, "Đã xảy ra lỗi khi thay đổi thông tin", Toast.LENGTH_SHORT).show();
        }
    }
}