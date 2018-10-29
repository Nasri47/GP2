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

import java.util.List;

public class Blocking_user extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<UserInfo>>{

    private static String USGS_REQUEST_URL =
            "http://192.168.43.172/api/blockuser";
    private static final int FIELD_LIST_LOADER_ID = 1 ;
    private TextView uName ;
    private TextView uPhone ;
    private EditText resons ;
    private Button block ;
    private int userId ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocking_user);
        Bundle bundle = getIntent().getExtras();
        userId = bundle.getInt("user_id");
        String userName = bundle.getString("user_name");
        String userPhone = bundle.getString("user_phone");
        uName = (TextView) findViewById(R.id.user_name_block) ;
        uName.setText(userName);
        uPhone = (TextView) findViewById(R.id.user_phone_block);
        uPhone.setText(userPhone);
        block = (Button) findViewById(R.id.block_button);
        resons = (EditText) findViewById(R.id.user_block_resons);
    }

    public void block(View v){
        if(!resons.getText().toString().isEmpty()){
            USGS_REQUEST_URL += "?field_id=" + LoginInfo.getFieldId() +  "&user_id=" + userId + "&resons=" + resons.getText() ;
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<List<UserInfo>> onCreateLoader(int i, Bundle bundle) {
        return new BlockUserLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<List<UserInfo>> loader, List<UserInfo> userInfos) {
        Intent i = new Intent(this, ClientsActivity.class);
        startActivity(i);
    }

    @Override
    public void onLoaderReset(Loader<List<UserInfo>> loader) {

    }


}
