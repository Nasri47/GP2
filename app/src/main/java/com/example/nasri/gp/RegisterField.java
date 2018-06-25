package com.example.nasri.gp;
        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;

public class RegisterField extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegist(View view){
        Intent log = new Intent(this , Main2Activity.class) ;
        startActivity(log);
    }
}
