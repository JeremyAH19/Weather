package com.jeremyah19.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MinutelyForecast extends Forecast {
    public static final String TAG = "MinutelyForecast";

    private static MinutelyForecast mMinutelyForecast;

    public static MinutelyForecast getInstance() {
        if(mMinutelyForecast == null) {
            mMinutelyForecast = new MinutelyForecast();
        }
        return mMinutelyForecast;
    }

    private MinutelyForecast() {
    }

    @Override
    public void setData(JSONArray array) {
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                Data data = new Data();
                data.setTime(new Date(o.getLong("time")));
                data.setPrecipIntensity(o.getInt("precipIntensity"));
                data.setPrecipProbability(o.getInt("precipProbability"));
                addToDataArrayList(i, data);
            }
        } catch(JSONException je) {
            Log.e(TAG, "Unable to parse JSON", je);
        }
    }
}
