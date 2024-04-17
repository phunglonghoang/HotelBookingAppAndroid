package com.tutorial.travel.Adapter;
import android.annotation.SuppressLint;
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

import java.text.CollationElementIterator;
import java.util.List;
public class HotelByLocationAdapter extends RecyclerView.Adapter<HotelByLocationAdapter.ViewHolder>{
    private Context context;
    private List<HotelModel> hotelLocationList;
    private OnHotelClickListener onHotelClickListener;
//    public interface OnHotelClickListener {
//        void onHotelClick(int hotel_id);
//    }
//public HotelByLocationAdapter(Context context, List<HotelModel> hotelLocationList, OnHotelClickListener onHotelClickListener) {
//    this.context = context;
//    this.hotelLocationList = hotelLocationList;
//    this.onHotelClickListener= onHotelClickListener;
//}
    public interface OnHotelClickListener {
        void onHotelClick(HotelModel hotel_id);
    }

    public HotelByLocationAdapter(Context context, List<HotelModel> hotelLocationList, OnHotelClickListener onHotelClickListener) {
        this.context = context;
        this.hotelLocationList = hotelLocationList;
        this.onHotelClickListener= onHotelClickListener;
    }

    @NonNull
    @Override
    public HotelByLocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hotel_by_location, parent, false);
        return new HotelByLocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HotelByLocationAdapter.ViewHolder holder, int position) {
        HotelModel hotel = hotelLocationList.get(position);
        holder.hotelNameTxt.setText(hotel.getHotelName());
        holder.locationTxt.setText(hotel.getLocation());
        holder.startTxt.setText(String.valueOf(hotel.getStarRating()));
        Picasso.get().load(hotel.getImage()).into(holder.imageView);

        // Set click listener
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (onHotelClickListener != null) {
//                    onHotelClickListener.onHotelClick(position);
//                }
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onHotelClickListener != null) {
                    onHotelClickListener.onHotelClick(hotel);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotelLocationList.size();
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
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && onHotelClickListener != null) {
//                        onHotelClickListener.onHotelClick(hotelLocationList.get(position).getId());
//                    }
//                }
//            });
        }
    }
}
