package com.jeremyah19.android.weather;


import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class ForecastApiUtils {
    public static final String TAG = "ForecastApiUtils";

    public static final String API_DOMAIN = "https://api.forecast.io/forecast";
    public static final String API_KEY = "a0f2b9ca2ff275e7533fefe72dce1907";

    public static Forecast getForecast(double latitude, double longitude,
                                                   double timePeriod) {

        Uri uri = Uri.parse(API_DOMAIN)
                .buildUpon()
                .appendPath(API_KEY)
                .appendPath(Double.toString(latitude) + "," + Double.toString(longitude))
                .build();

        try {
            JSONObject jsonObject =
                    new JSONObject(new String(GeocodingUtils.getUrlBytes(uri.toString())));
            Log.i(TAG, "Received JSON: " + jsonObject.toString());

            JSONObject forecastObject;
            Log.d(TAG, Double.toString(timePeriod));
            switch((int)timePeriod) {
                case 0: // Current weather conditions
                    forecastObject = jsonObject.getJSONObject("currently");
                    CurrentlyForecast currentlyForecast = CurrentlyForecast.getInstance();
                    currentlyForecast.setSummary(forecastObject.getString("summary"));
                    currentlyForecast.setIcon(forecastObject.getString("icon"));
                    currentlyForecast.setTime(new Date(forecastObject.getLong("time")));
                    currentlyForecast.setTemperature(forecastObject.getDouble("temperature"));
                    currentlyForecast.setApparentTemperature(
                            forecastObject.getDouble("apparentTemperature"));
                    currentlyForecast.setHumidity(forecastObject.getDouble("humidity"));
                    Log.d(TAG, "curently");
                    return currentlyForecast;
                case 1: // Weather conditions minute-by-minute for the next hour
                    forecastObject = jsonObject.getJSONObject("minutely");
                    MinutelyForecast minutelyForecast = MinutelyForecast.getInstance();
                    minutelyForecast.setSummary(forecastObject.getString("summary"));
                    minutelyForecast.setIcon(forecastObject.getString("icon"));
                    minutelyForecast.setData(forecastObject.getJSONArray("data"));
                    Log.d(TAG, "minutely");
                    return minutelyForecast;
                case 2: // Weather conditions hour-by-hour for the next two days
                    forecastObject = jsonObject.getJSONObject("hourly");
                    HourlyForecast hourlyForecast = HourlyForecast.getInstance();
                    hourlyForecast.setSummary(forecastObject.getString("summary"));
                    hourlyForecast.setIcon(forecastObject.getString("icon"));
                    hourlyForecast.setData(forecastObject.getJSONArray("data"));
                    Log.d(TAG, "hourly");
                    return hourlyForecast;
                case 3: // Weather conditions day-by-day for the next week
                    forecastObject = jsonObject.getJSONObject("daily");
                    DailyForecast dailyForecast = DailyForecast.getInstance();
                    dailyForecast.setSummary(forecastObject.getString("summary"));
                    dailyForecast.setIcon(forecastObject.getString("icon"));
                    dailyForecast.setData(forecastObject.getJSONArray("data"));
                    Log.d(TAG, "daily");
                    return dailyForecast;
            }

        } catch (IOException ioe) {
            Log.e(TAG, "Failed to connect", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return null;
    }

}
