package com.jeremyah19.android.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WeatherFragment extends Fragment {

    public static final String TAG = "WeatherFragment";

    private double mLatitude;
    private double mLongitude;

    private String mCityName;

    private TextView mCityTextView;
    private TextView mSummaryTextView;
    private TextView mTemperatureTextView;
    private TextView mApparentTemperatureTextView;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        mCityTextView = (TextView) v.findViewById(R.id.location_city);
        mSummaryTextView = (TextView) v.findViewById(R.id.currently_summary);
        mTemperatureTextView = (TextView) v.findViewById(R.id.currently_temperature);
        mApparentTemperatureTextView =
                (TextView) v.findViewById(R.id.currently_apparent_temperature);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.menu_weather, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                new GeocodingTask().execute(query.replace(" ", ""));
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return super.onOptionsItemSelected(menuItem);
    }

    private class GeocodingTask extends AsyncTask<String, Void, Bundle> {
        @Override
        protected Bundle doInBackground(String... params) {
            return GeocodingUtils.getLocationInfo(params[0]);
        }

        @Override
        protected void onPostExecute(Bundle info) {
            mCityName = info.getString(GeocodingUtils.BUNDLE_KEY_CITY);
            mLatitude = info.getDouble(GeocodingUtils.BUNDLE_KEY_LATITUDE);
            mLongitude = info.getDouble(GeocodingUtils.BUNDLE_KEY_LONGITUDE);

            mCityTextView.setText(mCityName);

            new FetchWeatherTask().execute(mLatitude, mLongitude, 0.0);

            Log.i(TAG, "City: " + mCityName);
            Log.i(TAG, "Latitude: " + mLatitude);
            Log.i(TAG, "Longitude: " + mLongitude);
        }
    }

    private class FetchWeatherTask extends AsyncTask<Double, Void, CurrentlyForecast> {
        @Override
        protected CurrentlyForecast doInBackground(Double... params) {
            return (CurrentlyForecast)ForecastApiUtils.getForecast(params[0], params[1], params[2]);

        }

        @Override
        protected void onPostExecute(CurrentlyForecast forecast) {
            mSummaryTextView.setText(forecast.getSummary());
            mTemperatureTextView.setText(
                    getString(R.string.temperature, forecast.getTemperature()));
            mApparentTemperatureTextView.setText(
                    getString(R.string.apparent_temperature, forecast.getApparentTemperature()));

            mCityTextView.setText(mCityName);

            Log.i(TAG, "City: " + mCityName);
            Log.i(TAG, "Latitude: " + mLatitude);
            Log.i(TAG, "Longitude: " + mLongitude);
        }
    }
}
