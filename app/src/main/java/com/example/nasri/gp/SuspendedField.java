package com.example.nasri.gp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SuspendedField extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {

    Switch suspendSwitch ;
    private String USGS_REQUEST_URL ;
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspended_field);
        Third third = new Third();
        suspendSwitch = (Switch) findViewById(R.id.suspend_switch);
        TextView suspendResons = (TextView) findViewById(R.id.suspend_resons);
        suspendResons.setText(third.blockResons);

        suspendSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                    openDialog();
            }
        });
    }

    public void openDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Activate Field");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        final TextView masg = new TextView(this);
        // Message Properties
        masg.setText("Are you sure you want to activate your field ?");
        masg.setGravity(Gravity.CENTER);
        masg.setTextColor(Color.BLACK);
        alertDialog.setView(masg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"ACTIVATE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                USGS_REQUEST_URL =
                        "http://192.168.43.172/api/unblockfield?field_id=" + LoginInfo.fieldId;
                startLoader();
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new SuspendedFieldLoder(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        if (fieldInformations.getResponse() == 1){
            Intent log = new Intent(SuspendedField.this, Main2Activity.class);
            startActivity(log);
            Toast.makeText(getApplication(), "Your field is available now", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {

    }

    public void startLoader(){
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
    }
}
