package com.example.loginsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAdmin extends AppCompatActivity {

    EditText mEmail,mpassword;
    Button mLoginBtn;
    TextView mCreateBtn;
    ProgressBar progressBar;


    FirebaseAuth fAuth;



    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        mEmail = findViewById(R.id.email);
        mpassword = findViewById(R.id.passwordD);
        progressBar = findViewById(R.id.progressBarD);
        mLoginBtn = findViewById(R.id.loginBtnD);
        mCreateBtn = findViewById(R.id.createtextD);


        fAuth=FirebaseAuth.getInstance();



        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),RegisterDealer.class));

            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password))
                {
                    mpassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6)
                {
                    mpassword.setError("Password must be more than or equal to 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(), "Log In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        }

                        else
                        {
                            Toast.makeText(getApplicationContext(), "error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}