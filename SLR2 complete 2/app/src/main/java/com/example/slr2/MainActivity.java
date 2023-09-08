package com.example.slr2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(MainActivity.this,loging.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}