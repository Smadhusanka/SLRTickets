package com.example.slr2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.FirebaseApp;

public class loging extends AppCompatActivity {

    private EditText nicEditText, passwordEditText;
    private Button loginButton;

    private DatabaseReference usersRef;
    private CheckBox savePasswordCheckbox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);


        Button btn2 = (Button) findViewById(R.id.button5);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loging.this, register.class));
            }


        });


        nicEditText = findViewById(R.id.editTextText);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.button);

        // Initialize the Firebase Realtime Database reference
        usersRef = FirebaseDatabase.getInstance().getReference().child("registerData");

        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String enteredNic = nicEditText.getText().toString().trim();
                final String enteredPassword = passwordEditText.getText().toString().trim();


                if(enteredNic.isEmpty() || enteredPassword.isEmpty()){

                    Toast.makeText(loging.this, "enter NIC and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Query the database for the entered NIC
                usersRef.child(enteredNic).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve the password from the database
                            String passwordFromDB = dataSnapshot.child("password").getValue(String.class);

                            // Compare the entered password with the one from the database
                            if (enteredPassword.equals(passwordFromDB)) {
                                // Credentials are correct, proceed to another activity
                                Toast.makeText(loging.this, "Login successful", Toast.LENGTH_SHORT).show();


                                // TODO: Start the new activity here
                                //startActivity(new Intent(loging.this, bookticket.class));
                                Intent intent = new Intent(loging.this, bookticket.class);
                                intent.putExtra("enterNic", enteredNic);
                                intent.putExtra("enterPassword", enteredPassword);
                                startActivity(intent);
                            } else {
                                // Invalid password
                                Toast.makeText(loging.this, "Invalid password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Invalid NIC
                            Toast.makeText(loging.this, "Invalid NIC", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // An error occurred while reading the data
                        Toast.makeText(loging.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }



                });
            }
        });

    }



}

