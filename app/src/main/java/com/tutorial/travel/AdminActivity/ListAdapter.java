package com.tutorial.travel.AdminActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorial.travel.AdminActivity.Details_of_Reservation;
import com.tutorial.travel.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> ID;
    ArrayList<String> ReservationDate;
    ArrayList<String> RoomType;
    ArrayList<String> NumberOfRooms;
    ArrayList<String> CheckInDate;
    ArrayList<String> CheckOutDate;
    ArrayList<String> FirstName;
    ArrayList<String> LastName;
    ArrayList<String> NumberOfAdults;
    ArrayList<String> NumberOfChildren;
    ArrayList<String> TotalPrice;

    public ListAdapter(Context context1, ArrayList<String> ID, ArrayList<String> reservationDate, ArrayList<String> roomType, ArrayList<String> numberOfRooms, ArrayList<String> checkInDate, ArrayList<String> checkOutDate, ArrayList<String> firstName, ArrayList<String> lastName, ArrayList<String> numberOfAdults, ArrayList<String> numberOfChildren, ArrayList<String> totalPrice) {
        this.context = context1;
        this.ID = ID;
        ReservationDate = reservationDate;
        RoomType = roomType;
        NumberOfRooms = numberOfRooms;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        FirstName = firstName;
        LastName = lastName;
        NumberOfAdults = numberOfAdults;
        NumberOfChildren = numberOfChildren;
        TotalPrice = totalPrice;
    }
    public int getCount() {
        // TODO Auto-generated method stub
        return ID.size();
    }
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    public View getView(final int position, View child, ViewGroup parent){
        Holder holder;
        LayoutInflater layoutInflater;

        if(child == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            child = layoutInflater.inflate(R.layout.items,null);

            holder = new Holder();

            holder.ID_TextView = (TextView) child.findViewById(R.id.textViewID);
            holder.ReservationDate_TextView = (TextView) child.findViewById(R.id.textViewReservationDate);
            holder.RoomType_TextView = (TextView) child.findViewById(R.id.textViewRoomType);
            holder.NumberOfRooms_TexView = (TextView) child.findViewById(R.id.textViewNumberOfRoom);
            holder.CheckInDate_TextView = (TextView) child.findViewById(R.id.textViewCheckInData);
            holder.CheckOutDate_TextView = (TextView) child.findViewById(R.id.textViewCheckOutData);
            holder.View_Button = (Button) child.findViewById(R.id.buttonView);

            child.setTag(holder);
        }else {
            holder = (Holder)child.getTag();
        }
        holder.ID_TextView.setText(ID.get(position));
        holder.ReservationDate_TextView.setText(ReservationDate.get(position));
        holder.RoomType_TextView.setText(RoomType.get(position));
        holder.NumberOfRooms_TexView.setText(NumberOfRooms.get(position));
        holder.CheckInDate_TextView.setText(CheckInDate.get(position));
        holder.CheckOutDate_TextView.setText(CheckOutDate.get(position));

        holder.View_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("View button clicked","======");
                Toast.makeText(context, "View button Clicked", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, Details_of_Reservation.class);
                intent.putExtra("id", ID.get(position));
                intent.putExtra("roomType", RoomType.get(position));
                intent.putExtra("numberOfRooms", NumberOfRooms.get(position));
                intent.putExtra("checkInDate", CheckInDate.get(position));
                intent.putExtra("checkOutDate", CheckOutDate.get(position));

                intent.putExtra("firstName", FirstName.get(position));
                intent.putExtra("lastName", LastName.get(position));
                intent.putExtra("numberOfAdults", NumberOfAdults.get(position));
                intent.putExtra("numberOfChildren", NumberOfChildren.get(position));
                intent.putExtra("totalPrice", TotalPrice.get(position));

                context.startActivity(intent);
            }
        });
    return child;
    }
    public class Holder {

        TextView ID_TextView;
        TextView ReservationDate_TextView;
        TextView RoomType_TextView;
        TextView NumberOfRooms_TexView;
        TextView CheckInDate_TextView;
        TextView CheckOutDate_TextView;

        Button View_Button;
    }
}
