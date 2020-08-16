package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.parkingapp.Model.Orders;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserOrderDetails extends AppCompatActivity {

    private String UserOrderID = "";

    private TextView aubookingid, auvehicleno, audateofbooking, aucateogry, autotalhours, autotalamount, auparkingname, auparkingaddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_order_details);

        UserOrderID = getIntent().getStringExtra("orderID");

        aubookingid = (TextView) findViewById(R.id.auo_booking_id);
        auvehicleno = (TextView) findViewById(R.id.auo_vehicle_number);
        audateofbooking = (TextView) findViewById(R.id.auo_date_of_booking);
        aucateogry = (TextView) findViewById(R.id.auo_category);
        autotalhours = (TextView) findViewById(R.id.auo_total_hours);
        autotalamount = (TextView) findViewById(R.id.auo_total_charges);
        auparkingname = (TextView) findViewById(R.id.auo_parking_center);
        auparkingaddress = (TextView) findViewById(R.id.auo_parking_address);

        getUserOrderDetails(UserOrderID);

    }

    private void getUserOrderDetails(String UserOrderID) {

        DatabaseReference UserOrder = FirebaseDatabase.getInstance().getReference().child("Orders");

        UserOrder.child(UserOrderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Orders orders= dataSnapshot.getValue(Orders.class);

                    aubookingid.setText(orders.getOrderid());
                    aucateogry.setText(orders.getCategory());
                    audateofbooking.setText(orders.getBookingdate());
                    auparkingaddress.setText(orders.getParkingaddress());
                    auparkingname.setText(orders.getParkingname());
                    auvehicleno.setText(orders.getVehicleno());
                    autotalamount.setText(orders.getTotalamount());
                    autotalhours.setText(orders.getNoofhours());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
