package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkingapp.Model.AdminOrders;
import com.example.parkingapp.Model.Orders;
import com.example.parkingapp.Model.Products;
import com.example.parkingapp.Prevalent.Prevalent;
import com.example.parkingapp.ViewHolder.AdminOrderViewHolder;
import com.example.parkingapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {

    private DatabaseReference AdminOrderRef;
    private RecyclerView AdminOrderRecycler;
    RecyclerView.LayoutManager AdminLayoutMananger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_orders);

        AdminOrderRef = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineUser.getPhone()).child("Admin Orders");

        AdminOrderRecycler = (RecyclerView) findViewById(R.id.recycler_admin_order);
        AdminOrderRecycler.setHasFixedSize(true);
        AdminLayoutMananger = new LinearLayoutManager(this);
        AdminOrderRecycler.setLayoutManager(AdminLayoutMananger);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions.Builder<AdminOrders>()
                .setQuery(AdminOrderRef,AdminOrders.class).build();

        FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrderViewHolder holder, int position, @NonNull final AdminOrders model) {

                holder.txtBookingID.setText(model.getOrderID());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(AdminNewOrdersActivity.this,AdminOrderDetails.class);
                        intent.putExtra("orderID",model.getOrderID());
                        startActivity(intent);

                    }
                });

            }

            @NonNull
            @Override
            public AdminOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_admin_layout,parent,false);
                AdminOrderViewHolder holder = new AdminOrderViewHolder(view);
                return holder;

            }
        };

        AdminOrderRecycler.setAdapter(adapter);
        adapter.startListening();

    }

}
