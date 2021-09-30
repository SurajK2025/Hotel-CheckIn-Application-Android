package com.sklabs.hotelcheckin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Context myContext;
    private List<Bookings> mBookings;

    public Adapter(Context myContext, List<Bookings> mBookings){
        this.myContext = myContext;
        this.mBookings = mBookings;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.layout_bookings, parent, false);
        return new Adapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        Bookings bookings = mBookings.get(position);
        holder.show_booking_time.setText("Time: " + bookings.getTime());
        holder.show_booking_date.setText("Date: " + bookings.getDate());
        holder.show_booking_room_no.setText("Room Number: " + bookings.getRoomNumber().toString());
        holder.show_booking_id.setText("BookingId: " + bookings.getBookingID().toString());
    }

    @Override
    public int getItemCount() {
        return mBookings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_booking_id, show_booking_date, show_booking_time, show_booking_room_no;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_booking_id = itemView.findViewById(R.id.show_booking_id);
            show_booking_date = itemView.findViewById(R.id.show_booking_date);
            show_booking_time = itemView.findViewById(R.id.show_booking_time);
            show_booking_room_no = itemView.findViewById(R.id.show_booking_room_no);

        }
    }
}
