package com.jeremyah19.android.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

public class CurrentWeatherActivity extends SingleFragmentActivity {

    private static final String EXTRA_CITY = "com.jeremyah19.android.weather.city";
    private static final String EXTRA_SUMMARY = "com.jeremyah19.android.weather.summary";
    private static final String EXTRA_TEMPERATURE = "com.jeremyah19.android.weather.temperature";
    private static final String EXTRA_APPARENT_TEMPERATURE =
            "com.jeremyah19.android.weather.apparentTemperature";
    private static final String EXTRA_ICON = "com.jeremyah19.android.weather.icon";
    private static final String EXTRA_PRECIP_PROB = "com.jeremyah19.android.weather.precipProbability";
    private static final String EXTRA_PRECIP_INTESITY = "com.jeremyah19.android.weather.precipIntensity";
    private static final String EXTRA_HUMIDITY = "com.jeremyah19.android.weather.humidity";
    private static final String EXTRA_WIND_SPEED = "com.jeremyah19.android.weather.windSpeed";
    private static final String EXTRA_WIND_BEARING = "com.jeremyah19.android.weather.windBearing";
    private static final String EXTRA_CLOUD_COVER = "com.jeremyah19.android.weather.cloudCover";
    private static final String EXTRA_DEW_POINT = "com.jeremyah19.android.weather.dewPoint";
    private static final String EXTRA_PRESSURE = "com.jeremyah19.android.weather.pressure";
    private static final String EXTRA_VISIBILITY = "com.jeremyah19.android.weather.visibility";

    private String mCity;

    public static Intent newIntent(Context packageContext, ForecastInfo forecastInfo, String city) {
        Intent intent = new Intent(packageContext, CurrentWeatherActivity.class);
        intent.putExtra(EXTRA_CITY, city);
        intent.putExtra(EXTRA_SUMMARY, forecastInfo.getSummary(ForecastInfo.CURRENTLY));
        intent.putExtra(EXTRA_TEMPERATURE, forecastInfo.getCurrentlyTemperature());
        intent.putExtra(EXTRA_APPARENT_TEMPERATURE, forecastInfo.getCurrentlyApparentTemperature());
        intent.putExtra(EXTRA_ICON, forecastInfo.getIcon(ForecastInfo.CURRENTLY));
        intent.putExtra(EXTRA_PRECIP_PROB, forecastInfo.getCurrentlyPrecipProbability());
        intent.putExtra(EXTRA_PRECIP_INTESITY, forecastInfo.getCurrentlyPrecipIntensity());
        intent.putExtra(EXTRA_HUMIDITY, forecastInfo.getCurrentlyHumidity());
        intent.putExtra(EXTRA_WIND_SPEED, forecastInfo.getCurrentlyWindSpeed());
        intent.putExtra(EXTRA_WIND_BEARING, forecastInfo.getCurrentlyWindBearing());
        intent.putExtra(EXTRA_CLOUD_COVER, forecastInfo.getCurrentlyCloudCover());
        intent.putExtra(EXTRA_DEW_POINT, forecastInfo.getCurrentlyDewPoint());
        intent.putExtra(EXTRA_PRESSURE, forecastInfo.getCurrentlyPressure());
        intent.putExtra(EXTRA_VISIBILITY, forecastInfo.getCurrentlyVisibility());

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String summary = getIntent().getStringExtra(EXTRA_SUMMARY);
        String icon = getIntent().getStringExtra(EXTRA_ICON);

        double temperature = getIntent().getDoubleExtra(EXTRA_TEMPERATURE, 0.0);
        double apparentTemperature = getIntent().getDoubleExtra(EXTRA_APPARENT_TEMPERATURE, 0.0);
        double humidity = getIntent().getDoubleExtra(EXTRA_HUMIDITY, 0.0);
        double windSpeed = getIntent().getDoubleExtra(EXTRA_WIND_SPEED, 0.0);
        double visibility = getIntent().getDoubleExtra(EXTRA_VISIBILITY, 0.0);
        double dewPoint = getIntent().getDoubleExtra(EXTRA_DEW_POINT, 0.0);
        double cloudCover = getIntent().getDoubleExtra(EXTRA_CLOUD_COVER, 0.0);
        double pressure = getIntent().getDoubleExtra(EXTRA_PRESSURE, 0.0);
        double precipProbability = getIntent().getDoubleExtra(EXTRA_PRECIP_PROB, 0.0);
        double precipIntensity = getIntent().getDoubleExtra(EXTRA_PRECIP_INTESITY, 0.0);

        int windBearing = getIntent().getIntExtra(EXTRA_WIND_BEARING, 0);

        mCity = getIntent().getStringExtra(EXTRA_CITY);

        return CurrentWeatherDetailFragment.newInstance(
                summary,
                icon,
                temperature,
                apparentTemperature,
                humidity,
                windSpeed,
                windBearing,
                visibility,
                dewPoint,
                cloudCover,
                pressure,
                precipProbability,
                precipIntensity
        );
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.title_current_conditions));
        toolbar.setSubtitle(mCity);
        setSupportActionBar(toolbar);
    }
}
