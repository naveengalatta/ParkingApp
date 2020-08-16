package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {

    private ImageView cycle,bike;
    private ImageView autoRickshaw;
    private ImageView car,minivan;
    private ImageView lorry, largeLorry;

    private Button LogoutBtn, CheckOrdersBtn, maintainProductsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);


        LogoutBtn = (Button)findViewById(R.id.admin_logout_btn);
        CheckOrdersBtn = (Button)findViewById(R.id.check_orders_btn);
        //maintainProductsBtn = (Button)findViewById(R.id.maintain_btn);

        cycle = (ImageView)findViewById(R.id.admin_cycle);
        bike = (ImageView)findViewById(R.id.admin_bike);

        autoRickshaw = (ImageView)findViewById(R.id.admin_autoRickshaw);

        car = (ImageView)findViewById(R.id.admin_car);
        minivan = (ImageView)findViewById(R.id.admin_minivan);

        lorry = (ImageView)findViewById(R.id.admin_lorry);
        largeLorry = (ImageView)findViewById(R.id.admin_largeLorry);


        cycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","cycle");
                startActivity(intent);


            }
        });

        bike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","bike");
                startActivity(intent);


            }
        });

        autoRickshaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","autoRickshaw");
                startActivity(intent);


            }
        });

        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","car");
                startActivity(intent);


            }
        });

        minivan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","minivan");
                startActivity(intent);


            }
        });

        lorry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","lorry");
                startActivity(intent);


            }
        });

        largeLorry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminAddNewProductActivity.class);
                intent.putExtra("category","largeLorry");
                startActivity(intent);

            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });

        /*maintainProductsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminCategoryActivity.this,HomeActivity.class);
                intent.putExtra("Admin","Admin");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });*/

    }
}
