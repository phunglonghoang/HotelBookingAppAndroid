package com.tutorial.travel.Activity;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorial.travel.R;
import com.tutorial.travel.model.Booking;

import java.util.List;
public class BookingAdapter extends BaseAdapter {
    private Context context;
    private List<Booking> bookingList;

    public BookingAdapter(Context context, List<Booking> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    @Override
    public int getCount() {
        return bookingList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookingList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.booking_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.roomIdTextView = convertView.findViewById(R.id.roomIdTextView);
            viewHolder.userIdTextView = convertView.findViewById(R.id.userIdTextView);
            viewHolder.checkInDateTextView = convertView.findViewById(R.id.checkInDateTextView);
            viewHolder.checkOutDateTextView = convertView.findViewById(R.id.checkOutDateTextView);
            viewHolder.confirmedTextView = convertView.findViewById(R.id.isConfirmedTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Booking booking = bookingList.get(position);

        viewHolder.roomIdTextView.setText("Room ID: " + booking.getRoomId());
        viewHolder.userIdTextView.setText("User ID: " + booking.getUserId());
        viewHolder.checkInDateTextView.setText("Check In Date: " + booking.getCheckInDate());
        viewHolder.checkOutDateTextView.setText("Check Out Date: " + booking.getCheckOutDate());
        viewHolder.confirmedTextView.setText("Confirmed: " + (booking.getIsConfirmed()));

        return convertView;
    }

    static class ViewHolder {
        TextView roomIdTextView;
        TextView userIdTextView;
        TextView checkInDateTextView;
        TextView checkOutDateTextView;
        TextView confirmedTextView;
    }
}
