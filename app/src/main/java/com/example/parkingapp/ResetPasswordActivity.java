package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.Model.Users;
import com.example.parkingapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private  String check = "";
    //private Spinner spinner;
    private TextView pageTitle, titleQuestions;
    private EditText phoneNumber, answerEditText, questionEditText;
    private Button verifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        check = getIntent().getStringExtra("check");

        pageTitle = (TextView)findViewById(R.id.page_title);
        titleQuestions = (TextView)findViewById(R.id.title_question);

        verifyButton = (Button)findViewById(R.id.verify_btn);

        phoneNumber = (EditText)findViewById(R.id.find_phone_number);
        answerEditText = (EditText)findViewById(R.id.security_answer);
        questionEditText = (EditText)findViewById(R.id.security_question);

        /*spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.questions, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);*/

    }

    @Override
    protected void onStart() {
        super.onStart();

        phoneNumber.setVisibility(View.GONE);

        if(check.equals("settings")){

            pageTitle.setText("Questions");
            titleQuestions.setText("Please answer the question");
            verifyButton.setText("Set");
            displayPreviousAnswer();

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   setAnswer();

                }
            });

        }
        else if(check.equals("login")){

            phoneNumber.setVisibility(View.VISIBLE);

            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    verifyUser();

                }
            });

        }

    }

    private void verifyUser() {

        final String phone = phoneNumber.getText().toString();
        final String question1 = questionEditText.getText().toString().toLowerCase();
        final String answer1 = answerEditText.getText().toString().toLowerCase();

        if(!phone.equals("") && !answer1.equals("") && !question1.equals("")){

            final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(phone);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){

                        String mphone = dataSnapshot.child("phone").getValue().toString();

                        if(phone.equals(mphone)){

                            if(dataSnapshot.hasChild("Security Questions")){

                                String question = dataSnapshot.child("Security Questions").child("question").getValue().toString();
                                String answer = dataSnapshot.child("Security Questions").child("answer").getValue().toString();

                                if(!question.equals(question1)){

                                    Toast.makeText(ResetPasswordActivity.this,"Selected Question is wrong",Toast.LENGTH_SHORT).show();

                                }
                                else if(!answer.equals(answer1)){

                                    Toast.makeText(ResetPasswordActivity.this,"Answer is wrong",Toast.LENGTH_SHORT).show();

                                }
                                else{

                                    AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                    builder.setTitle("New password");

                                    final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                    newPassword.setHint("New password");
                                    builder.setView(newPassword);

                                    builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            if(!newPassword.getText().toString().equals("")){

                                                ref.child("password").setValue(newPassword.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if(task.isSuccessful()){

                                                                    Toast.makeText(ResetPasswordActivity.this,"Password Changed Successfully",Toast.LENGTH_SHORT).show();

                                                                    Intent intent = new Intent(ResetPasswordActivity.this,LoginActivity.class);
                                                                    startActivity(intent);

                                                                }

                                                            }
                                                        });

                                            }

                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.cancel();

                                        }
                                    });
                                    builder.show();

                                }

                            }

                        }
                        else {

                            Toast.makeText(ResetPasswordActivity.this,"You have not set security question",Toast.LENGTH_SHORT).show();

                        }

                    }
                    else {

                        Toast.makeText(ResetPasswordActivity.this,"Phone number wrong",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{

            Toast.makeText(ResetPasswordActivity.this,"Complete the form",Toast.LENGTH_SHORT).show();

        }

    }

    private void setAnswer() {

        String question = questionEditText.getText().toString().toLowerCase();
        String answer = answerEditText.getText().toString().toLowerCase();

        if(answer.equals("")){

            Toast.makeText(ResetPasswordActivity.this,"Please answer the Questions",Toast.LENGTH_SHORT).show();

        }
        else{

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            HashMap<String, Object> userdataMap = new HashMap<>();
            userdataMap.put("question",question);
            userdataMap.put("answer",answer);

            ref.child("Security Questions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){

                        Toast.makeText(ResetPasswordActivity.this,"Security question updated successfully",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPasswordActivity.this,SettingsActivity.class);
                        startActivity(intent);


                    }

                }
            });

        }

    }

    private void displayPreviousAnswer(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Prevalent.currentOnlineUser.getPhone());

        ref.child("Security Questions").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String question = dataSnapshot.child("question").getValue().toString();
                    String answer = dataSnapshot.child("answer").getValue().toString();

                    questionEditText.setText(question);
                    answerEditText.setText(answer);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
