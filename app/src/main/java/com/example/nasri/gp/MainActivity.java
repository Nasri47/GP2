package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations>{

    FieldsList mFieldList = new FieldsList();
    EditText mFieldName;
    EditText ownerNmae ;
    EditText mFieldLocation ;
    EditText mFieldPhone;
    EditText mFiledPass;
    private String USGS_REQUEST_URL ;
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFieldName = (EditText) findViewById(R.id.name_field);
        ownerNmae = (EditText) findViewById(R.id.owner_name);
        mFieldLocation = (EditText) findViewById(R.id.field_location);
        mFieldPhone = (EditText) findViewById(R.id.field_phone);
        mFiledPass = (EditText) findViewById(R.id.password_field);
         }

    public void addField(View v){
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/registerfield?owner_name=" + ownerNmae.getText().toString() +
                        "&field_name=" + mFieldName.getText().toString() + "&field_city=" + mFieldLocation.getText().toString()
                        + "&owner_phone=" + mFieldPhone.getText().toString() + "&password=" + mFiledPass.getText().toString();
        //Toast.makeText(this , "add Successfully", Toast.LENGTH_LONG).show();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
    }

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new AddFieldLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        if (fieldInformations.getResponse() == 1){
            LoginInfo.setFieldId(fieldInformations.getFieldId());
            Intent log = new Intent(this , Main2Activity.class);
            startActivity(log);
            mFieldName.setText("");
            mFieldLocation.setText("");
            mFieldPhone.setText("");
            mFiledPass.setText("");
            ownerNmae.setText("");
        }else {
            mFieldPhone.setText("");
            Toast.makeText(getApplication(), "This phone number already used !", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {

    }
}