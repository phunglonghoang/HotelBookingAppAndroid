package com.tutorial.travel.AdminActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.tutorial.travel.AdminActivity.Modify_Delete_Hotel;
import com.tutorial.travel.R;
import com.tutorial.travel.model.HotelModel;
import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import java.util.ArrayList;

public class HotelAdapter extends BaseAdapter {
    public static final String SHARED_PREF_NAME = "mypref";
    public static final String KEY_HOTEL_NAME = "hotelName";
    SharedPreferences sharedpreferences;

    Context context;
    ArrayList<HotelModel> arrayListht;
    Button viewHotelDetails;

    public HotelAdapter(Context context, ArrayList<HotelModel> arrayListht) {
        this.context = context;
        this.arrayListht = arrayListht;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return arrayListht.get(i);
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.list_item_hotel,null);
        EditText hotelNameEditText = (EditText)view.findViewById(R.id.admin_HotelName);
        EditText locationEditText = (EditText)view.findViewById(R.id.admin_locationHotelAdmin);
        EditText starRatingEditText = (EditText)view.findViewById(R.id.admin_StarRatingHotelAdmin);
        EditText starIMGEditText = (EditText)view.findViewById(R.id.admin_ImgURLHotelAdmin);
        //final EditText hotelManagerEditText = (EditText)view.findViewById(R.id.admin_ImgURLHotelAdmin);

        HotelModel hotel = arrayListht.get(i);

        hotelNameEditText.setText(hotel.getHotelName());
        locationEditText.setText(hotel.getLocation());
        starRatingEditText.setText(String.valueOf(hotel.getStarRating()));
        starIMGEditText.setText(hotel.getImage());
        // Assuming hotel manager information is not available in the Hotel class
        // You might want to modify this part according to your actual implementation

        viewHotelDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save selected hotel name to shared preferences
                sharedpreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor session = sharedpreferences.edit();
                session.putString(KEY_HOTEL_NAME, hotelNameEditText.getText().toString());
                session.apply();

                // Start activity to view hotel details
                Intent intent = new Intent(view.getContext(), Modify_Delete_Hotel.class);
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return this.arrayListht.size();
    }
}