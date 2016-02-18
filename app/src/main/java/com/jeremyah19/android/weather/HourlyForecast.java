package com.jeremyah19.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class HourlyForecast extends Forecast {
    public static final String TAG = "HourlyForecast";

    private static HourlyForecast mHourlyForecast;

    public static HourlyForecast getInstance() {
        if(mHourlyForecast == null) {
            mHourlyForecast = new HourlyForecast();
        }
        return mHourlyForecast;
    }

    private HourlyForecast() {
    }

    @Override
    public void setData(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                HourlyData hourlyData = new HourlyData();
                hourlyData.setTime(new Date(o.getLong("time")));
                hourlyData.setPrecipIntensity(o.getInt("precipIntensity"));
                hourlyData.setPrecipProbability(o.getInt("precipProbability"));
                hourlyData.setTemperature(o.getDouble("temperature"));
                hourlyData.setApparentTemperature(o.getDouble("apparentTemperature"));
                hourlyData.setHumidity(o.getDouble("humidity"));
                addToDataArrayList(i, hourlyData);
            }
        } catch(JSONException je) {
            Log.e(TAG, "Unable to parse JSON", je);
        }
    }

    private class HourlyData extends Forecast.Data {
        private double mTemperature;
        private double mApparentTemperature;
        private double mHumidity;

        protected double getTemperature() {
            return mTemperature;
        }

        protected void setTemperature(double temperature) {
            mTemperature = temperature;
        }

        protected double getApparentTemperature() {
            return mApparentTemperature;
        }

        protected void setApparentTemperature(double apparentTemperature) {
            mApparentTemperature = apparentTemperature;
        }

        protected double getHumidity() {
            return mHumidity;
        }

        protected void setHumidity(double humidity) {
            mHumidity = humidity;
        }
    }
}
