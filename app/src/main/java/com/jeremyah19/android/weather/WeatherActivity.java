package com.jeremyah19.android.weather;

import android.support.v4.app.Fragment;

public class WeatherActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return WeatherFragment.newInstance();
    }
}
