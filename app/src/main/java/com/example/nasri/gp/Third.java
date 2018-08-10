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
    String response ;
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
                Intent log = new Intent(Third.this, RegisterField.class);
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

        if(loginInfo != null ){
            response = loginInfo.getResponse();
        }
        if (response.equalsIgnoreCase("OK")) {
            final Intent log = new Intent(Third.this, Main2Activity.class);
            startActivity(log);
        }else if (response.equalsIgnoreCase("Wrong password ! please try again..")){
            password = (TextView) findViewById(R.id.user_pass);
            password.clearComposingText();
        }else if (response.equalsIgnoreCase("sorry ! this user is blocked frome using the app..")){
            // Launch the block activity
        }else if(response.equalsIgnoreCase("You dont have acount")){
            // Make the hint visible
        }
    }

    @Override
    public void onLoaderReset(Loader<LoginInfo> loader) {
            response = "" ;
    }
}
