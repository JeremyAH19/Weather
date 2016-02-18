package com.jeremyah19.android.weather;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;

public abstract class Forecast {
    public static final String TAG = "Forecast";

    private String mSummary;
    private String mIcon;

    private ArrayList<Data> mDataArrayList = new ArrayList<>();

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public ArrayList<Data> getData() {
        return mDataArrayList;
    }

    public abstract void setData(JSONArray array);

    protected void addToDataArrayList(int index, Data data) {
        mDataArrayList.add(index, data);
    }

    protected class Data {

        private Date mTime;

        private int mPrecipIntensity;
        private int mPrecipProbability;

        protected Date getTime() {
            return mTime;
        }

        protected void setTime(Date time) {
            mTime = time;
        }

        protected int getPrecipIntensity() {
            return mPrecipIntensity;
        }

        protected void setPrecipIntensity(int precipIntensity) {
            mPrecipIntensity = precipIntensity;
        }

        protected int getPrecipProbability() {
            return mPrecipProbability;
        }

        protected void setPrecipProbability(int precipProbability) {
            mPrecipProbability = precipProbability;
        }
    }
}

