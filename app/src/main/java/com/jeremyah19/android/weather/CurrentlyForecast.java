package com.jeremyah19.android.weather;

import org.json.JSONArray;

import java.util.Date;


public class CurrentlyForecast extends Forecast {
    public static final String TAG = "CurrentlyForecast";

    private static CurrentlyForecast mCurrentlyForecast;

    private Date mTime;

    private int mPrecipIntensity;
    private int mPrecipProbability;

    private double mTemperature;
    private double mApparentTemperature;
    private double mHumidity;

    public static CurrentlyForecast getInstance() {
        if(mCurrentlyForecast == null) {
            mCurrentlyForecast = new CurrentlyForecast();
        }
        return mCurrentlyForecast;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }

    public int getPrecipIntensity() {
        return mPrecipIntensity;
    }

    public void setPrecipIntensity(int precipIntensity) {
        mPrecipIntensity = precipIntensity;
    }

    public int getPrecipProbability() {
        return mPrecipProbability;
    }

    public void setPrecipProbability(int precipProbability) {
        mPrecipProbability = precipProbability;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getApparentTemperature() {
        return mApparentTemperature;
    }

    public void setApparentTemperature(double apparentTemperature) {
        mApparentTemperature = apparentTemperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    private CurrentlyForecast() {
    }

    @Override
    public void setData(JSONArray array) {

    }

}
