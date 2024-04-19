package com.tutorial.travel.Activity;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.tutorial.travel.AdminActivity.adminViewGuestManager;
import com.tutorial.travel.R;
import com.tutorial.travel.model.User;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    public static final String SHARED_PREF_NAME = "mypref";
    public static final String Key_un = "username";

    SharedPreferences sharedPreferences;

    Context context;

    ArrayList<User> arrayList;

    Button viewGM;

    public MyAdapter(Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_layout, null);
//        EditText fn = (EditText)view.findViewById(R.id.admin_fmg);
        EditText ln = (EditText)view.findViewById(R.id.admin_lmg);
//        EditText rl = (EditText)view.findViewById(R.id.admin_rmg);
        final EditText un = (EditText)view.findViewById(R.id.admin_umg);
        viewGM = (Button) view.findViewById(R.id.admin_viewGM);

        User user = arrayList.get(position);

        ln.setText(user.getEmail());
//        rl.setText(user.getPhone());
        un.setText(user.getUsername());

        viewGM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

                SharedPreferences.Editor session =sharedPreferences.edit();
                session.putString(Key_un, un.getText().toString());
                session.apply();

                Intent intent = new Intent(view.getContext(), adminViewGuestManager.class);
                view.getContext().startActivity(intent);


            }
        });
        return view;
    }
}
