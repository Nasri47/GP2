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

    EditText mFieldName;
    EditText ownerNmae ;
    EditText mFieldLocation ;
    EditText mFieldPhone;
    EditText mFiledPass;
    EditText mFieldConfPass ;
    static String fName ;
    static String oName ;
    static String fLocate ;
    static String fPhone ;
    static String fPass ;
    private String USGS_REQUEST_URL ;
    private int FIELD_LIST_LOADER_ID = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFieldName = (EditText) findViewById(R.id.name_field);
        ownerNmae = (EditText) findViewById(R.id.owner_name);
        mFieldLocation = (EditText) findViewById(R.id.field_location);
        mFieldPhone = (EditText) findViewById(R.id.field_phone);
        mFiledPass = (EditText) findViewById(R.id.password_field);
        mFieldConfPass = (EditText) findViewById(R.id.conf_pass);
         }

    public void addField(View v){
        if (!mFieldName.getText().toString().equals("")&& !ownerNmae.getText().toString().equals("")&&
                !mFieldLocation.getText().toString().equals("")&& !mFiledPass.getText().toString().equals("")&&
                !mFieldPhone.getText().toString().equals("")){
            if (mFiledPass.getText().toString().equals(mFieldConfPass.getText().toString())){
                USGS_REQUEST_URL =
                        "http://192.168.43.172/api/registerfield/";
                fName = mFieldName.getText().toString();
                oName = ownerNmae.getText().toString();
                fPhone = mFieldPhone.getText().toString();
                fPass = mFiledPass.getText().toString();
                fLocate = mFieldLocation.getText().toString();
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
            }else {
                Toast.makeText(getApplication(), "Password you entered dos not match, please try again", Toast.LENGTH_LONG)
                        .show();
                mFieldConfPass.setText("");
            }

        }else{
            Toast.makeText(getApplication(), "Please fill all the informations ", Toast.LENGTH_LONG)
                    .show();
        }
        FIELD_LIST_LOADER_ID++;
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
            mFieldConfPass.setText("");
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