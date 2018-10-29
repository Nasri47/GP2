package com.example.nasri.gp;

import android.app.LoaderManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.content.Loader;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import payment.payment;

/**
 * A login screen that offers login via email/password.
 */
public class reserveRegester extends AppCompatActivity implements LoaderManager.LoaderCallbacks<UserInfo>{

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    TextView userPhoneLength ;
    TextView userPhoneValid ;
    TextView userPhoneSpace ;
    TextView userNameChars ;
    TextView userNameSpaces ;
    private static final String USGS_REQUEST_URL =
            "http://192.168.43.172/api/fieldslist/";
    private static final int FIELD_LIST_LOADER_ID = 1 ;


    // UI references.
    private EditText userName;
    private EditText userPhone;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_regester);
        // Set up the login form.
        userName = (EditText) findViewById(R.id.user_regist_name);
        userPhone = (EditText) findViewById(R.id.user_regist_phone);
        userPhoneLength = (TextView) findViewById(R.id.user_phone_ten);
        userPhoneValid = (TextView) findViewById(R.id.user_phone_not_valid);
        userPhoneSpace = (TextView) findViewById(R.id.user_phone_space);
        userNameChars = (TextView) findViewById(R.id.user_only_char);
        userNameSpaces = (TextView) findViewById(R.id.user_no_space);


        userPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userPhone.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        return false;
                    }
                });

                    int len = userPhone.getText().length();
                    if(len == 10) {

                    }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

    }


    public void onDone(View v){
        boolean userNameChar = false ;
        boolean userNameSpace = true ;
        boolean phoneSpace = false ;
        boolean phoneLength = true ;
        boolean phoneValidate = false ;
        boolean flag1 = true ; boolean flag2 = true ;

        if( TextUtils.isEmpty(userName.getText())) {
            userName.setError("Field name is required!");
            userNameChar = true;
            flag1 = false;
        }else {
            for (int x = 0; x < userName.getText().toString().length(); x++) {

                if (!String.valueOf(userName.getText().toString().charAt(x)).equals(" ")) {
                    userNameSpace = true;
                } else {
                    userNameSpace = false;
                }
            }
            for (int x = 0; x < userName.getText().toString().length(); x++) {

                if (String.valueOf(userName.getText().toString().charAt(x)).matches("[a-zA-Z]+")) {
                    userNameChar = true;
                } else {
                    userNameChar = false;
                    break;
                }
            }
        }

        ///Phone number

        if( TextUtils.isEmpty(userPhone.getText())) {
            userPhone.setError("Phone number is required!");
            phoneValidate = true;
            phoneLength = true ;
            phoneSpace = true;
            flag2 = false;
        }else {
            if (userPhone.getText().toString().length() == 10) {
                phoneLength = true;
                String num = String.valueOf(userPhone.getText().toString().charAt(0));
                if (num.matches("0")) {
                    if (String.valueOf(userPhone.getText().toString().charAt(1)).matches("9") ||
                            String.valueOf(userPhone.getText().toString().charAt(1)).matches("1")) {
                        phoneValidate = true;
                    }else{
                        phoneValidate = false;
                    }
                } else {
                    phoneValidate = false;
                }
            } else {
                phoneLength = false;
            }

            for (int i = 0; i < userPhone.getText().toString().length(); i++) {

                if (!String.valueOf(userPhone.getText().toString().charAt(i)).equals(" ")) {
                    phoneSpace = true;
                } else {
                    phoneSpace = false;
                    break;
                }
            }
        }

        if (!userNameChar){userNameChars.setVisibility(View.VISIBLE);}else {userNameChars.setVisibility(View.GONE);}
        if (!userNameSpace){userNameSpaces.setVisibility(View.VISIBLE);}else {userNameSpaces.setVisibility(View.GONE);}
        if (!phoneSpace){userPhoneSpace.setVisibility(View.VISIBLE);}else {userPhoneSpace.setVisibility(View.GONE);}
        if (!phoneValidate){userPhoneValid.setVisibility(View.VISIBLE);}else {userPhoneValid.setVisibility(View.GONE);}
        if (!phoneLength){userPhoneLength.setVisibility(View.VISIBLE);}else {userPhoneLength.setVisibility(View.GONE);}


        if (flag1&&flag2&&userNameChar&&userNameSpace&&phoneSpace&&phoneValidate&&phoneLength){

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
        }
    }



    public void payButton(View view){

        boolean userNameChar = false ;
        boolean userNameSpace = true ;
        boolean phoneSpace = false ;
        boolean phoneLength = true ;
        boolean phoneValidate = false ;
        boolean flag1 = true ; boolean flag2 = true ;

        if( TextUtils.isEmpty(userName.getText())) {
            userName.setError("User name is required!");
            userNameChar = true;
            flag1 = false;
        }else {
            for (int x = 0; x < userName.getText().toString().length(); x++) {

                if (!String.valueOf(userName.getText().toString().charAt(x)).equals(" ")) {
                    userNameSpace = true;
                } else {
                    userNameSpace = false;
                }
            }
            for (int x = 0; x < userName.getText().toString().length(); x++) {

                if (String.valueOf(userName.getText().toString().charAt(x)).matches("[a-zA-Z]+")) {
                    userNameChar = true;
                } else {
                    userNameChar = false;
                    break;
                }
            }
        }

        ///Phone number

        if( TextUtils.isEmpty(userPhone.getText())) {
            userPhone.setError("Phone number is required!");
            phoneValidate = true;
            phoneLength = true ;
            flag2 = false;
        }else {
            if (userPhone.getText().toString().length() == 10) {
                phoneLength = true;
                String num = String.valueOf(userPhone.getText().toString().charAt(0));
                if (num.matches("0")) {
                    if (String.valueOf(userPhone.getText().toString().charAt(1)).matches("9") ||
                            String.valueOf(userPhone.getText().toString().charAt(1)).matches("1")) {
                        phoneValidate = true;
                    }else{
                        phoneValidate = false;
                    }
                } else {
                    phoneValidate = false;
                }
            } else {
                phoneLength = false;
            }

            for (int i = 0; i < userPhone.getText().toString().length(); i++) {

                if (!String.valueOf(userPhone.getText().toString().charAt(i)).equals(" ")) {
                    phoneSpace = true;
                } else {
                    phoneSpace = false;
                    break;
                }
            }
        }
        if (flag1&&flag2&&userNameChar&&userNameSpace&&phoneSpace&&phoneValidate&&phoneLength){
            Intent log = new Intent(reserveRegester.this, payment.class);
            startActivity(log);
            }

    }

    @Override
    public Loader<UserInfo> onCreateLoader(int i, Bundle bundle) {
        return new UserRegisterLoader(this , USGS_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<UserInfo> loader, UserInfo userInfo) {

    }

    @Override
    public void onLoaderReset(Loader<UserInfo> loader) {

    }
}

