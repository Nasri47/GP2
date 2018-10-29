package com.example.nasri.gp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Complaints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ComplaintsFragment()).commit();

    }
}
