package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.parkingapp.Model.Orders;
import com.example.parkingapp.Model.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class AdminOrderDetails extends AppCompatActivity {

    private String adminOrderID = "";

    private TextView aobookingid, aovehicleno, aodateofbooking, aocateogry, aototalhours, aototalamount, aousername, aophonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order_details);

        adminOrderID = getIntent().getStringExtra("orderID");

        aobookingid = (TextView) findViewById(R.id.apo_booking_id);
        aovehicleno = (TextView) findViewById(R.id.apo_vehicle_number);
        aodateofbooking = (TextView) findViewById(R.id.apo_date_of_booking);
        aocateogry = (TextView) findViewById(R.id.apo_category);
        aototalhours = (TextView) findViewById(R.id.apo_total_hours);
        aototalamount = (TextView) findViewById(R.id.apo_total_charges);
        aousername = (TextView) findViewById(R.id.apo_user_name);
        aophonenumber = (TextView) findViewById(R.id.apo_user_phone_number);

        getAdminOrderDetails(adminOrderID);


    }

    private void getAdminOrderDetails(final String adminOrderID) {

        DatabaseReference AdminOrder = FirebaseDatabase.getInstance().getReference().child("Orders");

        AdminOrder.child(adminOrderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Orders orders= dataSnapshot.getValue(Orders.class);

                    aobookingid.setText(orders.getOrderid());
                    aocateogry.setText(orders.getCategory());
                    aodateofbooking.setText(orders.getBookingdate());
                    aophonenumber.setText(orders.getUserphonenumber());
                    aousername.setText(orders.getUsername());
                    aovehicleno.setText(orders.getVehicleno());
                    aototalamount.setText(orders.getTotalamount());
                    aototalhours.setText(orders.getNoofhours());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
