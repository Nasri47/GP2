package com.example.nasri.gp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    FieldsList mFieldList = new FieldsList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText mFieldName = (EditText) findViewById(R.id.name_field);
        final EditText mFieldLocation = (EditText) findViewById(R.id.field_location);
        EditText mFieldPhone = (EditText) findViewById(R.id.field_phone);
        Button mAddDetails = (Button) findViewById(R.id.add_update);
        mAddDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "add Successfully", Toast.LENGTH_SHORT).show();
//                mFieldList.add(new FieldItem(mFieldName.getText().toString(), mFieldLocation.getText().toString()));
//                Toast.makeText(MainActivity.this,"add Successfully",Toast.LENGTH_SHORT).show();
//                Intent log = new Intent(MainActivity.this, FieldsList.class);
//                startActivity(log);
            }
        });

    }
}