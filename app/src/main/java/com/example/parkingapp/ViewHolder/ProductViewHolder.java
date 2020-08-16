package com.example.parkingapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkingapp.Interface.ItemClickListner;
import com.example.parkingapp.Model.Products;
import com.example.parkingapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName,txtProductCategory, txtProductPrice,txtProductAddress, txtProductDescription;
    public ItemClickListner listner;

    public ProductViewHolder(@NonNull View itemView) {

        super(itemView);

        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
        txtProductAddress= (TextView) itemView.findViewById(R.id.product_address);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductCategory = (TextView) itemView.findViewById(R.id.product_category);

    }

    public void setItemClickListner(ItemClickListner listner) {

        this.listner = listner;

    }

    @Override
    public void onClick(View v) {

        listner.onClick(v, getAdapterPosition(),false);

    }
}
