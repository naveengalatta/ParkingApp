package com.example.parkingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.Interface.ItemClickListner;
import com.example.parkingapp.R;


public class AdminOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtBookingID;
    public ItemClickListner listner;

    public AdminOrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtBookingID = (TextView) itemView.findViewById(R.id.oal_booking_id);

    }

    public void setItemClickListener(ItemClickListner listener){

        this.listner= listener;

    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(),false);

    }
}
