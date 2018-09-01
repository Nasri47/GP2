package com.example.nasri.gp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class FieldSettings extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {

    Switch availability ;
    FieldFragment fieldFragment ;
    private static String USGS_REQUEST_URL =
            "http://192.168.43.172/api/fieldslist/";
    private static final int FIELD_LIST_LOADER_ID = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_settings);
            fieldFragment = new FieldFragment();
        availability = (Switch) findViewById(R.id.switchAB);
        if (fieldFragment.suspend == 2){
            availability.setChecked(false);
            availability.setText("unavailable");
        }
        TextView editInfo = (TextView) findViewById(R.id.edit_info);
        editInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(FieldSettings.this, EditFieldInfo.class);
                startActivity(log);
            }
        });

        TextView changePass = (TextView) findViewById(R.id.change_pass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(FieldSettings.this, ChangePass.class);
                startActivity(log);
            }
        });

        availability.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplication(), "ON", Toast.LENGTH_SHORT)
                            .show();
                    availability.setText("available");
                } else {
                    openDialog();
                    Toast.makeText(getApplication(), "OFF", Toast.LENGTH_SHORT)
                            .show();
                    availability.setText("unavailable");
                }
            }
        });
    }

    public void openDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Closing field");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        final EditText masg = new EditText(this);
        // Message Properties
        masg.setHint("Why your filed is not available ?");
        masg.setSingleLine(false);
        masg.setTextColor(Color.BLACK);
        alertDialog.setView(masg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                availability.setChecked(true);
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CLOSE FIELD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (!masg.getText().toString().equals("")){
                    USGS_REQUEST_URL = "http://192.168.43.172/api/closefield?field_id=" + LoginInfo.fieldId + "&suspend_resons=" + masg.getText().toString();
                    startLoader();
                }else {
                    availability.setChecked(true);
                    Toast.makeText(getApplication(), "You have to write reasons for closing your field", Toast.LENGTH_LONG)
                            .show();
                }
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

    @NonNull
    @Override
    public Loader<FieldInformations> onCreateLoader(int id, @Nullable Bundle args) {
        return new CloseFieldLoder(this , USGS_REQUEST_URL) ;
    }


    @Override
    public void onLoadFinished(@NonNull Loader<FieldInformations> loader, FieldInformations data) {
        Intent log = new Intent(FieldSettings.this, Main2Activity.class);
        startActivity(log);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<FieldInformations> loader) {

    }

    public void startLoader(){
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
    }
}
