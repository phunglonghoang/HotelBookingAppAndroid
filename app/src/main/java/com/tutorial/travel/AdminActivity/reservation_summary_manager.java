package com.tutorial.travel.AdminActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.R;

public class reservation_summary_manager extends AppCompatActivity   {
    SQLiteDatabase sqLiteDatabaseObj;
    EditText EditTextDate;
    Button ButtonLogOut, ButtonHome, ButtonListView;
    RadioGroup RadioGroupRoomType;
    RadioButton RadioButtonStandard, RadioButtonDeluxe, RadioButtonSuite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation_summary_manager);

        ButtonLogOut = (Button)findViewById(R.id.button_logout);
        ButtonHome = (Button) findViewById(R.id.button_home);
        ButtonListView = (Button) findViewById(R.id.buttonListView);
        EditTextDate = (EditText)findViewById(R.id.editTextDate);
        RadioGroupRoomType = (RadioGroup) findViewById(R.id.radioGroup);
        RadioButtonStandard = (RadioButton) findViewById(R.id.radioButtonStandard);
        RadioButtonDeluxe = (RadioButton) findViewById(R.id.radioButtonDeluxe);
        RadioButtonSuite = (RadioButton) findViewById(R.id.radioButtonSuite);

        ButtonListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = EditTextDate.getText().toString();
                String roomType = "";

                int selectedRadioButtonId = RadioGroupRoomType.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                if (selectedRadioButton != null) {
                    roomType = selectedRadioButton.getText().toString();
                }
                if(roomType.isEmpty() || date.isEmpty()){
                    Toast.makeText(reservation_summary_manager.this,"Please fill the reservation date!", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(reservation_summary_manager.this, List_of_Reservations.class);
                intent.putExtra("date", date);
                intent.putExtra("roomType", roomType);
                startActivity(intent);
            }
        });
    }
}