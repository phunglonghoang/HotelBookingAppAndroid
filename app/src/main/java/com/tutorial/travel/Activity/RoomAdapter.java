package com.tutorial.travel.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tutorial.travel.R;
import com.tutorial.travel.model.Room;
import com.tutorial.travel.model.RoomModel;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<Room> {
    private Context mcontext;
    private int mresource;

    public RoomAdapter(@NonNull Context context, int resource,  @NonNull List<Room> objects) {
        super(context, resource, objects);
        mcontext = context;
        mresource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String roomName = getItem(position).getRoomName();
        String roomType = getItem(position).getRoomType();
        String roomRate = getItem(position).getRoomRate();

        LayoutInflater inflater = LayoutInflater.from(mcontext);
        convertView = inflater.inflate(mresource,parent,false);

        TextView textViewRoomName = convertView.findViewById(R.id.textViewRoomName);
        TextView textViewRoomType = convertView.findViewById(R.id.textViewRoomType);
        TextView textViewRoomRate = convertView.findViewById(R.id.textViewRoomRate);

        textViewRoomName.setText(roomName);
        textViewRoomType.setText(roomType);
        textViewRoomRate.setText(roomRate);

        return convertView;
    }
}
