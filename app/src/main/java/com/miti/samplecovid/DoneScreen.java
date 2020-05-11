package com.miti.samplecovid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DoneScreen extends AppCompatActivity {

    FloatingActionButton btnFloatingCloseVAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_screen);

        btnFloatingCloseVAR = findViewById(R.id.btnFloatingCloseId);
        btnFloatingCloseVAR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
