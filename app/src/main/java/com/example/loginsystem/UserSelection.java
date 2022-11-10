package com.example.loginsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);


        Button button1 = (Button) findViewById(R.id.customer);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Register.class);
                view.getContext().startActivity(intent);}
        });

        Button button2 = (Button) findViewById(R.id.dealer);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterDealer.class);
                view.getContext().startActivity(intent);}
        });

        Button button3 = (Button) findViewById(R.id.admin);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RegisterAdmin.class);
                view.getContext().startActivity(intent);}
        });


    }
}