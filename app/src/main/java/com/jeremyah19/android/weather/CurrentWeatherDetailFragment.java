package com.jeremyah19.android.weather;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

public class CurrentWeatherDetailFragment extends Fragment {
    public static final String TAG = "CurrentWeatherDetailFragment";

    private static final String ARG_CITY = "city";
    private static final String ARG_SUMMARY = "summary";
    private static final String ARG_TEMPERATURE = "temperature";
    private static final String ARG_APPARENT_TEMPERATURE = "apparentTemperature";
    private static final String ARG_ICON = "icon";
    private static final String ARG_PRECIP_PROB = "precipitationProbability";
    private static final String ARG_PRECIP_INTENSITY = "precipitationIntensity";
    private static final String ARG_HUMIDITY = "humidity";
    private static final String ARG_WIND_SPEED = "windSpeed";
    private static final String ARG_WIND_BEARING = "windBearing";
    private static final String ARG_CLOUD_COVER = "cloudCover";
    private static final String ARG_DEW_POINT = "dewPoint";
    private static final String ARG_PRESSURE = "pressure";
    private static final String ARG_VISIBILITY = "visibility";

    public static CurrentWeatherDetailFragment newInstance(String summary, String icon,
                                                           double temperature, double apparentTemperature,
                                                           double humidity, double windSpeed,
                                                           int windBearing, double visibility,
                                                           double dewPoint, double cloudCover,
                                                           double pressure, double precipProbability,
                                                           double precipIntensity) {
        Bundle args = new Bundle();
        args.putString(ARG_SUMMARY, summary);
        args.putString(ARG_ICON, icon);
        args.putDouble(ARG_TEMPERATURE, temperature);
        args.putDouble(ARG_APPARENT_TEMPERATURE, apparentTemperature);
        args.putDouble(ARG_HUMIDITY, humidity);
        args.putDouble(ARG_WIND_SPEED, windSpeed);
        args.putInt(ARG_WIND_BEARING, windBearing);
        args.putDouble(ARG_VISIBILITY, visibility);
        args.putDouble(ARG_DEW_POINT, dewPoint);
        args.putDouble(ARG_CLOUD_COVER, cloudCover);
        args.putDouble(ARG_PRESSURE, pressure);
        args.putDouble(ARG_PRECIP_PROB, precipProbability);
        args.putDouble(ARG_PRECIP_INTENSITY, precipIntensity);

        CurrentWeatherDetailFragment fragment = new CurrentWeatherDetailFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        long temperature = Math.round(getArguments().getDouble(ARG_TEMPERATURE));
        long apparentTemperature = Math.round(getArguments().getDouble(ARG_APPARENT_TEMPERATURE));
        long dewPoint = Math.round(getArguments().getDouble(ARG_DEW_POINT));
        long humidity = Math.round(getArguments().getDouble(ARG_HUMIDITY) * 100);
        long windSpeed = Math.round(getArguments().getDouble(ARG_WIND_SPEED));
        long cloudCover = Math.round(getArguments().getDouble(ARG_CLOUD_COVER) * 100);
        long precipProbability = Math.round(getArguments().getDouble(ARG_PRECIP_PROB) * 100);

        double pressure = getArguments().getDouble(ARG_PRESSURE);
        double visibility = getArguments().getDouble(ARG_VISIBILITY);
        double precipIntensity = getArguments().getDouble(ARG_PRECIP_INTENSITY);

        String summary = getArguments().getString(ARG_SUMMARY);
        String icon = getArguments().getString(ARG_ICON);
        String windBearing = getWindBearingString((double) getArguments().getInt(ARG_WIND_BEARING));

        View v = inflater.inflate(R.layout.fragment_weather_detail, container, false);

        TextView summaryTextView = (TextView) v.findViewById(R.id.details_summary_text_view);
        summaryTextView.setText(summary);

        TextView temperatureTextView = (TextView) v.findViewById(R.id.details_temperature_text_view);
        temperatureTextView.setText(getString(R.string.temperature, temperature));

        TextView apparentTemperatureTextView = (TextView) v.findViewById(R.id.details_apparent_temperature_text_view);
        apparentTemperatureTextView.setText(getString(R.string.apparent_temperature_2, apparentTemperature));

        TextView humidityTextView = (TextView) v.findViewById(R.id.details_humidity_text_view);
        humidityTextView.setText(getString(R.string.humidity, humidity));

        TextView precipProbTextView = (TextView) v.findViewById(R.id.details_precip_probabability_text_view);
        precipProbTextView.setText(getString(R.string.humidity, precipProbability));

        TextView precipIntensityTextView = (TextView) v.findViewById(R.id.details_precip_intensity_text_view);
        precipIntensityTextView.setText(getString(R.string.precipitation_intensity, new DecimalFormat("#.##").format(precipIntensity)));

        TextView windSpeedTextView = (TextView) v.findViewById(R.id.details_wind_speed_text_view);
        windSpeedTextView.setText(getString(R.string.wind_speed, windSpeed));

        TextView windBearingTextView = (TextView) v.findViewById(R.id.details_wind_bearing_text_view);
        if(windSpeed == 0) {
            windBearingTextView.setText(getString(R.string.not_applicable));
        } else {
            windBearingTextView.setText(windBearing);
        }

        TextView visibilityTextView = (TextView) v.findViewById(R.id.details_visibility_text_view);
        visibilityTextView.setText(getString(R.string.visibility, visibility));

        TextView dewPointTextView = (TextView) v.findViewById(R.id.details_dew_point_text_view);
        dewPointTextView.setText(getString(R.string.temperature, dewPoint));

        TextView cloudCoverTextView = (TextView) v.findViewById(R.id.details_cloud_cover_text_view);
        cloudCoverTextView.setText(getString(R.string.humidity, cloudCover));

        TextView pressureTextView = (TextView) v.findViewById(R.id.details_pressure_text_view);
        pressureTextView.setText(getString(R.string.pressure, new DecimalFormat("#.##").format(pressure)));

        ImageView iconImageView = (ImageView) v.findViewById(R.id.details_icon_image_view);
        iconImageView.setImageDrawable(ForecastApiUtils.getIconDrawable(getActivity(), icon));

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    private String getWindBearingString(double windBearing) {
        if(11.25 <= windBearing && windBearing < 33.75) {
            return "NNE";
        } else if(33.75 <= windBearing && windBearing < 56.25) {
            return "NE";
        } else if(56.25 <= windBearing && windBearing < 78.75) {
            return "ENE";
        } else if(78.75 <= windBearing && windBearing < 101.25) {
            return "E";
        } else if(101.25 <= windBearing && windBearing < 123.75) {
            return "ESE";
        } else if(123.75 <= windBearing && windBearing < 146.25) {
            return "SE";
        } else if(146.25 <= windBearing && windBearing < 168.75) {
            return "SSE";
        } else if(168.75 <= windBearing && windBearing < 191.25) {
            return "S";
        } else if(191.25 <= windBearing && windBearing < 213.75) {
            return "SSW";
        } else if(213.75 <= windBearing && windBearing < 236.25) {
            return "SW";
        } else if(236.25 <= windBearing && windBearing < 258.75) {
            return "WSW";
        } else if(258.75 <= windBearing && windBearing < 281.25) {
            return "W";
        } else if(281.25 <= windBearing && windBearing < 303.75) {
            return "WNW";
        } else if(303.75 <= windBearing && windBearing < 326.25) {
            return "NW";
        } else if(326.25 <= windBearing && windBearing < 348.75) {
            return "NNW";
        } else {
            return "N";
        }
    }

}
