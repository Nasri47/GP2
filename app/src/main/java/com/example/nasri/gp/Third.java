package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class Third extends AppCompatActivity implements LoaderManager.LoaderCallbacks<LoginInfo> {

    TextView phoneNumber ;
    TextView password ;
    int response ;
    boolean flag = false ;
    private static final int LOGIN_LOADER_ID = 1 ;
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
        String phone = phoneNumber.getText().toString();
        String pass = password.getText().toString();
        USGS_REQUEST_URL =
                "http://192.168.43.172/api/fieldlogin?owner_phone=" + phone
                    + "&password=" + pass;
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(LOGIN_LOADER_ID, null, this);
    }

    @Override
    public Loader<LoginInfo> onCreateLoader(int i, Bundle bundle) {
        return new LoginLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<LoginInfo> loader, LoginInfo loginInfo) {
        if (loginInfo != null) {
            password = (TextView) findViewById(R.id.user_pass);
            response = loginInfo.getResponse();
            if (response == 1 && flag) {
                loginInfo.setResponse(4);
                flag = false ;
                final Intent log = new Intent(Third.this, Main2Activity.class);
                startActivity(log);
                password.setText("");
            } else if (response == 3) {
                password.setText("");
            } else if (response == 2) {
                // Launch the block activity
            } else if (response == 0) {
                // Make the hint visible
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<LoginInfo> loader) {
        response = 4 ;
    }
}
