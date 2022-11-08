package com.example.loginsystem;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends AppCompatActivity {


    public static final String TAG = "TAG";
    EditText mFullName,mEmail,mPassword,mPhone;
    Button mRegisterBtn;
    TextView mloginBtn;
    ProgressBar progressBar;


    FirebaseFirestore fstore;
    String userID;
    FirebaseAuth fAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register);


        mFullName = findViewById(R.id.fullname);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);
        mPhone = findViewById(R.id.phone);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mloginBtn = findViewById(R.id.createtext);
        progressBar = findViewById(R.id.progressBar);


        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();


        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),UserSelection.class));
            finish();
        }




        mloginBtn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),Login.class)));


        mRegisterBtn.setOnClickListener(view -> {
            final String email = mEmail.getText().toString().trim();
            String password = mPassword.getText().toString().trim();
            final String fullName = mFullName.getText().toString();
            final String phone = mPhone.getText().toString();


            if(TextUtils.isEmpty(email)){
                mEmail.setError("Email is required");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mPassword.setError("Password is Required");
                return;
            }

            if(password.length() < 6){
                mPassword.setError("Password must be >= 6 characters");
                return;
            }
            progressBar.setVisibility(View.VISIBLE);


            fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                FirebaseUser fuser = fAuth.getCurrentUser();
                fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"Onfailure: Email Not Sent " + e.getMessage());
                    }
                });


                Toast.makeText(getApplicationContext(), "User Created", Toast.LENGTH_SHORT).show();
                userID = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fstore.collection("user").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("fName",fullName);
                user.put("email",email);
                user.put("phone",phone);
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"OnSuccess: user profile is created for " + userID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"OnFailure: " + e.toString());
                    }
                });

                startActivity(new Intent(getApplicationContext(),Dashboard.class));


            }
                else {
                    Toast.makeText(Register.this, "Error + task.getException()).getMessage()" , Toast.LENGTH_SHORT);
                progressBar.setVisibility(View.GONE);
            }
        });



    });
}}