package com.example.nasri.gp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 8/11/2018.
 */

public class FieldInfoUtils {

    private static final String LOG_TAG = FieldInfoUtils.class.getSimpleName();
    private FieldInfoUtils() {
    }

    private static FieldInformations extractFeatureFromJson(String ownerJSON) {
        FieldInformations fieldInfo = new FieldInformations();
        if (TextUtils.isEmpty(ownerJSON)) {
            return null;
        }

        try {

            JSONObject baseJsonResponse = new JSONObject(ownerJSON);
            List<ResearvationsRequistsInfo> reserveInfo = new ArrayList<>();
            ResearvationsRequistsInfo reservs ;
            if (!baseJsonResponse.optBoolean("error")) {
                JSONArray reservesList = baseJsonResponse.getJSONArray("reserves");
                JSONArray ownerInfoList = baseJsonResponse.getJSONArray("data");
                for (int i = 0; i < ownerInfoList.length(); i++) {
                    JSONObject ownerInfoListJSONObject = ownerInfoList.getJSONObject(i);
                    int fieldId = ownerInfoListJSONObject.getInt("id");
                    int suspendState = ownerInfoListJSONObject.getInt("block_state") ;
                    String ownerName = ownerInfoListJSONObject.getString("owner_name");
                    String fieldName = ownerInfoListJSONObject.getString("field_name");
                    String fieldCity = ownerInfoListJSONObject.getString("field_city");
                    String fieldSize = ownerInfoListJSONObject.getString("field_size");
                    String hourPrice = ownerInfoListJSONObject.getString("field_hour_price");
                    String phone = ownerInfoListJSONObject.getString("owner_phone");
                    String openTime = ownerInfoListJSONObject.getString("open_time") ;
                    String closeTime = ownerInfoListJSONObject.getString("close_time") ;
                    String resons = ownerInfoListJSONObject.getString("suspend_resons");
                    long lat = ownerInfoListJSONObject.getLong("field_lat") ;
                    long lng = ownerInfoListJSONObject.getLong("field_lng") ;
                    for (int x = 0; x < reservesList.length(); x++) {
                        JSONObject reservesObject = reservesList.getJSONObject(x);
                        int reserveId = reservesObject.getInt("id");
                        String reserveStart = reservesObject.getString("reserve_frome");
                        String reserveEnd = reservesObject.getString("reserve_to");
                        String userName = reservesObject.getString("user_name");
                        String userPhone = reservesObject.getString("user_phone");
                        reservs = new ResearvationsRequistsInfo(reserveId , reserveStart , reserveEnd ,
                                                                userName , userPhone);
                        reserveInfo.add(reservs);

                    }
                    fieldInfo = new FieldInformations(fieldId , fieldName , ownerName , fieldCity ,
                            fieldSize , hourPrice , phone , openTime ,
                            closeTime , reserveInfo , lat , lng , suspendState , resons);
                }

            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return fieldInfo;
    }

    public static FieldInformations fetchfieldsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        FieldInformations response = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return response;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {

            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {

                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
