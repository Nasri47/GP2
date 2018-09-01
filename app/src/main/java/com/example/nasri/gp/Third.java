package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Third extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LoginInfo> {

    TextView phoneNumber ;
    TextView password ;
    static int blockState ;
    static String phone ;
    static String pass ;
    int respons ;
    static String blockResons ;
    boolean flag = false ;
    private static int LOGIN_LOADER_ID = 1 ;
    private String USGS_REQUEST_URL ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Button mRegisterButton = (Button) findViewById(R.id.btn_register);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Third.this, MainActivity.class);
                startActivity(log);
            }
        });

        Button mReserveButton = (Button) findViewById(R.id.btn_reserve);
        mReserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log = new Intent(Third.this, FieldsList.class);
                startActivity(log);
            }
        });
    }

    // login method
    public void login(View v){
        flag = true ;
        phoneNumber = (TextView) findViewById(R.id.user_phone);
        password = (TextView) findViewById(R.id.user_pass) ;
        phone = phoneNumber.getText().toString();
        pass = password.getText().toString();
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/fieldlogin/";
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOGIN_LOADER_ID, null, this);
        LOGIN_LOADER_ID++;
    }

    @Override
    public Loader<LoginInfo> onCreateLoader(int i, Bundle bundle) {
        return new LoginLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<LoginInfo> loader, LoginInfo loginInfo) {
        if (loginInfo != null) {
            password = (TextView) findViewById(R.id.user_pass);
            respons = loginInfo.getResponse() ;
            blockState = loginInfo.getSuspendState();
            blockResons = loginInfo.getSuspendResons();
            if (blockState == 1 && flag) {
                loginInfo.setResponse(4);
                flag = false ;
                final Intent log = new Intent(Third.this, Main2Activity.class);
                startActivity(log);
                password.setText("");
            } else if (blockState == 3 && flag) {
                flag = false ;
                password.setText("");
            } else if (blockState == 2 && flag) {
                flag = false ;
                final Intent log = new Intent(Third.this, SuspendedField.class);
                startActivity(log);
                password.setText("");
            }
            else if (blockState == 4 && flag) {
                flag = false ;
                Toast.makeText(getApplication(), "Wrong password , please try again !", Toast.LENGTH_LONG)
                        .show();
                password.setText("");
            }
            else if (respons == 0 && flag) {
                flag = false ;
                Toast.makeText(getApplication(), "Sorry ! the phone number you entered is not registered", Toast.LENGTH_LONG)
                        .show();
                password.setText("");
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<LoginInfo> loader) {}
}
