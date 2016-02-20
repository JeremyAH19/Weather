package com.jeremyah19.android.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class WeatherFragment extends Fragment {

    public static final String TAG = "WeatherFragment";

    private double mLatitude;
    private double mLongitude;

    private String mCityName;

    private CurrentWeatherView mCurrentWeatherView;

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

        mCurrentWeatherView = (CurrentWeatherView) v.findViewById(R.id.current_weather_view);
        mCurrentWeatherView.setVisibility(View.INVISIBLE);

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

            ((AppCompatActivity) getActivity())
                    .getSupportActionBar()
                    .setTitle(mCityName);

            new FetchWeatherTask().execute(mLatitude, mLongitude, 0.0);

            Log.i(TAG, "City: " + mCityName);
            Log.i(TAG, "Latitude: " + mLatitude);
            Log.i(TAG, "Longitude: " + mLongitude);
        }
    }

    private class FetchWeatherTask extends AsyncTask<Double, Void, ForecastInfo> {
        @Override
        protected ForecastInfo doInBackground(Double... params) {
            return ForecastApiUtils.getForecastInfo(params[0], params[1]);

        }

        @Override
        protected void onPostExecute(ForecastInfo forecastInfo) {
            mCurrentWeatherView.setVisibility(View.VISIBLE);

            Log.d(TAG, forecastInfo.getIcon(ForecastInfo.CURRENTLY));
            switch(forecastInfo.getIcon(ForecastInfo.CURRENTLY)) {
                case "clear-day":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_clear_day);
                    Log.d(TAG, "clear-day");
                    break;
                case "clear-night":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_clear_night);
                    Log.d(TAG, "clear_night");
                    break;
                case "rain":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_rain);
                    Log.d(TAG, "rain");
                    break;
                case "snow":
                case "hail":
                case "sleet":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_snow);
                    Log.d(TAG, "snow");
                    break;
                case "wind":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_wind);
                    Log.d(TAG, "wind");
                    break;
                case "fog":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_fog);
                    Log.d(TAG, "fog");
                    break;
                case "cloudy":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_cloudy);
                    Log.d(TAG, "cloudy");
                    break;
                case "partly-cloudy-day":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_partly_cloudy_day);
                    Log.d(TAG, "partly-cloudy-day");
                    break;
                case "partly-cloudy-night":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_partly_cloudy_night);
                    Log.d(TAG, "partly-cloudy-night");
                    break;
                case "thunderstorm":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_thunderstorm);
                    Log.d(TAG, "thunderstorm");
                    break;
                case "tornado":
                    mCurrentWeatherView.setIconSrc(R.drawable.ic_tornado);
                    Log.d(TAG, "tornado");
                    break;
            }

            mCurrentWeatherView.setSummaryText(forecastInfo.getSummary(ForecastInfo.CURRENTLY));
            mCurrentWeatherView.setTemperatureText(getString(
                    R.string.temperature,
                    forecastInfo.getCurrentlyTemperature()));

            mCurrentWeatherView.setApparentTemperatureText(getString(
                    R.string.apparent_temperature,
                    forecastInfo.getCurrentlyApparentTemperature()));

            mCurrentWeatherView.setHumidityText(getString(
                    R.string.humidity,
                    forecastInfo.getCurrentlyHumidity() * 100));
        }
    }
}
