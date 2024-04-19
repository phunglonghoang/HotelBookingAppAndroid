package com.tutorial.travel.AdminActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import com.tutorial.travel.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tutorial.travel.model.Booking;


public class BookingListAdapter extends ArrayAdapter<Booking> {
    private Context mContext;
    private int mResource;
    TextView roomIdTextView, userIdTextView, checkInDateTextView, checkOutDateTextView, isConfirmedTextView;

    public BookingListAdapter(Context context, int resource, List<Booking> objects) {
        super(context, resource, objects);
        this.mContext = mContext;
        this.mResource = mResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String roomId = getItem(position).getRoomId();
        String userId = getItem(position).getUserId();
        String checkInDate = getItem(position).getCheckInDate();
        String checkOutDate = getItem(position).getCheckOutDate();
        int isConfirmed = getItem(position).getIsConfirmed();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);



        roomIdTextView = convertView.findViewById(R.id.roomIdTextView);
        userIdTextView = convertView.findViewById(R.id.userIdTextView);
        checkInDateTextView = convertView.findViewById(R.id.checkInDateTextView);
        checkOutDateTextView = convertView.findViewById(R.id.checkOutDateTextView);
        isConfirmedTextView = convertView.findViewById(R.id.isConfirmedTextView);

        roomIdTextView.setText("Room ID: " + roomId);
        userIdTextView.setText("User ID: " + userId);
        checkInDateTextView.setText("Check-in: " + checkInDate);
        checkOutDateTextView.setText("Check-out: " + checkOutDate);
        isConfirmedTextView.setText("Confirmed: " + isConfirmed);

        return convertView;
    }
}
