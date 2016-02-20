package com.jeremyah19.android.weather;


import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class ForecastApiUtils {
    public static final String TAG = "ForecastApiUtils";

    private static final String API_DOMAIN = "https://api.forecast.io/forecast";

    public static ForecastInfo getForecastInfo(double latitude, double longitude) {
        ForecastInfo forecastInfo = null;
        try {
            JSONObject jsonObject = new JSONObject(
                    new String(GeocodingUtils.getUrlBytes(
                            buildUri(latitude, longitude).toString())));

            forecastInfo = ForecastInfo.getInstance();
            forecastInfo.setTimezone(jsonObject.getString("timezone"));
            forecastInfo.setOffset(jsonObject.getDouble("offset"));

            Log.i(TAG, "Received JSON: " + jsonObject.toString());

            JSONObject cObject = jsonObject.getJSONObject("currently");
            JSONObject hObject = jsonObject.getJSONObject("hourly");
            JSONObject dObject = jsonObject.getJSONObject("daily");
            JSONObject mObject;
            if(jsonObject.isNull("minutely")) {
                mObject = null;
                forecastInfo.setHasMinutelyData(false);
            } else {
                mObject = jsonObject.getJSONObject("minutely");
                forecastInfo.setHasMinutelyData(true);
            }

            // Set current forecast data
            forecastInfo.setCurrentlyTime(new Date(cObject.getLong("time") * 1000));
            forecastInfo.setCurrentlyPrecipIntensity(
                    cObject.getDouble("precipIntensity"));
            forecastInfo.setCurrentlyPrecipProbability(
                    cObject.getDouble("precipProbability"));
            forecastInfo.setCurrentlyTemperature(cObject.getDouble("temperature"));
            forecastInfo.setCurrentlyApparentTemperature(
                    cObject.getDouble("apparentTemperature"));
            forecastInfo.setCurrentlyHumidity(cObject.getDouble("humidity"));

            // Set rest of data for all forecasts
            if(forecastInfo.hasMinutelyData()) {
                forecastInfo.setSummaries(
                        cObject.getString("summary"),
                        mObject.getString("summary"),
                        hObject.getString("summary"),
                        dObject.getString("summary")
                );

                forecastInfo.setIcons(
                        cObject.getString("icon"),
                        mObject.getString("icon"),
                        hObject.getString("icon"),
                        dObject.getString("icon")
                );

                forecastInfo.setData(
                        mObject.getJSONArray("data"),
                        hObject.getJSONArray("data"),
                        dObject.getJSONArray("data")
                );
            } else {
                forecastInfo.setSummaries(
                        cObject.getString("summary"),
                        hObject.getString("summary"),
                        dObject.getString("summary")
                );

                forecastInfo.setIcons(
                        cObject.getString("icon"),
                        hObject.getString("icon"),
                        dObject.getString("icon")
                );

                forecastInfo.setData(
                        hObject.getJSONArray("data"),
                        dObject.getJSONArray("data")
                );
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to connect", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return forecastInfo;
    }

    private static Uri buildUri(double latitude, double longitude) {
        return Uri.parse(API_DOMAIN)
                .buildUpon()
                .appendPath(ApiKeys.FORECAST_API_KEY)
                .appendPath(Double.toString(latitude) + "," + Double.toString(longitude))
                .build();

    }

}
