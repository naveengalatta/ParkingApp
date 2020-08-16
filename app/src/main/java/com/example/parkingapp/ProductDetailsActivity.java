package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Model.Products;
import com.example.parkingapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private TextView productName, productCategory, productPrice, productAddress;
    private String productID = "";
    private Button addToCartButton;

    public EditText vehicleNumberEditText, bookingDateEditText, noofhours;
    private Button confirmOrderBtn;

    TextView amtTotal,TotalPriceTextView;

    String strAmtTotal, strVehicleNumber, strNoofhours, strBookingDate;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productID = getIntent().getStringExtra("pid");

        addToCartButton = (Button)findViewById(R.id.pd_add_to_cart_btn);

        productName = (TextView)findViewById(R.id.product_name_details);
        productCategory = (TextView)findViewById(R.id.product_category_details);
        productPrice = (TextView)findViewById(R.id.product_price_details);
        productAddress = (TextView)findViewById(R.id.product_address_details);

        confirmOrderBtn = (Button)findViewById(R.id.confirm_final_order_btn);
        vehicleNumberEditText = (EditText)findViewById(R.id.vehicle_number);
        bookingDateEditText = (EditText)findViewById(R.id.booking_date);
        noofhours = (EditText)findViewById(R.id.no_of_hours);

        amtTotal = (TextView)findViewById(R.id.total_price);
        TotalPriceTextView = (TextView)findViewById(R.id.total_price_name_apd);

        TextWatcher textWatcher =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!noofhours.getText().toString().equals("")){

                    int temp1 = Integer.parseInt(noofhours.getText().toString());
                    int temp2 = Integer.parseInt(productPrice.getText().toString());
                    amtTotal.setText(String.valueOf(temp1 * temp2));
                    TotalPriceTextView.setText("Total Parking Charges -");

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        noofhours.addTextChangedListener(textWatcher);


        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(vehicleNumberEditText.getText().toString())){

                    Toast.makeText(ProductDetailsActivity.this,"Enter vehicle number",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(bookingDateEditText.getText().toString())){

                    Toast.makeText(ProductDetailsActivity.this,"Enter Booking date",Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(noofhours.getText().toString())){

                    Toast.makeText(ProductDetailsActivity.this,"Enter duration of parking",Toast.LENGTH_SHORT).show();

                }
                else{

                    Intent intent = new Intent(ProductDetailsActivity.this,ConformFinalOrderActivity.class);
                    intent.putExtra("pid",productID);

                    strAmtTotal=amtTotal.getText().toString();
                    intent.putExtra("TotalPrice",strAmtTotal);

                    strVehicleNumber=vehicleNumberEditText.getText().toString();
                    intent.putExtra("VehicleNumber",strVehicleNumber);

                    strBookingDate=bookingDateEditText.getText().toString();
                    intent.putExtra("BookingDate",strBookingDate);

                    strNoofhours=noofhours.getText().toString();
                    intent.putExtra("NoOfHours",strNoofhours);
                    /*intent.putExtra("TotalPrice",String.valueOf(amtTotal));
                    intent.putExtra("BookingDate",String.valueOf(bookingDateEditText));
                    intent.putExtra("VehicleNumber",String.valueOf(vehicleNumberEditText));
                    intent.putExtra("NoOfhours",String.valueOf(noofhours));*/
                    startActivity(intent);
                    finish();
                    //ConfirmOrder();

                }

            }
        });

    }

    /*private void ConfirmOrder() {

        final  String saveCurrentDate, saveCurrentTime;

        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount",amtTotal);
        ordersMap.put("pid",productID);
        ordersMap.put("name",)

    }*/

   /* private void addToCartList() {

        String saveCurrentTime, saveCurrentDate;


        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", productID);
        cartMap.put("price", productID);
        cartMap.put("date", productID);
        cartMap.put("time", productID);

    }*/

    private void getProductDetails(final String productID) {

        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Products products = dataSnapshot.getValue(Products.class);

                    productName.setText(products.getPname());
                    productCategory.setText("Vehicle Type - "+products.getCategory());
                    productPrice.setText(products.getPrice());
                    productAddress.setText("Parking Address - "+products.getAddress());


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

    }


}
