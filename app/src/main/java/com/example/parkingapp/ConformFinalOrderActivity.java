package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Model.Products;
import com.example.parkingapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EventListener;
import java.util.HashMap;

public class ConformFinalOrderActivity extends AppCompatActivity {

    private String productID="", totalAmount = "", vehicleNumber = "", bookingDate = "", noOfHours="";

    private String orderRandomKey;

    private TextView crfUserName, crfUserPhoneNumber, crfUserVehicleNumber, crfUserBookingDate,
                     crfUserNoOfHours, crfUserTotalAmount, crfPrdName, crfPrdPrice, crfPrdAddress,crfPrdCategory;

    private Button confirmOrderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform_final_order);

        productID = getIntent().getStringExtra("pid");
        totalAmount = getIntent().getExtras().getString("TotalPrice");
        vehicleNumber = getIntent().getExtras().getString("VehicleNumber");
        bookingDate = getIntent().getExtras().getString("BookingDate");
        noOfHours = getIntent().getExtras().getString("NoOfHours");

        crfUserName = (TextView)findViewById(R.id.crf_user_name);
        crfUserPhoneNumber = (TextView)findViewById(R.id.crf_user_phone_number);
        crfUserVehicleNumber = (TextView)findViewById(R.id.crf_user_vehicle_number);
        crfUserBookingDate = (TextView)findViewById(R.id.crf_user_booking_date);
        crfUserNoOfHours = (TextView)findViewById(R.id.crf_user_no_of_hours);
        crfUserTotalAmount = (TextView)findViewById(R.id.crf_user_total_amount);
        crfPrdAddress = (TextView)findViewById(R.id.crf_product_address);
        crfPrdName = (TextView)findViewById(R.id.crf_product_name);
        crfPrdPrice = (TextView)findViewById(R.id.crf_product_price);
        crfPrdCategory = (TextView)findViewById(R.id.crf_product_category);

        confirmOrderBtn = (Button)findViewById(R.id.confirm_final_order_btn);

        crfUserTotalAmount.setText(totalAmount);
        crfUserNoOfHours.setText(noOfHours);
        crfUserBookingDate.setText(bookingDate);
        crfUserVehicleNumber.setText(vehicleNumber);

        userInfoDisplay( crfUserName, crfUserPhoneNumber);

        getProductDetails(productID);


        confirmOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmOrder();

            }
        });



    }

    private void ConfirmOrder() {

        final String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy ");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:MM:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        orderRandomKey = saveCurrentDate +  saveCurrentTime;


        final DatabaseReference ordersRef =FirebaseDatabase.getInstance().getReference().child("Orders").child(orderRandomKey);

        HashMap<String, Object> ordersMap = new HashMap<>();

        ordersMap.put("orderid",orderRandomKey);
        ordersMap.put("totalamount",crfUserTotalAmount.getText().toString());
        ordersMap.put("username",crfUserName.getText().toString());
        ordersMap.put("userphonenumber",crfUserPhoneNumber.getText().toString());
        ordersMap.put("vehicleno",crfUserVehicleNumber.getText().toString());
        ordersMap.put("bookingdate",crfUserBookingDate.getText().toString());
        ordersMap.put("noofhours",crfUserNoOfHours.getText().toString());
        ordersMap.put("parkingname",crfPrdName.getText().toString());
        ordersMap.put("category",crfPrdCategory.getText().toString());
        ordersMap.put("parkingaddress",crfPrdAddress.getText().toString());


        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    DatabaseReference userOrders = FirebaseDatabase.getInstance().getReference().child("Users")
                            .child(Prevalent.currentOnlineUser.getPhone());

                    HashMap<String, Object> userorder = new HashMap<>();
                    userorder.put("orderID",orderRandomKey);

                    userOrders.child("User Orders").child(orderRandomKey).updateChildren(userorder).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                DatabaseReference getAdminPhone = FirebaseDatabase.getInstance().getReference()
                                        .child("Products").child(productID);

                                getAdminPhone.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){

                                            String adminPhone = dataSnapshot.child("adminPhone").getValue().toString();

                                            DatabaseReference adminOrders = FirebaseDatabase.getInstance().getReference()
                                                    .child("Admins").child(adminPhone);

                                            HashMap<String, Object> adminorder = new HashMap<>();
                                            adminorder.put("orderID",orderRandomKey);

                                            adminOrders.child("Admin Orders").child(orderRandomKey).updateChildren(adminorder)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {

                                                            Toast.makeText(ConformFinalOrderActivity.this,"Your Booking is Successful",Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(ConformFinalOrderActivity.this,HomeActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    });



                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                        }
                    });



                    /*Toast.makeText(ConformFinalOrderActivity.this,"Your Booking is Successful",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ConformFinalOrderActivity.this,HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                }

            }
        });


    }

    private void userInfoDisplay(final TextView crfUserName, final TextView crfUserPhoneNumber) {

        DatabaseReference UserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());

        UserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String name =dataSnapshot.child("name").getValue().toString();
                    String phone =dataSnapshot.child("phone").getValue().toString();

                    crfUserName.setText(name);
                    crfUserPhoneNumber.setText(phone);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getProductDetails(final String productID) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Products products = dataSnapshot.getValue(Products.class);

                    crfPrdName.setText(products.getPname());
                    crfPrdCategory.setText(products.getCategory());
                    crfPrdPrice.setText(products.getPrice());
                    crfPrdAddress.setText(products.getAddress());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

    }

}


