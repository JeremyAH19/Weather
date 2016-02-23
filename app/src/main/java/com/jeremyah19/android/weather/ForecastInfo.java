package com.jeremyah19.android.weather;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

// Class representation of JSON forecast data
public class ForecastInfo implements Serializable{
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

    public double getCurrentlyDewPoint() {
        return mCurrently.mDewPoint;
    }

    public void setCurrentlyDewPoint(double dewPoint) {
        mCurrently.mDewPoint = dewPoint;
    }

    public double getCurrentlyPressure() {
        return mCurrently.mPressure;
    }

    // Takes pressure in millibars and converts to in-Hg
    public void setCurrentlyPressure(double pressure) {
        mCurrently.mPressure = pressure * 0.02953;
    }

    public double getCurrentlyWindSpeed() {
        return mCurrently.mWindSpeed;
    }

    public void setCurrentlyWindSpeed(double windSpeed) {
        mCurrently.mWindSpeed = windSpeed;
    }

    public double getCurrentlyCloudCover() {
        return mCurrently.mCloudCover;
    }

    public void setCurrentlyCloudCover(double cloudCover) {
        mCurrently.mCloudCover = cloudCover;
    }

    public double getCurrentlyVisibility() {
        return mCurrently.mVisibility;
    }

    public void setCurrentlyVisibility(double visibility) {
        mCurrently.mVisibility = visibility;
    }

    public int getCurrentlyWindBearing() {
        return mCurrently.mWindBearing;
    }

    public void setCurrentlyWindBearing(int windBearing) {
        mCurrently.mWindBearing = windBearing;
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

    public ArrayList<Minutely.Data> getMinutelyData() {
        return mMinutely.mData;
    }

    public ArrayList<Hourly.Data> getHourlyData() {
        return mHourly.mData;
    }

    public ArrayList<Daily.Data> getDailyData() {
        return mDaily.mData;
    }

    public void setData(JSONArray m, JSONArray h, JSONArray d) {
        try {
            // Set minute-by-minute forecast data
            if(m != null) {
                for (int i = 0; i < m.length(); i++) {
                    JSONObject o = m.getJSONObject(i);
                    mMinutely.mData.get(i).mPrecipIntensity = o.getDouble("precipIntensity");
                    mMinutely.mData.get(i).mPrecipProbability = o.getDouble("precipProbability");
                    mMinutely.mData.get(i).mTime = new Date(o.getLong("time") * 1000);
                }
            }

            // Set hour-by-hour forecast data
            if(h != null) {
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
            }

            // Set day-by-day forecast data
            if(d != null) {
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
    }

    private class Currently implements Serializable{
        private double mPrecipIntensity;
        private double mPrecipProbability;
        private double mTemperature;
        private double mApparentTemperature;
        private double mHumidity;
        private double mDewPoint;
        private double mPressure;
        private double mWindSpeed;
        private double mCloudCover;
        private double mVisibility;

        private int mWindBearing;

        private String mSummary;
        private String mIcon;

        private Date mTime;
    }

    private class Minutely implements Serializable{
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

        private class Data implements Serializable{
            private double mPrecipIntensity;
            private double mPrecipProbability;

            private Date mTime;
        }
    }

    public class Hourly implements Serializable{
        private String mSummary;
        private String mIcon;

        private ArrayList<Data> mData;

        private Hourly() {
            mData = new ArrayList<>();
            for(int i = 0; i < SIZE_HOURLY_DATA; i++) {
                mData.add(i, new Data());
            }
            Log.d(TAG, "Hourly Constructor - mData.size() = " + mData.size());
        }

        public class Data implements Serializable{
            private double mPrecipIntensity;
            private double mPrecipProbability;
            private double mTemperature;
            private double mApparentTemperature;
            private double mHumidity;

            private String mSummary;
            private String mIcon;

            private Date mTime;

            public double getTemperature() {
                return mTemperature;
            }

            public String getSummary() {
                return mSummary;
            }

            public String getIcon() {
                return mIcon;
            }

            public Date getTime() {
                return mTime;
            }
        }
    }

    public class Daily implements Serializable{
        private String mSummary;
        private String mIcon;

        private ArrayList<Data> mData;

        public Daily getInstance() {
            return mDaily;
        }

        private Daily() {
            mData = new ArrayList<>();
            for(int i = 0; i < SIZE_DAILY_DATA; i++) {
                mData.add(i, new Data());
            }
            Log.d(TAG, "Daily Constructor - mData.size() = " + mData.size());
        }

        public class Data implements Serializable{
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

            public double getTemperatureMin() {
                return mTemperatureMin;
            }

            public double getTemperatureMax() {
                return mTemperatureMax;
            }

            public double getHumidity() {
                return mHumidity;
            }

            public String getSummary() {
                return mSummary;
            }

            public String getIcon() {
                return mIcon;
            }

            public Date getTime() {
                return mTime;
            }
        }
    }
}
