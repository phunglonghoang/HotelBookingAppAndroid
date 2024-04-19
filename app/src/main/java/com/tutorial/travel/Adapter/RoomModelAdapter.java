package com.tutorial.travel.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tutorial.travel.Activity.BookingActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.model.RoomModel;

import java.util.List;

public class RoomModelAdapter extends RecyclerView.Adapter<RoomModelAdapter.RoomViewHolder> {

    private List<RoomModel> roomList;
    private Context context;
    private String defaultCheckInDate;
    private String defaultCheckOutDate;

    public RoomModelAdapter(Context context, List<RoomModel> roomList, String defaultCheckInDate, String defaultCheckOutDate) {
        this.context = context;
        this.roomList = roomList;
        this.defaultCheckInDate = defaultCheckInDate;
        this.defaultCheckOutDate = defaultCheckOutDate;
    }
    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomModel room = roomList.get(position);

        if (room == null || room.getRoomStatus() == null) {
            return;
        }

        if (room.getRoomStatus().equals("Available")) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        holder.roomNameTxt.setText(room.getRoomName());
        holder.priceTxt.setText(String.format("%s VND", room.getPrice()));

        if (holder.descriptionTxt != null) {
            holder.descriptionTxt.setText(room.getDescription());
        }

        holder.roomStatusTxt.setText("Status: " + room.getRoomStatus());

        holder.bookRoombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookingActivity.class);
                intent.putExtra("roomId", room.getId());
                intent.putExtra("hotelId", room.getHotelId());
                intent.putExtra("price", room.getPrice());
                intent.putExtra("checkInDate", defaultCheckInDate);
                intent.putExtra("checkOutDate", defaultCheckOutDate);
                intent.putExtra("roomName", room.getRoomName());
                context.startActivity(intent);
            }
        });

        Log.d("RoomAdapter", "Loading image from URL: " + room.getRoomImage());
        Picasso.get().load(room.getRoomImage()).into(holder.roomImage, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("RoomAdapter", "Image loaded successfully");
            }

            @Override
            public void onError(Exception e) {
                Log.e("RoomAdapter", "Error loading image: " + e.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView roomNameTxt, priceTxt, descriptionTxt, roomStatusTxt;
        Button bookRoombtn;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            roomNameTxt = itemView.findViewById(R.id.roomNameTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            roomImage = itemView.findViewById(R.id.roomImage);
            roomStatusTxt = itemView.findViewById(R.id.roomStatusTxt);
            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
            bookRoombtn = itemView.findViewById(R.id.bookRoombtn);
        }
    }
}