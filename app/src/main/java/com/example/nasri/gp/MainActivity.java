package com.example.nasri.gp;

import android.Manifest;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<FieldInformations> {

    EditText mFieldName;
    EditText ownerNmae;
    EditText mFieldLocation;
    EditText mFieldPhone;
    EditText mFiledPass;
    EditText mFieldConfPass;
    static String fName;
    static String oName;
    static String fLocate;
    static String fPhone;
    static String fPass;
    static double lat ;
    static double lng ;
    private String USGS_REQUEST_URL;
    private int FIELD_LIST_LOADER_ID = 1;
    private Spinner citySpinner;

    TextView fieldSpase;
    TextView selectFieldCity;
    TextView ownerSpase;
    TextView fieldChars;
    TextView ownerChars;
    //phone validate
    TextView phoneSpaces;
    TextView phoneValid;
    TextView phoneLengths;
    //password validate
    TextView passUpper;
    TextView passLowr;
    TextView passLength;
    TextView passNum;
    TextView passMatch;
    boolean selectCity = true;
    LocationManager mLocationManager;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFieldName = (EditText) findViewById(R.id.name_field);
        ownerNmae = (EditText) findViewById(R.id.owner_name);
        citySpinner = (Spinner) findViewById(R.id.field_location);
        mFieldPhone = (EditText) findViewById(R.id.field_phone);
        mFiledPass = (EditText) findViewById(R.id.password_field);
        mFieldConfPass = (EditText) findViewById(R.id.regest_conf_pass);

        fieldSpase = (TextView) findViewById(R.id.field_no_space);
        ownerSpase = (TextView) findViewById(R.id.owner_no_space);
        fieldChars = (TextView) findViewById(R.id.field_only_char);
        ownerChars = (TextView) findViewById(R.id.owner_only_char);
        //phone validate
        phoneSpaces = (TextView) findViewById(R.id.phone_space);
        phoneValid = (TextView) findViewById(R.id.phone_not_valid);
        phoneLengths = (TextView) findViewById(R.id.phone_ten);
        //password validate
        passLength = (TextView) findViewById(R.id.password_length);
        passUpper = (TextView) findViewById(R.id.password_upper_case);
        passLowr = (TextView) findViewById(R.id.password_lower_case);
        passNum = (TextView) findViewById(R.id.password_number);
        passMatch = (TextView) findViewById(R.id.password_not_match);
        selectFieldCity = (TextView) findViewById(R.id.select_field_city);

        List<String> list = new ArrayList<String>();
        list.add("Khartoum");
        list.add("Umdurman");
        list.add("Bahry");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(dataAdapter);
        Button locationbtn = findViewById(R.id.location_btn);
        locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission to access the location is missing.
                    PermissionUtils.requestPermission(MainActivity.this, LOCATION_PERMISSION_REQUEST_CODE,
                            Manifest.permission.ACCESS_FINE_LOCATION, true);
                }
                else
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000,
                            10, mLocationListener);
            }
        });
    }
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
//            Toast.makeText(MainActivity.this,String.valueOf(location.getLatitude()) + "ASD" + String.valueOf(location.getLongitude()),Toast.LENGTH_SHORT).show();
            lat = location.getLatitude();
            lng = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };
    public void addField(View v) {

        boolean ownerCharFlag = false;
        boolean ownerSpaseFlag = true;
        boolean fieldCharFlag = false;
        boolean phoneOnlyNumbers = true;
        boolean phoneLength = false;
        boolean fieldSpaceFlag = true;
        boolean phoneSpace = true;
        //password
        boolean passVLength = true;
        boolean passCNum = true;
        boolean passCUpper = true;
        boolean passCLower = true;
        boolean passMatchV = true;

        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        boolean flag5 = true;
        // Field name
        if (TextUtils.isEmpty(mFieldName.getText())) {
            mFieldName.setError("Field name is required!");
            fieldCharFlag = true;
            flag1 = false;
        } else {
            for (int x = 0; x < mFieldName.getText().toString().length(); x++) {

                if (!String.valueOf(mFieldName.getText().toString().charAt(x)).equals(" ")) {
                    fieldSpaceFlag = true;
                } else {
                    fieldSpaceFlag = false;
                }
            }
            for (int x = 0; x < mFieldName.getText().toString().length(); x++) {

                if (String.valueOf(mFieldName.getText().toString().charAt(x)).matches("[a-zA-Z]+")) {
                    fieldCharFlag = true;
                } else {
                    fieldCharFlag = false;
                    break;
                }
            }
        }

        ///Phone number

        if (TextUtils.isEmpty(mFieldPhone.getText())) {
            mFieldPhone.setError("Phone number is required!");
            phoneOnlyNumbers = true;
            phoneLength = true;
            flag2 = false;
        } else {
            if (mFieldPhone.getText().toString().length() == 10) {
                phoneLength = true;
                String num = String.valueOf(mFieldPhone.getText().toString().charAt(0));
                if (num.matches("0")) {
                    if (String.valueOf(mFieldPhone.getText().toString().charAt(1)).matches("9") ||
                            String.valueOf(mFieldPhone.getText().toString().charAt(1)).matches("1")) {
                        phoneOnlyNumbers = true;
                    } else {
                        phoneOnlyNumbers = false;
                    }
                } else {
                    phoneOnlyNumbers = false;
                }
            } else {
                phoneLength = false;
            }


            for (int i = 0; i < mFieldPhone.getText().toString().length(); i++) {

                if (!String.valueOf(mFieldPhone.getText().toString().charAt(i)).equals(" ")) {
                    phoneSpace = true;
                } else {
                    phoneSpace = false;
                    break;
                }
            }
        }
        //password conferm
        if (TextUtils.isEmpty(mFieldConfPass.getText())) {
            mFieldConfPass.setError("Password conform is required!");
            flag3 = false;
        } else {
            if (!mFieldConfPass.getText().toString().matches(mFiledPass.getText().toString())) {
                passMatchV = false;
            } else {
                passMatchV = true;
            }
        }

        ///Password validate

        if (TextUtils.isEmpty(mFiledPass.getText())) {
            mFiledPass.setError("Password is required!");
            flag4 = false;
        } else {
            for (int x = 0; x < mFiledPass.getText().toString().length(); x++) {

                if (mFiledPass.getText().toString().length() < 8) {
                    passVLength = false;
                } else {
                    passVLength = true;
                }
            }
            for (int x = 0; x < mFiledPass.getText().toString().length(); x++) {
                if (String.valueOf(mFiledPass.getText().toString().charAt(x)).matches("^[0-9]*$")) {
                    passCNum = true;
                    break;
                } else {
                    passCNum = false;
                }
            }

            for (int i = 0; i < mFiledPass.getText().toString().length(); i++) {
                char ch = mFiledPass.getText().toString().charAt(i);
                if (Character.isUpperCase(ch)) {
                    passCUpper = true;
                    break;
                } else {
                    passCUpper = false;
                }
            }

            for (int i = 0; i < mFiledPass.getText().toString().length(); i++) {
                char ch = mFiledPass.getText().toString().charAt(i);
                if (Character.isLowerCase(ch)) {
                    passCLower = true;
                    break;
                } else {
                    passCLower = false;
                }
            }
        }
        // Owner name
        if (TextUtils.isEmpty(ownerNmae.getText())) {
            ownerNmae.setError("Owner name is required!");
            ownerCharFlag = true;
            flag5 = false;
        } else {

            for (int i = 0; i < ownerNmae.getText().toString().length(); i++) {

                if (!String.valueOf(ownerNmae.getText().toString().charAt(i)).equals(" ")) {
                    ownerSpaseFlag = true;
                } else {
                    ownerSpaseFlag = false;
                    break;
                }
            }
            for (int x = 0; x < ownerNmae.getText().toString().length(); x++) {

                if (String.valueOf(ownerNmae.getText().toString().charAt(x)).matches("[a-zA-Z]+")) {
                    ownerCharFlag = true;
                } else {
                    ownerCharFlag = false;
                    break;
                }
            }
        }
        if (!fieldSpaceFlag) {
            fieldSpase.setVisibility(View.VISIBLE);
        } else {
            fieldSpase.setVisibility(View.GONE);
        }
        if (!fieldCharFlag) {
            fieldChars.setVisibility(View.VISIBLE);
        } else {
            fieldChars.setVisibility(View.GONE);
        }
        if (!ownerSpaseFlag) {
            ownerSpase.setVisibility(View.VISIBLE);
        } else {
            ownerSpase.setVisibility(View.GONE);
        }
        if (!ownerCharFlag) {
            ownerChars.setVisibility(View.VISIBLE);
        } else {
            ownerChars.setVisibility(View.GONE);
        }
        //phone validate
        if (!phoneLength) {
            phoneLengths.setVisibility(View.VISIBLE);
        } else {
            phoneLengths.setVisibility(View.GONE);
        }
        if (!phoneOnlyNumbers) {
            phoneValid.setVisibility(View.VISIBLE);
        } else {
            phoneValid.setVisibility(View.GONE);
        }
        if (!phoneSpace) {
            phoneSpaces.setVisibility(View.VISIBLE);
        } else {
            phoneSpaces.setVisibility(View.GONE);
        }
        //password validate
        if (!passCLower) {
            passLowr.setVisibility(View.VISIBLE);
        } else {
            passLowr.setVisibility(View.GONE);
        }
        if (!passCUpper) {
            passUpper.setVisibility(View.VISIBLE);
        } else {
            passUpper.setVisibility(View.GONE);
        }
        if (!passVLength) {
            passLength.setVisibility(View.VISIBLE);
        } else {
            passLength.setVisibility(View.GONE);
        }
        if (!passCNum) {
            passNum.setVisibility(View.VISIBLE);
        } else {
            passNum.setVisibility(View.GONE);
        }
        if (!passMatchV) {
            passMatch.setVisibility(View.VISIBLE);
        } else {
            passMatch.setVisibility(View.GONE);
        }
        if (!selectCity) {
            selectFieldCity.setVisibility(View.VISIBLE);
        } else {
            selectFieldCity.setVisibility(View.GONE);
        }

        if (fieldSpaceFlag && fieldCharFlag && ownerSpaseFlag && ownerCharFlag && phoneLength && phoneOnlyNumbers && phoneSpace && passCLower
                && passCUpper && passVLength && passCNum && passMatchV && flag1 & flag2 & flag3 && flag4 && flag5 && selectCity) {

            USGS_REQUEST_URL =
                    "http://192.168.43.172/api/registerfield/";
            fName = mFieldName.getText().toString();
            oName = ownerNmae.getText().toString();
            fPhone = mFieldPhone.getText().toString();
            fPass = mFiledPass.getText().toString();
            fLocate = citySpinner.getSelectedItem().toString();
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(FIELD_LIST_LOADER_ID, null, this);
            FIELD_LIST_LOADER_ID++;
        }

    }

    @Override
    public Loader<FieldInformations> onCreateLoader(int i, Bundle bundle) {
        return new AddFieldLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<FieldInformations> loader, FieldInformations fieldInformations) {
        if (fieldInformations.getResponse() == 1) {
            LoginInfo.setFieldId(fieldInformations.getFieldId());
            Intent log = new Intent(this, Main2Activity.class);
            startActivity(log);
            mFieldName.setText("");
            mFieldPhone.setText("");
            mFiledPass.setText("");
            ownerNmae.setText("");
            mFieldConfPass.setText("");
        } else {
            mFieldPhone.setText("");
            Toast.makeText(getApplication(), "This phone number already used !", Toast.LENGTH_LONG)
                    .show();
        }

    }

    @Override
    public void onLoaderReset(Loader<FieldInformations> loader) {
    }
}