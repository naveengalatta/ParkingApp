package com.example.parkingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.Interface.ItemClickListner;
import com.example.parkingapp.Model.UserOrders;
import com.example.parkingapp.R;

public class UserOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtBookingID;
    public ItemClickListner listner;

    public UserOrderViewHolder(View itemView){
        super(itemView);

        txtBookingID = (TextView) itemView.findViewById(R.id.oul_booking_id);
    }

    public void setItemClickListener(ItemClickListner listener){

        this.listner= listener;

    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(),false);

    }

}
