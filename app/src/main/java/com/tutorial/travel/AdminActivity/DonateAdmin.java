package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;

public class DonateAdmin extends AppCompatActivity {
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_donate_admin);

        TextView bankInfoTextView = findViewById(R.id.bankInfo);
        String bankInfo = "Thông tin tài khoản ngân hàng:\nTên ngân hàng: Agribank\nSố tài khoản: 6000205584412\nChủ tài khoản: Nguyễn Quốc Huy";
        bankInfoTextView.setText(bankInfo);
    backButton = findViewById(R.id.backButton);
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(DonateAdmin.this, AdminMainActivity.class));
            finish();
        }
    });
    }
}