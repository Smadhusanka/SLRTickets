package com.example.slr2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.auth.AuthResult;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class bookticket extends AppCompatActivity{


    int value = 1;
    int a=0;
    private DatabaseReference databaseReference;
    private DatabaseReference usersRef;
    private DataSnapshot dataSnapshot;

    String enterNic;
    String enterPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookticket);


        //link details activity
        Button btn3 = (Button) findViewById(R.id.button7);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(bookticket.this, details.class));
            }
        });

        //arrays for spinners
        String[] from = {"Mathara", "Kaluthara", "Colombo"};
        String[] to = {"Kurunagala", "Anuradhapura", "Jaffna"};
        String[] clas={"1st_Class","2nd_Class","3rd_Class"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, from);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, to);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clas);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner1 = findViewById(R.id.spinnerFrom);
        Spinner spinner2 = findViewById(R.id.spinnerTo);
        Spinner spinner3 = findViewById(R.id.spinnerClass);

        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);



        Intent intent = getIntent();
        enterNic = intent.getStringExtra("enterNic");
        enterPassword = intent.getStringExtra("enterPassword");





        //link bookTicket activity
        Button book = findViewById(R.id.Bticket);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bookticket.this, softticket.class);
                // Get the selected value from the spinner
                //from
                Spinner inputFrom = findViewById(R.id.spinnerFrom);
                String selectedValue = inputFrom.getSelectedItem().toString();

                //to
                Spinner inputTo = findViewById(R.id.spinnerTo);
                String selectedValue2 = inputTo.getSelectedItem().toString();

                //class
                Spinner inputClass = findViewById(R.id.spinnerClass);
                String selectedValue3 = inputClass.getSelectedItem().toString();





                //calender
                CalendarView calendarView = findViewById(R.id.calendarView);
                long selectedDateInMillis = calendarView.getDate();

                Date currentTime = Calendar.getInstance().getTime();
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                String formattedTime = timeFormat.format(currentTime);



                // Add the selected value as an extra to the Intent
                intent.putExtra("From", selectedValue);
                intent.putExtra("To", selectedValue2);
                intent.putExtra("PRINTED_VALUE", value);
                intent.putExtra("SELECTED_DATE", selectedDateInMillis);
                intent.putExtra("CURRENT_TIME", formattedTime);

                switch (selectedValue3){

                    case "1st_Class":

                        a= 500*value;
                        //intent.putExtra("selectedValue3", a);
                        break;


                    case "2nd_Class":
                        a= 250*value;
                        //intent.putExtra("selectedValue3", a);
                        break;

                    case "3rd_Class":
                        a= 150*value;
                        //intent.putExtra("selectedValue3", a);
                        break;

                    default:
                        //intent.putExtra("selectedValue3", "error");
                        break;

                }
                intent.putExtra("selectedValue3", a);


                String id = enterNic;
                String pw = enterPassword;
                usersRef = FirebaseDatabase.getInstance().getReference().child("registerData");
                usersRef.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            // Retrieve the password from the database
                            String passwordFromDB = dataSnapshot.child("password").getValue(String.class);

                            // Compare the entered password with the one from the database
                            if (pw.equals(passwordFromDB)) {
                               // Toast.makeText(bookticket.this, "shakila", Toast.LENGTH_SHORT).show();
                                String name = dataSnapshot.child("firstName").getValue(String.class);
                                String Lname = dataSnapshot.child("lastName").getValue(String.class);
                                String mobileNumber = dataSnapshot.child("mobileNumber").getValue(String.class);
                                String nic = dataSnapshot.child("nic").getValue(String.class);
                                String ref = nic + mobileNumber;
                                String mail = dataSnapshot.child("email").getValue(String.class);

                                intent.putExtra("name", name);
                                intent.putExtra("Lname", Lname);
                                intent.putExtra("mobileNumber", mobileNumber);
                                intent.putExtra("ref", ref);
                                intent.putExtra("email", mail);
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                // Start the second activity
                //startActivity(intent);



            }
        });


        //assign buttons textviews for no.seats
        TextView numberTextView = findViewById(R.id.textView10);
        Button incrementButton = findViewById(R.id.button2);
        Button decrementButton = findViewById(R.id.button3);



        //increment no.seat
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value++;
                numberTextView.setText(String.valueOf(value));

            }
        });

        //decrement no.seats
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (value > 1) {
                    value--;
                    numberTextView.setText(String.valueOf(value));

                }
            }
        });





    };



    }
