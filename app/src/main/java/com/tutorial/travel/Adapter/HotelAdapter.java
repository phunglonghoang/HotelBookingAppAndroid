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
import com.tutorial.travel.model.HotelModel;
import com.tutorial.travel.R;

import java.util.List;

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    private Context context;
    private List<HotelModel> hotelList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(HotelModel hotel);
    }

    public HotelAdapter(Context context, List<HotelModel> hotelList,OnItemClickListener listener) {
        this.context = context;
        this.hotelList = hotelList;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hotel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HotelModel hotel = hotelList.get(position);
        holder.hotelNameTxt.setText(hotel.getHotelName());
        holder.locationTxt.setText(hotel.getLocation());
        holder.startTxt.setText(String.valueOf(hotel.getStarRating()));
        holder.priceTxt.setText(String.valueOf(hotel.getMinRoomPrice()));
        Picasso.get().load(hotel.getImage()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(hotel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hotelNameTxt, locationTxt, startTxt, priceTxt;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hotelNameTxt = itemView.findViewById(R.id.hotelNameTxt);
            locationTxt = itemView.findViewById(R.id.locationTxt);
            startTxt = itemView.findViewById(R.id.startTxt);
            imageView = itemView.findViewById(R.id.imageViewHotel);
            priceTxt = itemView.findViewById(R.id.priceTxt);
        }
    }
}