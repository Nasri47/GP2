package com.example.nasri.gp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
    }

    public void onLogin(View view){
        Intent log = new Intent(this , Main2Activity.class) ;
        startActivity(log);
    }

    public void register(View view){
        Intent log = new Intent(this , RegisterField.class) ;
        startActivity(log);
    }

    public void fieldsList(View view){
        Intent log = new Intent(this , FieldsList.class) ;
        startActivity(log);
    }

}