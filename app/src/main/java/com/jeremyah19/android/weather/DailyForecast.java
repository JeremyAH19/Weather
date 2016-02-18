package com.jeremyah19.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DailyForecast extends Forecast {
    public static final String TAG = "DailyForecast";

    private static DailyForecast mDailyForecast;

    public static DailyForecast getInstance() {
        if(mDailyForecast == null) {
            mDailyForecast = new DailyForecast();
        }
        return mDailyForecast;
    }

    private DailyForecast() {
    }

    @Override
    public void setData(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                DailyData dailyData = new DailyData();
                dailyData.setTime(new Date(o.getLong("time")));
                dailyData.setPrecipIntensity(o.getInt("precipIntensity"));
                dailyData.setPrecipProbability(o.getInt("precipProbability"));
                dailyData.setTemperature(o.getDouble("temperature"));
                dailyData.setApparentTemperature(o.getDouble("apparentTemperature"));
                dailyData.setHumidity(o.getDouble("humidity"));
                addToDataArrayList(i, dailyData);
            }
        } catch(JSONException je) {
            Log.e(TAG, "Unable to parse JSON", je);
        }
    }

    private class DailyData extends Forecast.Data {
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
