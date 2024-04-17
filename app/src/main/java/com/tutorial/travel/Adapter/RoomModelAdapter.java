package com.tutorial.travel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tutorial.travel.Activity.HotelDetailActivity;
import com.tutorial.travel.R;
import com.tutorial.travel.model.ReviewModel;
import com.tutorial.travel.model.RoomModel;

import java.util.List;

public class RoomModelAdapter extends RecyclerView.Adapter<RoomModelAdapter.RoomViewHolder> {

    private List<RoomModel> roomList;
    private Context context;

    public RoomModelAdapter(Context context, List<RoomModel> roomList) {
        this.context = context;
        this.roomList = roomList;
    }


    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        RoomModel room = roomList.get(position);

        holder.roomNameTxt.setText(room.getRoomName());
        holder.priceTxt.setText(String.format("%s VND", room.getPrice()));
//        holder.descriptionTxt.setText(room.getDescription());

        // Load hình ảnh bằng Picasso
        Picasso.get().load(room.getRoomImage()).placeholder(R.drawable.pic2).into(holder.roomImage);
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        ImageView roomImage;
        TextView roomNameTxt, priceTxt, descriptionTxt;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            roomImage = itemView.findViewById(R.id.roomImage);
            roomNameTxt = itemView.findViewById(R.id.roomNameTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);
            descriptionTxt = itemView.findViewById(R.id.descriptionTxt);
        }
    }
}
