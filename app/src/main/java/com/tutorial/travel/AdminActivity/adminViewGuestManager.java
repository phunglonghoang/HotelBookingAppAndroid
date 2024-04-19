package com.tutorial.travel.AdminActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.tutorial.travel.Activity.MyAdapter;
import com.tutorial.travel.R;
import com.tutorial.travel.database.DatabaseHelper;
import com.tutorial.travel.model.User;

public class adminViewGuestManager extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    DatabaseHelper dbHelper;
    public static final String SHARED_PREF_NAME = "mypref";
    RadioGroup radioGroupRole;
    RadioButton ButtonAdmin, ButtonUser;
    String roleId ; // Mặc định là 2 (User)
    Button admin_modifyGM, admin_deleteGM, home;
    EditText admin_userGM, admin_roleGM, admin_pwdGM, admin_emailGM, admin_phone;
    TextView admin_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_view_guest_manager);

        //Radio Button
        radioGroupRole = findViewById(R.id.radioGrRole);
        ButtonAdmin = findViewById(R.id.radioButtonAdmin);
        ButtonUser = findViewById(R.id.radioButtonUser);




        sharedpreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        final String keyUserName = sharedpreferences.getString(MyAdapter.Key_un, "");

        admin_userGM = findViewById(R.id.admin_userGM);
        admin_pwdGM = findViewById(R.id.admin_pwdGM); //email
        admin_emailGM = findViewById(R.id.admin_emailGM); //phone
        admin_phone = findViewById(R.id.admin_phoneGM);//birthday
        admin_roleGM = findViewById(R.id.admin_roleGM);//password

        //button
        admin_modifyGM = findViewById(R.id.admin_modifyGM);
        admin_deleteGM = findViewById(R.id.admin_deleteGM);
        //radio button


        home = findViewById(R.id.adminViewHome);


        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminViewGuestManager.this, AdminMainActivity.class));
            }
        });


        DatabaseHelper dbDatabaseHelper = new DatabaseHelper(adminViewGuestManager.this);
        User user = dbDatabaseHelper.adminViewUser(keyUserName);

        admin_userGM.setText(user.getUsername());
        admin_pwdGM.setText(user.getPassword());
        admin_emailGM.setText(user.getEmail());
        admin_phone.setText(user.getPhone());
        admin_roleGM.setText(user.getDOB());

        admin_userGM.setFocusableInTouchMode(true);
        admin_pwdGM.setFocusableInTouchMode(true);
        admin_emailGM.setFocusableInTouchMode(true);
        admin_phone.setFocusableInTouchMode(true);
        admin_roleGM.setFocusableInTouchMode(true);

        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButtonAdmin) {
                    // Nếu chọn RadioButton Admin, cập nhật roleId là 1
                    user.setRoleId("1");
                } else if (checkedId == R.id.radioButtonUser) {
                    // Nếu chọn RadioButton User, cập nhật roleId là 2
                    user.setRoleId("2");
                }
            }
        });
        admin_modifyGM.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                admin_userGM.setFocusableInTouchMode(true);
                admin_pwdGM.setFocusableInTouchMode(true);
                admin_emailGM.setFocusableInTouchMode(true);
                admin_phone.setFocusableInTouchMode(true);
                admin_roleGM.setFocusableInTouchMode(true);

                admin_deleteGM.setVisibility(View.INVISIBLE);

            }

        });

        admin_modifyGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                admin_deleteGM.setVisibility(View.VISIBLE);

                String newRoleId;
                if (ButtonAdmin.isChecked()) {
                    newRoleId = "1"; // Admin
                } else {
                    newRoleId = "2"; // User
                }


                DatabaseHelper db = new DatabaseHelper(adminViewGuestManager.this);
                User user = new User(admin_userGM.getText().toString(), admin_pwdGM.getText().toString(), admin_emailGM.getText().toString(), admin_phone.getText().toString(), admin_roleGM.getText().toString());
                user.setRoleId(newRoleId);
                final boolean uppdateResult = db.adminUpdateProfile(user, keyUserName);
                AlertDialog.Builder builder = new AlertDialog.Builder(adminViewGuestManager.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");


                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        if (uppdateResult) {

                            startActivity(new Intent(adminViewGuestManager.this, adminViewGuestManager.class));
                            Toast.makeText(adminViewGuestManager.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

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
        radioGroupRole.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.radioButtonAdmin) {
                    // Nếu chọn RadioButton Admin, cập nhật roleId là 1
                    ButtonAdmin.setChecked(true);// Đánh dấu RadioButton Admin là được chọn
                    ButtonUser.setChecked(false);
                } else{
                    ButtonAdmin.setChecked(false);
                    ButtonUser.setChecked(true); // Đánh dấu RadioButton User là được chọn
                }
            }
        });

        admin_deleteGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(adminViewGuestManager.this);

                builder.setTitle("Confirm");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        DatabaseHelper db = new DatabaseHelper(adminViewGuestManager.this);
                        final boolean res = db.deteleUser(keyUserName);

                        if (res) {
                            startActivity(new Intent(adminViewGuestManager.this, SearchGuestUser.class));
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