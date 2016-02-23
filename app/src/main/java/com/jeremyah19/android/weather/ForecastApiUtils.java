package com.jeremyah19.android.weather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class ForecastApiUtils {
    public static final String TAG = "ForecastApiUtils";

    private static final String API_DOMAIN = "https://api.forecast.io/forecast";

    public static ForecastInfo getForecastInfo(double latitude, double longitude) {
        ForecastInfo forecastInfo = null;
        Uri uri = Uri.parse(API_DOMAIN)
                .buildUpon()
                .appendPath(ApiKeys.FORECAST_API_KEY)
                .appendPath(Double.toString(latitude) + "," + Double.toString(longitude))
                .build();
        Log.d(TAG, "Forecast JSON URL: " + uri.toString());

        try {

            JSONObject jsonObject = new JSONObject(
                    new String(GeocodingUtils.getUrlBytes(uri.toString())));
            Log.i(TAG, "Received JSON: " + jsonObject.toString());

            forecastInfo = ForecastInfo.getInstance();
            forecastInfo.setTimezone(jsonObject.getString("timezone"));
            forecastInfo.setOffset(jsonObject.getDouble("offset"));

            JSONObject cObject = jsonObject.getJSONObject("currently");
            JSONObject hObject = jsonObject.getJSONObject("hourly");
            JSONObject dObject = jsonObject.getJSONObject("daily");
            JSONObject mObject;
            if(jsonObject.isNull("minutely")) {
                mObject = null;
                forecastInfo.setHasMinutelyData(false);
            } else {
                mObject = jsonObject.getJSONObject("minutely");
                forecastInfo.setHasMinutelyData(true);
            }

            // Set current forecast data
            setCurrentForecast(forecastInfo, cObject);

            // Set rest of data for all forecasts
            if(forecastInfo.hasMinutelyData()) {
                forecastInfo.setSummaries(
                        cObject.getString("summary"),
                        mObject.getString("summary"),
                        hObject.getString("summary"),
                        dObject.getString("summary")
                );

                forecastInfo.setIcons(
                        cObject.getString("icon"),
                        mObject.getString("icon"),
                        hObject.getString("icon"),
                        dObject.getString("icon")
                );

                forecastInfo.setData(
                        mObject.getJSONArray("data"),
                        hObject.getJSONArray("data"),
                        dObject.getJSONArray("data")
                );
            } else {
                forecastInfo.setSummaries(
                        cObject.getString("summary"),
                        hObject.getString("summary"),
                        dObject.getString("summary")
                );

                forecastInfo.setIcons(
                        cObject.getString("icon"),
                        hObject.getString("icon"),
                        dObject.getString("icon")
                );

                forecastInfo.setData(
                        hObject.getJSONArray("data"),
                        dObject.getJSONArray("data")
                );
            }
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to connect", ioe);
        } catch (JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
        return forecastInfo;
    }

    public static Drawable getIconDrawable(Context context, String icon) {
        if(Build.VERSION.SDK_INT >= 21) {
            return context.getResources().getDrawable(getIconId(icon), null);
        } else {
            return context.getResources().getDrawable(getIconId(icon));
        }
    }

    // Returns appropriate icon id based on input icon string
    private static int getIconId(String icon) {
        switch(icon) {
            case "clear-day":
                return R.drawable.ic_clear_day;
            case "clear-night":
                return R.drawable.ic_clear_night;
            case "rain":
                return R.drawable.ic_rain;
            case "snow":
            case "hail":
            case "sleet":
                return R.drawable.ic_snow;
            case "wind":
                return R.drawable.ic_wind;
            case "fog":
                return R.drawable.ic_fog;
            case "cloudy":
                return R.drawable.ic_cloudy;
            case "partly-cloudy-day":
                return R.drawable.ic_partly_cloudy_day;
            case "partly-cloudy-night":
                return R.drawable.ic_partly_cloudy_night;
            case "thunderstorm":
                return R.drawable.ic_thunderstorm;
            case "tornado":
                return R.drawable.ic_tornado;
        }
        return 0;
    }

    private static void setCurrentForecast(ForecastInfo info, JSONObject o) {
        try {
            info.setCurrentlyTime(new Date(o.getLong("time") * 1000));
            info.setCurrentlyPrecipIntensity(o.getDouble("precipIntensity"));
            info.setCurrentlyPrecipProbability(o.getDouble("precipProbability"));
            info.setCurrentlyTemperature(o.getDouble("temperature"));
            info.setCurrentlyApparentTemperature(o.getDouble("apparentTemperature"));
            info.setCurrentlyHumidity(o.getDouble("humidity"));
            info.setCurrentlyDewPoint(o.getDouble("dewPoint"));
            info.setCurrentlyWindSpeed(o.getDouble("windSpeed"));
            info.setCurrentlyCloudCover(o.getDouble("cloudCover"));
            info.setCurrentlyPressure(o.getDouble("pressure"));
            info.setCurrentlyVisibility(o.getDouble("visibility"));
            info.setCurrentlyWindBearing(o.getInt("windBearing"));
        } catch(JSONException je) {
            Log.e(TAG, "Failed to parse JSON", je);
        }
    }
}
