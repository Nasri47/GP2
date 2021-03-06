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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 8/12/2018.
 */

public class ComplaintUtils {

    private static final String LOG_TAG = ComplaintUtils.class.getSimpleName();
    private ComplaintUtils() {
    }

    private static List<ComplaintsInfo> extractFeatureFromJson(String ownerJSON) {
        List<ComplaintsInfo> complaintsInfo = new ArrayList<>();
        if (TextUtils.isEmpty(ownerJSON)) {
            return null;
        }
         try {
                JSONObject baseJsonResponse = new JSONObject(ownerJSON);
                if (!baseJsonResponse.optBoolean("error")) {
                    JSONArray complaintsList = baseJsonResponse.getJSONArray("data");
                    for (int i = 0; i < complaintsList.length(); i++) {
                        JSONObject reserveListJSONObject = complaintsList.getJSONObject(i);
                        int complaintId = reserveListJSONObject.getInt("id");
                        String userName = reserveListJSONObject.getString("user_name");
                        String userPhone = reserveListJSONObject.getString("user_phone");
                        String complaint = reserveListJSONObject.getString("complaint");
                        ComplaintsInfo reserveObj = new ComplaintsInfo(complaintId , userName , userPhone , complaint);
                        complaintsInfo.add(reserveObj);
                    }
                }
            } catch (JSONException e) {

                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return complaintsInfo;
    }

    public static List<ComplaintsInfo> fetchfieldsData(String requestUrl) {
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
        List<ComplaintsInfo> response = extractFeatureFromJson(jsonResponse);

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
