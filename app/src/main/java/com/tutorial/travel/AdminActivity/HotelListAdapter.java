package com.tutorial.travel.AdminActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tutorial.travel.R;
import com.tutorial.travel.model.HotelModel;

import java.util.List;

public class HotelListAdapter extends BaseAdapter {
    private Context context;
    private List<HotelModel> hotelList;

    public HotelListAdapter(Context context, List<HotelModel> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hotel_list, parent, false);
        }

        TextView hotelNameTextView = convertView.findViewById(R.id.hotel_name);
        TextView locationTextView = convertView.findViewById(R.id.location);
        TextView starRatingTextView = convertView.findViewById(R.id.star_rating);

        HotelModel hotel = hotelList.get(position);

        hotelNameTextView.setText(hotel.getHotelName());
        locationTextView.setText(hotel.getLocation());
        starRatingTextView.setText(String.valueOf(hotel.getStarRating()));

        return convertView;
    }
}
