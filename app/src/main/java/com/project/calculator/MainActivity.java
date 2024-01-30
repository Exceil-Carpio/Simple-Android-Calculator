package com.project.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Delays the action of moving to the next screen to display the splash screen
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, Calculator.class);
            startActivity(i);
            finish();
        }, 3000);

    }
}