package com.jeremyah19.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

// Class representation of JSON forecast data
public class ForecastInfo {
    public static final String TAG = "ForecastInfo";

    public static final int CURRENTLY = 0;
    public static final int MINUTELY = 1;
    public static final int HOURLY = 2;
    public static final int DAILY = 3;

    private static final int SIZE_MINUTELY_DATA = 61;
    private static final int SIZE_HOURLY_DATA = 49;
    private static final int SIZE_DAILY_DATA = 8;

    private static ForecastInfo mForecastInfo;

    private boolean mHasMinutelyData;

    private double  mOffset;

    private String mTimezone;

    private Currently mCurrently;
    private Minutely mMinutely;
    private Hourly mHourly;
    private Daily mDaily;

    public static ForecastInfo getInstance() {
        if(mForecastInfo == null) {
            mForecastInfo = new ForecastInfo();
        }
        return mForecastInfo;
    }

    public boolean hasMinutelyData() {
        return mHasMinutelyData;
    }

    public void setHasMinutelyData(boolean hasMinutelyData) {
        mHasMinutelyData = hasMinutelyData;
    }

    public double getOffset() {
        return mOffset;
    }

    public void setOffset(double offset) {
        mOffset = offset;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    public Date getCurrentlyTime() {
        return mCurrently.mTime;
    }

    public void setCurrentlyTime(Date time) {
        mCurrently.mTime = time;
    }

    public double getCurrentlyPrecipIntensity() {
        return mCurrently.mPrecipIntensity;
    }

    public void setCurrentlyPrecipIntensity(double precipIntensity) {
        mCurrently.mPrecipIntensity = precipIntensity;
    }

    public double getCurrentlyPrecipProbability() {
        return mCurrently.mPrecipProbability;
    }

    public void setCurrentlyPrecipProbability(double precipProbability) {
        mCurrently.mPrecipProbability = precipProbability;
    }

    public double getCurrentlyTemperature() {
        return mCurrently.mTemperature;
    }

    public void setCurrentlyTemperature(double temperature) {
        mCurrently.mTemperature = temperature;
    }

    public double getCurrentlyApparentTemperature() {
        return mCurrently.mApparentTemperature;
    }

    public void setCurrentlyApparentTemperature(double apparentTemperature) {
        mCurrently.mApparentTemperature = apparentTemperature;
    }

    public double getCurrentlyHumidity() {
        return mCurrently.mHumidity;
    }

    public void setCurrentlyHumidity(double humidity) {
        mCurrently.mHumidity = humidity;
    }

    public String getSummary(int timePeriod) {
        String summary = null;
        switch(timePeriod) {
            case CURRENTLY:
                summary = mCurrently.mSummary;
                break;
            case MINUTELY:
                summary = mMinutely.mSummary;
                break;
            case HOURLY:
                summary = mHourly.mSummary;
                break;
            case DAILY:
                summary = mDaily.mSummary;
        }
        return summary;
    }

    public void setSummaries(String cSummary, String mSummary, String hSummary, String dSummary) {
        mCurrently.mSummary = cSummary;
        mMinutely.mSummary = mSummary;
        mHourly.mSummary = hSummary;
        mDaily.mSummary = dSummary;
    }

    // In case there is no minute-by-minute data in forecast
    public void setSummaries(String cSummary, String hSummary, String dSummary) {
        mCurrently.mSummary = cSummary;
        mHourly.mSummary = hSummary;
        mDaily.mSummary = dSummary;
    }

    public String getIcon(int timePeriod) {
        String icon = null;
        switch(timePeriod) {
            case CURRENTLY:
                icon = mCurrently.mIcon;
                break;
            case MINUTELY:
                icon = mMinutely.mIcon;
                break;
            case HOURLY:
                icon = mHourly.mIcon;
                break;
            case DAILY:
                icon = mDaily.mIcon;
        }
        return icon;
    }

    public void setIcons(String cIcon, String mIcon, String hIcon, String dIcon) {
        mCurrently.mIcon = cIcon;
        mMinutely.mIcon = mIcon;
        mHourly.mIcon = hIcon;
        mDaily.mIcon = dIcon;
    }

    // In case there is no minute-by-minute data in forecast
    public void setIcons(String cIcon, String hIcon, String dIcon) {
        mCurrently.mIcon = cIcon;
        mHourly.mIcon = hIcon;
        mDaily.mIcon = dIcon;
    }

    public double getDataPrecipIntensity(int timePeriod, int index) throws ForecastInfoException {
        double precipIntensity = 0.0;
        switch(timePeriod) {
            case CURRENTLY:
                throw new ForecastInfoException(
                        "getDataPrecipIntensity cannot use ForecastInfo.CURRENTLY time period"
                );
            case MINUTELY:
                precipIntensity = mMinutely.mData.get(index).mPrecipIntensity;
                break;
            case HOURLY:
                precipIntensity = mHourly.mData.get(index).mPrecipIntensity;
                break;
            case DAILY:
                precipIntensity = mDaily.mData.get(index).mPrecipIntensity;
        }
        return precipIntensity;
    }

    public double getDataPrecipProbability(int timePeriod, int index) throws ForecastInfoException {
        double precipProbability = 0.0;
        switch(timePeriod) {
            case CURRENTLY:
                throw new ForecastInfoException(
                        "getDataPrecipProbability cannot use ForecastInfo.CURRENTLY time period"
                );
            case MINUTELY:
                precipProbability = mMinutely.mData.get(index).mPrecipProbability;
                break;
            case HOURLY:
                precipProbability = mHourly.mData.get(index).mPrecipProbability;
                break;
            case DAILY:
                precipProbability = mDaily.mData.get(index).mPrecipProbability;
        }
        return precipProbability;
    }

    public Date getDataTime(int timePeriod, int index) throws ForecastInfoException {
        Date time = null;
        switch(timePeriod) {
            case CURRENTLY:
                throw new ForecastInfoException(
                        "getDataTime cannot use ForecastInfo.CURRENTLY time period"
                );
            case MINUTELY:
                time = mMinutely.mData.get(index).mTime;
                break;
            case HOURLY:
                time = mHourly.mData.get(index).mTime;
                break;
            case DAILY:
                time = mDaily.mData.get(index).mTime;
        }
        return time;
    }

    public double getDataHourlyTemperature(int index) {
        return mHourly.mData.get(index).mTemperature;
    }

    public double getDataHourlyApparentTemperature(int index) {
        return mHourly.mData.get(index).mApparentTemperature;
    }

    public double getDataDailyTemperatureMin(int index) {
        return mDaily.mData.get(index).mTemperatureMin;
    }

    public double getDataDailyTemperatureMax(int index) {
        return mDaily.mData.get(index).mTemperatureMax;
    }

    public double getDataDailyApparentTemperatureMin(int index) {
        return mDaily.mData.get(index).mApparentTemperatureMin;
    }

    public double getDataDailyApparentTemperatureMax(int index) {
        return mDaily.mData.get(index).mApparentTemperatureMax;
    }

    public double getDataHumidity(int timePeriod, int index) throws ForecastInfoException{
        double humidity = 0.0;
        switch(timePeriod) {
            case CURRENTLY:
            case MINUTELY:
                throw new ForecastInfoException(
                        "getDataHumidity cannot use ForecastInfo.CURRENTLY or ForecastInfo.MINUTELY time periods"
                );
            case HOURLY:
                humidity = mHourly.mData.get(index).mHumidity;
                break;
            case DAILY:
                humidity = mDaily.mData.get(index).mHumidity;
        }
        return humidity;
    }

    public String getDataSummary(int timePeriod, int index) throws ForecastInfoException{
        String summary = null;
        switch(timePeriod) {
            case CURRENTLY:
            case MINUTELY:
                throw new ForecastInfoException(
                        "getDataSummary cannot use ForecastInfo.CURRENTLY or ForecastInfo.MINUTELY time periods"
                );
            case HOURLY:
                summary = mHourly.mData.get(index).mSummary;
                break;
            case DAILY:
                summary = mDaily.mData.get(index).mSummary;
        }
        return summary;
    }

    public String getDataIcon(int timePeriod, int index) throws ForecastInfoException{
        String icon = null;
        switch(timePeriod) {
            case CURRENTLY:
            case MINUTELY:
                throw new ForecastInfoException(
                        "getDataIcon cannot use ForecastInfo.CURRENTLY or ForecastInfo.MINUTELY time periods"
                );
            case HOURLY:
                icon = mHourly.mData.get(index).mIcon;
                break;
            case DAILY:
                icon = mDaily.mData.get(index).mIcon;
        }
        return icon;
    }

    public void setData(JSONArray m, JSONArray h, JSONArray d) {
        try {
            // Set minute-by-minute forecast data
            for (int i = 0; i < m.length(); i++) {
                JSONObject o = m.getJSONObject(i);
                mMinutely.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                mMinutely.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                mMinutely.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
            }

            // Set hour-by-hour forecast data
            for (int i = 0; i < h.length(); i++) {
                JSONObject o = h.getJSONObject(i);
                mHourly.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                mHourly.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                mHourly.mData.get(i).mTemperature = o.getDouble("temperature");
                mHourly.mData.get(i).mApparentTemperature = o.getDouble("apparentTemperature");
                mHourly.mData.get(i).mHumidity = o.getDouble("humidity");
                mHourly.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
                mHourly.mData.get(i).mSummary = o.getString("summary");
                mHourly.mData.get(i).mIcon = o.getString("icon");
            }

            // Set day-by-day forecast data
            for (int i = 0; i < d.length(); i++) {
                JSONObject o = d.getJSONObject(i);
                mDaily.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                mDaily.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                mDaily.mData.get(i).mTemperatureMin = o.getDouble("temperatureMin");
                mDaily.mData.get(i).mTemperatureMax = o.getDouble("temperatureMax");
                mDaily.mData.get(i).mApparentTemperatureMin = o.getDouble("apparentTemperatureMin");
                mDaily.mData.get(i).mApparentTemperatureMax = o.getDouble("apparentTemperatureMax");
                mDaily.mData.get(i).mHumidity = o.getDouble("humidity");
                mDaily.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
                mDaily.mData.get(i).mSummary = o.getString("summary");
                mDaily.mData.get(i).mIcon = o.getString("icon");
            }

        } catch(JSONException je) {
            Log.e(TAG, "Cannot parse JSON", je);
        }
    }

    // In case there is no minute-by-minute data in forecast
    public void setData(JSONArray h, JSONArray d) {
        try {
            // Set hour-by-hour forecast data
            for (int i = 0; i < h.length(); i++) {
                JSONObject o = h.getJSONObject(i);
                mHourly.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                mHourly.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                mHourly.mData.get(i).mTemperature = o.getDouble("temperature");
                mHourly.mData.get(i).mApparentTemperature = o.getDouble("apparentTemperature");
                mHourly.mData.get(i).mHumidity = o.getDouble("humidity");
                mHourly.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
                mHourly.mData.get(i).mSummary = o.getString("summary");
                mHourly.mData.get(i).mIcon = o.getString("icon");
            }

            // Set day-by-day forecast data
            for (int i = 0; i < d.length(); i++) {
                JSONObject o = d.getJSONObject(i);
                mDaily.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                mDaily.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                mDaily.mData.get(i).mTemperatureMin = o.getDouble("temperatureMin");
                mDaily.mData.get(i).mTemperatureMax = o.getDouble("temperatureMax");
                mDaily.mData.get(i).mApparentTemperatureMin = o.getDouble("apparentTemperatureMin");
                mDaily.mData.get(i).mApparentTemperatureMax = o.getDouble("apparentTemperatureMax");
                mDaily.mData.get(i).mHumidity = o.getDouble("humidity");
                mDaily.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
                mDaily.mData.get(i).mSummary = o.getString("summary");
                mDaily.mData.get(i).mIcon = o.getString("icon");
            }

        } catch(JSONException je) {
            Log.e(TAG, "Cannot parse JSON", je);
        }
    }

    private ForecastInfo() {
        mCurrently = new Currently();
        mMinutely = new Minutely();
        mHourly = new Hourly();
        mDaily = new Daily();
        Log.d(TAG, "ForecastInfo Constructor - mMinutely.mData.size() = " + mMinutely.mData.size());
    }

    private class Currently {
        private double mPrecipIntensity;
        private double mPrecipProbability;
        private double mTemperature;
        private double mApparentTemperature;
        private double mHumidity;

        private String mSummary;
        private String mIcon;

        private Date mTime;
    }

    private class Minutely {
        private String mSummary;
        private String mIcon;

        private ArrayList<Data> mData;

        private Minutely() {
            mData = new ArrayList<>();
            for(int i = 0; i < SIZE_MINUTELY_DATA; i++) {
                mData.add(i, new Data());
            }
            Log.d(TAG, "Minutely Constructor - mData.size() = " + mData.size());
        }

        private class Data {
            private double mPrecipIntensity;
            private double mPrecipProbability;

            private Date mTime;
        }
    }

    private class Hourly {
        private String mSummary;
        private String mIcon;

        private ArrayList<Data> mData;

        private Hourly() {
            mData = new ArrayList<>();
            for(int i = 0; i < SIZE_HOURLY_DATA; i++) {
                mData.add(i, new Data());
            }
        }

        private class Data {
            private double mPrecipIntensity;
            private double mPrecipProbability;
            private double mTemperature;
            private double mApparentTemperature;
            private double mHumidity;

            private String mSummary;
            private String mIcon;

            private Date mTime;
        }
    }

    private class Daily {
        private String mSummary;
        private String mIcon;

        private ArrayList<Data> mData;

        private Daily() {
            mData = new ArrayList<>();
            for(int i = 0; i < SIZE_DAILY_DATA; i++) {
                mData.add(i, new Data());
            }
        }

        private class Data {
            private double mPrecipIntensity;
            private double mPrecipProbability;
            private double mTemperatureMin;
            private double mTemperatureMax;
            private double mApparentTemperatureMin;
            private double mApparentTemperatureMax;
            private double mHumidity;

            private String mSummary;
            private String mIcon;

            private Date mTime;
        }
    }
}
