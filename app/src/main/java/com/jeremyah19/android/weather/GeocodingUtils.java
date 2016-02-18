package com.jeremyah19.android.weather;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

// Takes address, and uses google maps api to get latitude, longitude, etc.
public class GeocodingUtils {
    public static final String TAG = "GeocodingUtils";

    public static final String BUNDLE_KEY_CITY = "city";
    public static final String BUNDLE_KEY_LATITUDE = "latitude";
    public static final String BUNDLE_KEY_LONGITUDE = "longitude";

    private static final String API_DOMAIN = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String API_KEY = "AIzaSyCT-IQj9wSSKEA950CNqAymSDQVLzt4I9c";

    public static Bundle getLocationInfo(String address) {
        Bundle info = new Bundle();
        Uri uri = Uri.parse(API_DOMAIN)
                .buildUpon()
                .appendQueryParameter("address", address)
                .appendQueryParameter("key", API_KEY)
                .build();
        try {
            JSONObject jsonObject = new JSONObject(new String(getUrlBytes(uri.toString())));
            Log.i(TAG, "Received JSON: " + jsonObject.toString());

            JSONObject resultsObject = jsonObject.getJSONArray("results").getJSONObject(0);

            String city = resultsObject
                    .getJSONArray("address_components")
                    .getJSONObject(0)
                    .getString("long_name");

            JSONObject locationObject = resultsObject
                    .getJSONObject("geometry")
                    .getJSONObject("location");

            info.putString(BUNDLE_KEY_CITY, city);
            info.putDouble(BUNDLE_KEY_LATITUDE, locationObject.getDouble("lat"));
            info.putDouble(BUNDLE_KEY_LONGITUDE, locationObject.getDouble("lng"));

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to connect", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }

        return info;
    }

    public static byte[] getUrlBytes(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + url);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

}
