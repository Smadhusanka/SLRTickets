package com.example.slr2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
//import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class softticket extends AppCompatActivity {

    //String dbFrom;
   //String dbTo;
    //int dbPrintValue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_softticket);



       ImageButton imageButton = (ImageButton) findViewById(R.id.imageBackBtn1);
       imageButton.setOnClickListener(new View.OnClickListener() {

           @Override
           public void onClick(View v) {
               startActivity(new Intent(softticket.this, bookticket.class));

           }
       });
       Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   navigateToLogin();
               }
        });





           //get values booking activity
               Intent intent = getIntent();
               String selectedValue = intent.getStringExtra("From");
               String selectedValue2 = intent.getStringExtra("To");
               int printedValue = intent.getIntExtra("PRINTED_VALUE", 1);

               long selectedDateInMillis = intent.getLongExtra("SELECTED_DATE", 0);
               Date selectedDate = new Date(selectedDateInMillis);
               SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
               String formattedSelectedDate = dateFormat.format(selectedDate);

               String currentTime = intent.getStringExtra("CURRENT_TIME");

               int selectedValue3 = intent.getIntExtra("selectedValue3", 0);

               String name = intent.getStringExtra("name");
               String Lname = intent.getStringExtra("Lname");
               String mobileNumber = intent.getStringExtra("mobileNumber");
               String ref = intent.getStringExtra("ref");
               String email = intent.getStringExtra("email");


               //assing values softticket textviews
               TextView textView = findViewById(R.id.textView33);
               TextView textView2 = findViewById(R.id.textView34);
               TextView printedValueTextView = findViewById(R.id.textView37);
               TextView dateText = findViewById(R.id.textView35);
               TextView Time = findViewById(R.id.textView36);
               TextView price = findViewById(R.id.textView38);
               TextView nameTxt = findViewById(R.id.textView30);
               TextView mobileNumberTxt = findViewById(R.id.textView31);
               TextView refTxt = findViewById(R.id.textView32);


               //pritn the values
               textView.setText(selectedValue);
               textView2.setText(selectedValue2);
               printedValueTextView.setText(String.valueOf(printedValue));
               dateText.setText(formattedSelectedDate);
               Time.setText(currentTime);
               price.setText("Rs." + (String.valueOf(selectedValue3)));
               nameTxt.setText(name + " " + Lname);
               mobileNumberTxt.setText(mobileNumber);
               refTxt.setText(ref);

               //dbFrom = selectedValue;
               //dbTo = selectedValue2;
               //dbPrintValue = printedValue;

               Button book = (Button) findViewById(R.id.button4);
               book.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                       DatabaseReference userInputRef = databaseRef.child("bookTicketDetails");
                       String uniqueKey = userInputRef.push().getKey();


                       userInputRef.child(ref).child("from").setValue(selectedValue);
                       userInputRef.child(ref).child("to").setValue(selectedValue2);
                       userInputRef.child(ref).child("name").setValue(name);
                       userInputRef.child(ref).child("Lastname").setValue(Lname);
                       userInputRef.child(ref).child("mobileNumber").setValue(mobileNumber);
                       userInputRef.child(ref).child("ref").setValue(ref);
                       userInputRef.child(ref).child("NumerOfSeats").setValue(printedValue);
                       userInputRef.child(ref).child("price").setValue(selectedValue3);

                       Toast.makeText(softticket.this, "success!", Toast.LENGTH_SHORT).show();


                       String emailAddress = email;
                       String subject = "Booking Details";
                       String body = "Name : " + name + " " + Lname + "\n" + "Phone number : " + mobileNumber + "\n" + "Reference Number : "
                               + ref + "\n" + "From : " + selectedValue + "\n" + "To : " + selectedValue2 + "\n" + "Number od Seats : " +
                               printedValue + "\n" + "Price : " + selectedValue3;

                       Intent emailIntent = new Intent(Intent.ACTION_SEND);
                       emailIntent.setType("message/rfc822");
                       emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                       emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                       emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                       if (emailIntent.resolveActivity(getPackageManager()) != null) {
                           startActivity(emailIntent);
                       } else {
                           Toast.makeText(softticket.this, "No email app found", Toast.LENGTH_SHORT).show();
                       }

                   }
               });

           }


    private void navigateToLogin() {
        Intent intent = new Intent(this, loging.class);
        startActivity(intent);
        finish(); // Optional: Close the current activity to prevent going back to it
    }
}