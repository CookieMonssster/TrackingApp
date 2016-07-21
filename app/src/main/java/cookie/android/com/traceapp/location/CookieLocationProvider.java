package cookie.android.com.traceapp.location;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.util.Log;

import cookie.android.com.traceapp.location.events.LocationChangedEvent;
import cookie.android.com.traceapp.location.events.LocationProviderDisabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderEnabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderStatusChangedEvent;
import cookie.android.com.traceapp.location.filters.CookieSensorEventListener;
import cookie.android.com.traceapp.location.filters.CookieSimpleFilter;
import rx.Observable;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieLocationProvider {

    private static final String TAG = "CookieLocationProvider";

    private static final String DEFAULT_PROVIDER = LocationManager.GPS_PROVIDER;
    private static final long DEFAULT_MINIMUM_TIME = 3000;
    private static final long DEFAULT_MINIMUM_DISTANCE = 3;

    private CookieLocationListener locationListener;
    private LocationManager locationManager;

    protected String provider = DEFAULT_PROVIDER;
    protected long minimumTime = DEFAULT_MINIMUM_TIME;
    protected long minimumDistance = DEFAULT_MINIMUM_DISTANCE;

    private Activity activity;

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private CookieSensorEventListener cookieSensorEventListener;


    protected CookieLocationProvider(Activity activity) {
        this.activity = activity;
        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        cookieSensorEventListener = new CookieSensorEventListener();
    }

    public void startCompass() {
        sensorManager.registerListener(cookieSensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(cookieSensorEventListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void stopCompass() {
        sensorManager.unregisterListener(cookieSensorEventListener);
    }


    public void startTracking() {
        PermissionRequester.verifyGPSPermissions(activity);

        locationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new CookieLocationListener();
        try {
            locationManager.requestLocationUpdates(provider, minimumTime, minimumDistance, locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Security error: ", e);
        }
    }

    public void stopTracking() {
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Security Error: ", e);
        }
    }

    public Observable<LocationChangedEvent> getLocationChangeObservable() {
        return locationListener.getLocationChangedObservable();
    }

    public Observable<LocationChangedEvent> getFilteredLocationChangeObservable(Func1<LocationChangedEvent, LocationChangedEvent> func1) {
        return locationListener.getLocationChangedObservable().map(func1);
    }

    public Observable<LocationChangedEvent> getSimpleFilteredLocationChangeObservable() {
        return getFilteredLocationChangeObservable(new CookieSimpleFilter());
    }

    public Observable<LocationProviderStatusChangedEvent> getLocationProviderStatusChangedObservable() {
        return locationListener.getLocationProviderStatusChangedObservable();
    }

    public Observable<LocationProviderEnabledEvent> getLocationProviderEnabledObservable() {
        return locationListener.getLocationProviderEnabledEvent();
    }

    public Observable<LocationProviderDisabledEvent> getLocationProviderDisabledObservable() {
        return locationListener.getLocationProviderDisabledEvent();
    }
}
