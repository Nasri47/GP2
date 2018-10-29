package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ChangePass extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ChangePassRespons> {

    private static String USGS_REQUEST_URL;
    private static int CHANGE_PASS_LOADER_ID = 1 ;
    EditText oldPass ;
    EditText newPass ;
    EditText confPass ;
    static String oldP ;
    static String newP ;
    static String fId ;
    Button change ;
    Button cancel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        oldPass = (EditText) findViewById(R.id.old_pass) ;
        newPass = (EditText) findViewById(R.id.new_pass) ;
        confPass = (EditText) findViewById(R.id.conf_pass) ;

        cancel = (Button) findViewById(R.id.cancel_butt) ;
        change = (Button) findViewById(R.id.change_butt) ;

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(ChangePass.this, FieldSettings.class);
                startActivity(log);
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (newPass.getText().toString().equals(confPass.getText().toString())){
                    onClickChange() ;
                }else{
                    Toast.makeText(getApplication(), "The confirm password dos not match please try again", Toast.LENGTH_LONG)
                            .show();
                    confPass.setText("");
                }
            }
        });

    }

    public void onClickChange(){
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/changepassword/";
        oldP = oldPass.getText().toString() ;
        newP = newPass.getText().toString() ;
        fId = String.valueOf(LoginInfo.fieldId) ;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(CHANGE_PASS_LOADER_ID , null, this);
        CHANGE_PASS_LOADER_ID++ ;
    }

    @Override
    public Loader<ChangePassRespons> onCreateLoader(int i, Bundle bundle) {
        return new ChangePassLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<ChangePassRespons> loader, ChangePassRespons changePassRespons) {
        if(changePassRespons != null){
            if (changePassRespons.getResponse() == 1){
                Toast.makeText(getApplication(), "Password successfully changed", Toast.LENGTH_LONG)
                        .show();
                oldPass.setText("");
                newPass.setText("");
                confPass.setText("");
            }else if (changePassRespons.getResponse() == 2){
                Toast.makeText(getApplication(), "Wrong password please try again !", Toast.LENGTH_LONG)
                        .show();
                oldPass.setText("");
                newPass.setText("");
                confPass.setText("");
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<ChangePassRespons> loader) {

    }

}
