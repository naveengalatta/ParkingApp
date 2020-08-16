package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parkingapp.Model.UserOrders;
import com.example.parkingapp.Prevalent.Prevalent;
import com.example.parkingapp.ViewHolder.UserOrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNewOrder extends AppCompatActivity {

    private DatabaseReference UserOrderRef;
    private RecyclerView UserOrderRecycler;
    RecyclerView.LayoutManager UserLayoutMananger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_new_order);

        UserOrderRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("User Orders");

        UserOrderRecycler = (RecyclerView) findViewById(R.id.recycler_user_order);
        UserOrderRecycler.setHasFixedSize(true);
        UserLayoutMananger = new LinearLayoutManager(this);
        UserOrderRecycler.setLayoutManager(UserLayoutMananger);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<UserOrders> options = new FirebaseRecyclerOptions.Builder<UserOrders>()
                .setQuery(UserOrderRef,UserOrders.class).build();

        FirebaseRecyclerAdapter<UserOrders, UserOrderViewHolder> adapter = new FirebaseRecyclerAdapter<UserOrders, UserOrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserOrderViewHolder holder, int position, @NonNull final UserOrders model) {

                holder.txtBookingID.setText(model.getOrderID());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(UserNewOrder.this,UserOrderDetails.class);
                        intent.putExtra("orderID",model.getOrderID());
                        startActivity(intent);

                    }
                });


            }

            @NonNull
            @Override
            public UserOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_users_layout,parent,false);
                UserOrderViewHolder holder = new UserOrderViewHolder(view);
                return holder;

            }
        };

        UserOrderRecycler.setAdapter(adapter);
        adapter.startListening();

    }

}
