package com.tutorial.travel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tutorial.travel.R;

import com.tutorial.travel.model.RoomModel;

import java.util.List;

class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private List<RoomModel> roomList;
    private Context context;

    public RoomAdapter(Context context, List<RoomModel> roomList) {
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
        holder.roomTypeTxt.setText(room.getRoomTypeId());
//        holder.roomDescriptionTxt.setText(room.ge());
        holder.roomPriceTxt.setText(String.format("%s VND", room.getPrice()));
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView roomTypeTxt, roomDescriptionTxt, roomPriceTxt;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            roomTypeTxt = itemView.findViewById(R.id.roomTypeTxt);
            roomDescriptionTxt = itemView.findViewById(R.id.tvRoomDescription);
            roomPriceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }
}
