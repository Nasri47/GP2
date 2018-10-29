package com.example.nasri.gp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.media.MediaRecorder.VideoSource.CAMERA;

public class UpdatePics extends AppCompatActivity  {

    Bitmap FixBitmap;
    Bitmap firstBitmap;
    ImageButton firstImage ;
    private int flag = 0 ;
    Button uploadButton ;
    Uri firstUri ;
    Uri secondUri ;
    Uri thirdUri ;
    private boolean fFlag = false ;
    private int GALLERY = 1, CAMERA = 2;

    private String IMG_UPLOAD_URL =
            "http://192.168.43.172/api/addimages";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pics);

        uploadButton = (Button) findViewById(R.id.upload_bt);
        firstImage = (ImageButton) findViewById(R.id.first_image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            finish();
            startActivity(intent);
            return;
        }
    }

    public void firstImage(View v){
        flag = 1 ;
        showPictureDialog();
    }

    public void secondImage(View v){
        flag = 2 ;
        showPictureDialog();
    }

    public void thirdImage(View v){
        flag = 3 ;
        showPictureDialog();
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Photo Gallery",
                "Camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                if (flag == 1){
                    firstUri = data.getData();
                }else if (flag == 2){
                    secondUri = data.getData();
                }else if (flag == 3){
                    thirdUri = data.getData();
                }
                try {
                    // String path = saveImage(bitmap);
                    //Toast.makeText(UpdatePics.this, "Image Saved !", Toast.LENGTH_SHORT).show();
                    if (flag == 1){
                        firstBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), firstUri);
                        firstImage.setImageBitmap(firstBitmap);
                        fFlag = true ;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(UpdatePics.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
                if (flag == 1){
                    firstUri = data.getData();
                }else if (flag == 2){
                    secondUri = data.getData();
                }else if (flag == 3){
                    thirdUri = data.getData();
                }
            FixBitmap = (Bitmap) data.getExtras().get("data");
            if (flag == 1){
                firstBitmap = (Bitmap) data.getExtras().get("data");
                firstImage.setImageBitmap(FixBitmap);
                fFlag = true ;
            }
            //  saveImage(thumbnail);
            //Toast.makeText(ShadiRegistrationPart5.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }


    public void uploadButton(View v){
        uploadBitmap();
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap() {

        //getting the tag from the edittext
        final String tags = Integer.toString(LoginInfo.fieldId) ;
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, IMG_UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            //Log.i("tagconvertstr", "["+new String(response.data)+"]");
                            String x = new String(response.data) ;
                            JSONObject obj = new JSONObject(x);
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("field_id", tags);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                if (firstBitmap != null){
                    params.put("pic1", new DataPart(imagename + ".png", getFileDataFromDrawable(firstBitmap)));
                }
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
