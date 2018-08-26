package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SendComplaint extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ComplaintsInfo> {

    PlayerActivity fieldInformations = new PlayerActivity();
    TextView userPhone , complaint;
    private static String USGS_REQUEST_URL ;
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    private static final String LOG_TAG = FieldsList.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_complaint);





    }

    @Override
    public Loader<ComplaintsInfo> onCreateLoader(int i, Bundle bundle) {
        return new SendComlaintsLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<ComplaintsInfo> loader, ComplaintsInfo complaintsInfo) {
        if (complaintsInfo.getComlaintResponse() == 1){
            Toast.makeText(this, "Thank you \n you will get a respond soon..",
                    Toast.LENGTH_LONG).show();
            userPhone.setText("");
            complaint.setText("");
        }else if (complaintsInfo.getComlaintResponse() == 2){
            userPhone.setText("");
            Toast.makeText(this, "Sorry ! the phone number you entered is not registered..",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<ComplaintsInfo> loader) {}

    public void sendComlaint(View v){
        userPhone = (TextView) findViewById(R.id.comp_phone) ;
        complaint = (TextView) findViewById(R.id.complaint) ;
        USGS_REQUEST_URL = "http://192.168.43.172/api/sendcomplaint?field_id=" + fieldInformations.fieldId + "&userphone="
                + userPhone.getText().toString() + "&complaint=" + complaint.getText().toString();
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
    }
}
