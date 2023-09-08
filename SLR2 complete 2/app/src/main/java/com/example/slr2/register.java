package com.example.slr2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class register<User> extends AppCompatActivity {

   

    String firstName;
    String lastName;
    String nic;
    String email;
    String mobileNumber;
    String password;

    String reenterPassword;
    String mail;
    String otp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageBackBtn2);
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, loging.class));
            }
        });

        // Step 1: Retrieve references to EditTexts and the save button
        EditText firstNameEditText = findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = findViewById(R.id.lastNameEditText);
        EditText nicEditText = findViewById(R.id.nicEditText);
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText mobileNumberEditText = findViewById(R.id.mobileNumberEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText reenterPasswordEditText = findViewById(R.id.reenterPasswordEditText);
        Button register = findViewById(R.id.register);
        Button otpButton = findViewById(R.id.getOtp);
        EditText userEnterOtp = findViewById(R.id.txtOtp);



        Random random = new Random();
        int otpNumber = random.nextInt(100000);

        otpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail = emailEditText.getText().toString().trim();

                String emailAddress = mail;
                String subject = "OTP Code";
                String body = "OTP Code : " + otpNumber;

                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, body);

                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(register.this, "No email app found", Toast.LENGTH_SHORT).show();
                }

            }
        });

       // Step 2: Set an OnClickListener on the save button
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Step 3: Inside onClick method, add the code for input validation and saving data to Firebase
                 firstName = firstNameEditText.getText().toString().trim();
                 lastName = lastNameEditText.getText().toString().trim();
                 nic = nicEditText.getText().toString().trim();
                 email = emailEditText.getText().toString().trim();
                 mobileNumber = mobileNumberEditText.getText().toString().trim();
                 password = passwordEditText.getText().toString().trim();
                 reenterPassword = reenterPasswordEditText.getText().toString().trim();

                // Validate inputs
                if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(nic) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(mobileNumber) ||
                        TextUtils.isEmpty(password) || TextUtils.isEmpty(reenterPassword)) {
                    // Display an error message for empty fields
                    Toast.makeText(getApplicationContext(), "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String pattern = "^[a-zA-Z]+$";
                String pattern2 = "^[0-9V]+$";
                if (!firstName.matches(pattern)) {
                    // Username is valid (contains only alphabetic characters)
                    Toast.makeText(getApplicationContext(), "invalid name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!lastName.matches(pattern)) {
                    // Username is valid (contains only alphabetic characters)
                    Toast.makeText(getApplicationContext(), "invalid name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!nic.matches(pattern2) || (nic.length() < 8)) {
                    // Username is valid (contains only alphabetic characters)
                    Toast.makeText(getApplicationContext(), "invalid NIC or length error", Toast.LENGTH_SHORT).show();
                    return;
                }


                // Validate email address
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Display an error message for invalid email
                    Toast.makeText(getApplicationContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mobileNumber.length() < 10){
                    Toast.makeText(getApplicationContext(), "invalid Mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    otp = userEnterOtp.getText().toString();
                    int userInput = Integer.parseInt(otp);
                    int sendOtp = otpNumber;

                    if (userInput == sendOtp) {
                        Toast.makeText(register.this, "OTP verified", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(register.this, "invalid OTP", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(register.this, "Invalid Format", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate mobile number or perform verification process
                // (You can use a third-party SMS verification service or Firebase Authentication for this step)

                // Check if password and reenter password match
                if (!password.equals(reenterPassword)) {
                    // Display an error message for password mismatch
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }



                // All inputs are valid, proceed to save data to Firebase
                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference userInputRef = databaseRef.child("registerData");
                String uniqueKey = userInputRef.push().getKey();



                userInputRef.child(nic).child("firstName").setValue(firstName);
                userInputRef.child(nic).child("lastName").setValue(lastName);
                userInputRef.child(nic).child("email").setValue(email);


                userInputRef.child(nic).child("mobileNumber").setValue(mobileNumber);
                userInputRef.child(nic).child("password").setValue(password);
                userInputRef.child(nic).child("nic").setValue(nic);

                Toast.makeText(getApplicationContext(), "Registation Success! now you can logging", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(register.this, loging.class));

            }
        });
    }
}