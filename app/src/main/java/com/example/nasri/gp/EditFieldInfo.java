package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class EditFieldInfo extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {

    FieldFragment fieldFragment = new FieldFragment();
    EditText fieldName ;
    EditText phone ;
    EditText city ;
    EditText price ;
    EditText size ;
    EditText open ;
    EditText close ;
    EditText secondPhone ;
    EditText thirdPhone ;
    private static String USGS_REQUEST_URL ;
    private static final int FIELD_LIST_LOADER_ID = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_field_info);
        fieldName = (EditText) findViewById(R.id.edit_field_name) ;
        price = (EditText) findViewById(R.id.edit_price) ;
        size = (EditText) findViewById(R.id.edit_size) ;
        city = (EditText) findViewById(R.id.edit_city) ;
        phone = (EditText) findViewById(R.id.edit_phone) ;
        open = (EditText) findViewById(R.id.edit_open) ;
        close = (EditText) findViewById(R.id.edit_close) ;
        secondPhone = (EditText) findViewById(R.id.second_phone) ;
        thirdPhone = (EditText) findViewById(R.id.third_phone) ;
        fieldName.setText(fieldFragment.fieldN);
        size.setText(fieldFragment.fieldS);
        city.setText(fieldFragment.fieldC);
        phone.setText(fieldFragment.fieldPN);
        if (!fieldFragment.fieldCT.equals("00:00:00")){
            open.setText(fieldFragment.fieldOT);
        }
        if (!fieldFragment.fieldOT.equals("00:00:00")){
            close.setText(fieldFragment.fieldCT);
        }
        if (!fieldFragment.fieldP.equals(0)){
            price.setText(fieldFragment.fieldP);
        }
    }

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new EditFieldInfoLoder(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        LoginInfo.setFieldId(fieldFragment.fieldId);
        Intent log = new Intent(EditFieldInfo.this, Main2Activity.class);
        startActivity(log);
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {}

    public void onSave (View v){
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/updatefieldinfo?field_id=" + fieldFragment.fieldId + "&field_name=" + fieldName.getText().toString()
                        + "&field_size=" + size.getText().toString() + "&field_city=" + city.getText().toString() + "&open_time=" + open.getText().toString()  +
                        "&close_time=" + close.getText().toString() + "&hour_price=" + price.getText().toString()
                        + "&f_phone=" + phone.getText().toString()  +
                        "&s_phone=" + secondPhone.getText().toString() + "&t_phone=" + thirdPhone.getText().toString();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
    }
}