package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, PName, Address, Price, Description, saveCurrentDate, saveCurrentTime;
    private Button AddNewProductButton;
    private EditText InputProductDescription,InputProductPrice,InputproductName,InputproductAddress;
    private TextView closeNewProduct, updateNewProduct;
    private String productRandomKey;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadindBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getStringExtra("category");

        Toast.makeText(this,CategoryName,Toast.LENGTH_SHORT).show();

        closeNewProduct = (TextView)findViewById(R.id.close_new_product_btn);
        updateNewProduct = (TextView)findViewById(R.id.update_new_product_btn);
        AddNewProductButton = (Button)findViewById(R.id.add_new_product);
        InputproductName = (EditText)findViewById(R.id.product_name);
        InputproductAddress = (EditText)findViewById(R.id.product_address);
        InputProductDescription = (EditText)findViewById(R.id.product_description);
        InputProductPrice = (EditText)findViewById(R.id.product_price);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadindBar = new ProgressDialog(this);

        productInfoDisplayButton(InputproductName,InputproductAddress,InputProductPrice,InputProductDescription);

        closeNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });

        updateNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProductInfo();

            }
        });

    }

    private void updateProductInfo() {

        final DatabaseReference AdminProductUpdate = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineUser.getPhone());

        AdminProductUpdate.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.child(CategoryName).exists()){

                        //updateNewProduct.setVisibility(View.GONE);
                        //updateNewProduct.setVisibility(View.INVISIBLE);

                        String productID =dataSnapshot.child(CategoryName).getValue().toString();

                        DatabaseReference updateProduct = FirebaseDatabase.getInstance().getReference().child("Products");

                        HashMap<String, Object> productUpdateMap = new HashMap<>();
                        productUpdateMap.put("pname", InputproductName.getText().toString());
                        productUpdateMap.put("address", InputproductAddress.getText().toString());
                        productUpdateMap.put("price", InputProductPrice.getText().toString());
                        productUpdateMap.put("description", InputProductDescription.getText().toString());
                        if(TextUtils.isEmpty(InputproductName.getText().toString())){
                            Toast.makeText(AdminAddNewProductActivity.this,"Please enter the name of the Parking Stand",Toast.LENGTH_SHORT).show();

                        }
                        else if(TextUtils.isEmpty(InputproductAddress.getText().toString())){

                            Toast.makeText(AdminAddNewProductActivity.this,"Please enter the Address of the Parking Stand",Toast.LENGTH_SHORT).show();

                        }
                        else if(TextUtils.isEmpty(InputProductPrice.getText().toString())){

                            Toast.makeText(AdminAddNewProductActivity.this,"Please enter the price",Toast.LENGTH_SHORT).show();

                        }
                        else if(TextUtils.isEmpty(InputProductDescription.getText().toString())){

                            Toast.makeText(AdminAddNewProductActivity.this,"Please write any conditions or rules, if not write no conditions",Toast.LENGTH_SHORT).show();

                        }
                        else {

                            updateProduct.child(productID).updateChildren(productUpdateMap);

                            //startActivity(new Intent(SettingsActivity.this,HomeActivity.class));

                            Toast.makeText(AdminAddNewProductActivity.this, "Product Infomation Updated", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void productInfoDisplayButton(final  EditText inputproductName,final EditText inputproductAddress,final EditText inputProductPrice,final EditText inputProductDescription) {

        DatabaseReference AdminProduct = FirebaseDatabase.getInstance().getReference().child("Admins").child(Prevalent.currentOnlineUser.getPhone());

        AdminProduct.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    if(dataSnapshot.child(CategoryName).exists()){

                        String productID =dataSnapshot.child(CategoryName).getValue().toString();

                        DatabaseReference ProductDetails = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

                        ProductDetails.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){

                                    AddNewProductButton.setVisibility(View.GONE);

                                    String name =dataSnapshot.child("pname").getValue().toString();
                                    String address =dataSnapshot.child("address").getValue().toString();
                                    String price =dataSnapshot.child("price").getValue().toString();
                                    String description =dataSnapshot.child("description").getValue().toString();

                                    InputproductName.setText(name);
                                    InputproductAddress.setText(address);
                                    InputProductPrice.setText(price);
                                    InputProductDescription.setText(description);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else {

                        updateNewProduct.setVisibility(View.GONE);

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ValidateProductData() {

        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        PName = InputproductName.getText().toString();
        Address = InputproductAddress.getText().toString();

        if(TextUtils.isEmpty(PName)){

            Toast.makeText(this,"Please enter the name of the Parking Stand",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Address)){

            Toast.makeText(this,"Please enter the Address of the Parking Stand",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Price)){

            Toast.makeText(this,"Please enter the price",Toast.LENGTH_SHORT).show();

        }
        else if(TextUtils.isEmpty(Description)){

            Toast.makeText(this,"Please write any conditions or rules, if not write no conditions",Toast.LENGTH_LONG).show();

        }
        else {

            StoreProductInformation();

        }

    }

    private void StoreProductInformation(){

        loadindBar.setTitle("Uploading Details");
        loadindBar.setMessage("Please wait, while we are uploading your parking details");
        loadindBar.setCanceledOnTouchOutside(false);
        loadindBar.show();


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy ");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        SaveProductInfoToDatabase();

    }

    private void SaveProductInfoToDatabase(){

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid",productRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",Description);
        productMap.put("category",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname", PName);
        productMap.put("address", Address);
        productMap.put("adminPhone",Prevalent.currentOnlineUser.getPhone());

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admins")
                                    .child(Prevalent.currentOnlineUser.getPhone());

                            HashMap<String, Object> adminProduct = new HashMap<>();
                            adminProduct.put(CategoryName,productRandomKey);

                            ref.updateChildren(adminProduct).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){

                                        loadindBar.dismiss();
                                        Toast.makeText(AdminAddNewProductActivity.this,"Details Uploaded",Toast.LENGTH_SHORT).show();

                                        Intent intent55 =new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                                        startActivity(intent55);


                                    }

                                }
                            });

                            /*loadindBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this,"Details Uploaded",Toast.LENGTH_SHORT).show();

                            Intent intent55 =new Intent(AdminAddNewProductActivity.this,AdminCategoryActivity.class);
                            startActivity(intent55);*/


                        }
                        else{

                            loadindBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this," Failed to upload the Details due to "+message,Toast.LENGTH_SHORT).show();

                        }

                    }
                });


    }

}
