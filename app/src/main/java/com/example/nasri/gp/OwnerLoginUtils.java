package com.example.nasri.gp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 8/10/2018.
 */

public class OwnerLoginUtils {

    private static final String LOG_TAG = OwnerLoginUtils.class.getSimpleName();

    private OwnerLoginUtils() {
    }

    private static LoginInfo extractFeatureFromJson(String ownerJSON) {
        LoginInfo loginInfo = new LoginInfo();
        if (TextUtils.isEmpty(ownerJSON)) {
            return null;
        }

        try {

            JSONObject baseJsonResponse = new JSONObject(ownerJSON);
            if (!baseJsonResponse.optBoolean("error")) {
                JSONArray ownerInfoList = baseJsonResponse.getJSONArray("data");
                for (int i = 0; i < ownerInfoList.length(); i++) {
                    JSONObject ownerInfoListJSONObject = ownerInfoList.getJSONObject(i);
                    int userId = ownerInfoListJSONObject.getInt("user_id");
                    int fieldId = ownerInfoListJSONObject.getInt("field_id");
                    int suspendState = ownerInfoListJSONObject.getInt("block_state");
                    String suspendResons = ownerInfoListJSONObject.getString("suspend_resons");
                    int response = ownerInfoListJSONObject.getInt("response");
                    loginInfo = new LoginInfo(response , fieldId , userId , suspendState , suspendResons);
                }
            }else {
                int response = 0;
                loginInfo = new LoginInfo(response);
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return loginInfo;
    }

    public static LoginInfo fetchfieldsData(String requestUrl) {
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
        LoginInfo response = extractFeatureFromJson(jsonResponse);

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
            LoginInfo loginInfo = new LoginInfo();
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            List<ParamValues> params = new ArrayList<ParamValues>();
            params.add(new ParamValues("owner_phone", Third.phone));
            params.add(new ParamValues("password", Third.pass));
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getQuery(params));
            writer.flush();
            writer.close();
            os.close();
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

    private static String getQuery(List<ParamValues> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (ParamValues pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getParametre(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

}
