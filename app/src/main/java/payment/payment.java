package payment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.nasri.gp.PlayerActivity;
import com.example.nasri.gp.R;
import com.example.nasri.gp.USSDService;

import java.util.ArrayList;
import java.util.List;

public class payment extends AppCompatActivity {

    Spinner simType ;
    LinearLayout codeLayout ;
    EditText zainCode ;
    LinearLayout perError ;
    TextView ussdRes ;
    String zainNumber = "" ;
    String mtnNumber = "" ;
    String sudaniNumber = "" ;
    boolean zainFlag = false ;
    boolean sudaniFlag = false ;
    boolean mtnFlag = false ;
    boolean perFlag = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        simType = (Spinner) findViewById(R.id.sim_spinner);
        perError = (LinearLayout) findViewById(R.id.permission_error);
        ussdRes = (TextView) findViewById(R.id.ussd_response);

        if (USSDService.perResponse.equals("")){
            ussdRes.setText("proccess completed successfully");

        }else if (USSDService.perResponse.equals("")){
            ussdRes.setText("proccess completed successfully");

        }else if (USSDService.perResponse.equals("")){
            ussdRes.setText("proccess completed successfully");

        }else{
            ussdRes.setTextColor(Color.RED);
            ussdRes.setText("proccess not complete , please try again !");
        }

        List<String> list = new ArrayList<String>();
        //Log.i("tagconvertstr", "["+new String(response.data)+"]");



        if (!PlayerActivity.fInformations.getOwnerPhone().equals("")){
            if (String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(2)).equals("1")||
                    String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(2)).equals("0")||
                    String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(2)).equals("6")){
                list.add("Zain");
                zainNumber = PlayerActivity.fInformations.getOwnerPhone() ;
            }else if (!PlayerActivity.fInformations.getsPone().equals("")){
                if (String.valueOf(PlayerActivity.fInformations.getsPone().charAt(2)).equals("1")||
                        String.valueOf(PlayerActivity.fInformations.getsPone().charAt(2)).equals("0")||
                        String.valueOf(PlayerActivity.fInformations.getsPone().charAt(2)).equals("6")){
                    list.add("Zain");
                    zainNumber = PlayerActivity.fInformations.getsPone() ;
                }
            }else if (!PlayerActivity.fInformations.gettPhone().equals("")){
                if (String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(2)).equals("1")||
                        String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(2)).equals("0")||
                        String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(2)).equals("6")){
                    list.add("Zain");
                    zainNumber = PlayerActivity.fInformations.gettPhone() ;
                }
            }
        }

        if (!PlayerActivity.fInformations.getOwnerPhone().equals("")){
            if (String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(2)).equals("2")||
                    String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(2)).equals("9")){
                list.add("MTN");
                mtnNumber = PlayerActivity.fInformations.getOwnerPhone() ;
            }else if (!PlayerActivity.fInformations.getsPone().equals("")){
                if (String.valueOf(PlayerActivity.fInformations.getsPone().charAt(2)).equals("2")||
                        String.valueOf(PlayerActivity.fInformations.getsPone().charAt(2)).equals("9")){
                    list.add("MTN");
                    mtnNumber = PlayerActivity.fInformations.getsPone() ;
                }else if (!PlayerActivity.fInformations.gettPhone().equals("")){
                    if (String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(2)).equals("2")||
                            String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(2)).equals("9")){
                        list.add("MTN");
                        mtnNumber = PlayerActivity.fInformations.gettPhone() ;
                    }
                }
        }
        }


        if (!PlayerActivity.fInformations.getOwnerPhone().equals("")){
            if (String.valueOf(PlayerActivity.fInformations.getOwnerPhone().charAt(1)).equals("1")){
                list.add("Sudani");
                sudaniNumber = PlayerActivity.fInformations.getOwnerPhone() ;
            }else if (!PlayerActivity.fInformations.gettPhone().equals("")){
                if (String.valueOf(PlayerActivity.fInformations.gettPhone().charAt(1)).equals("1")){
                    list.add("Sudani");
                    sudaniNumber = PlayerActivity.fInformations.gettPhone() ;
                }else if (!PlayerActivity.fInformations.getsPone().equals("")){
                    if (String.valueOf(PlayerActivity.fInformations.getsPone().charAt(1)).equals("1")){
                        list.add("Sudani");
                        sudaniNumber = PlayerActivity.fInformations.getsPone() ;
                    }
        }
        }
        }



        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        codeLayout = (LinearLayout) findViewById(R.id.zain_code_layout);
        simType.setAdapter(dataAdapter);

        simType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (simType.getSelectedItem().toString().equals("Zain")){
                    codeLayout.setVisibility(View.VISIBLE);
                    zainFlag = true ;
                }else {
                    codeLayout.setVisibility(View.GONE);
                    zainFlag = false ;
                }

                if (simType.getSelectedItem().toString().equals("Sudani")){
                    sudaniFlag = true ;
                }else {
                    sudaniFlag = false ;
                }

                if (simType.getSelectedItem().toString().equals("MTN")){
                    mtnFlag = true ;
                }else {
                    mtnFlag = false ;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {
            perFlag = false ;


        } else {
            perFlag = true ;
        }
    }

    public void payment(View v){
        startService(new Intent(payment.this, USSDService.class));
        /*
        if ( ContextCompat.checkSelfPermission(payment.this, Manifest.permission.BIND_ACCESSIBILITY_SERVICE)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("fffffffffffffffffffffffffff2");
            perFlag = false ;

        } else {
            System.out.println("fffffffffffffffffffffffffff3");
            perFlag = true ;
        }
        */
        if (!perFlag){
            String call = "" ;
            if (sudaniFlag){
                perError.setVisibility(View.GONE);
                call = "*303*50*" + sudaniNumber + "*0000" ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" +  Uri.encode("#")));
                startActivity(intent);
            }else if (mtnFlag){
                call = "*121*" + mtnNumber + "*50*00000" ;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + call + Uri.encode("#")));
                startActivity(intent);
            }else if (zainFlag){
                if(TextUtils.isEmpty(zainCode.getText())) {
                    zainCode.setError("Password conform is required!");
                }else {
                    call = "*200*" + zainCode.getText().toString() + "*50*" + zainNumber + "*" + zainNumber;
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + call + Uri.encode("#")));
                    startActivity(intent);
                }
            }
        }else{
            perError.setVisibility(View.VISIBLE);
        }


    }

}