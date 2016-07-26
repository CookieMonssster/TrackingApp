package cookie.android.com.traceapp.location;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import cookie.android.com.traceapp.location.events.LocationChangedEvent;
import cookie.android.com.traceapp.location.events.LocationProviderDisabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderEnabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderStatusChangedEvent;
import cookie.android.com.traceapp.location.events.CookieSensorAccuracyChanged;
import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import cookie.android.com.traceapp.location.filters.CookieSimpleLocationFilter;
import cookie.android.com.traceapp.location.filters.CookieSimpleSensorFilter;
import rx.Observable;
import rx.functions.Action0;
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
    protected String provider = DEFAULT_PROVIDER;
    protected long minimumTime = DEFAULT_MINIMUM_TIME;
    protected long minimumDistance = DEFAULT_MINIMUM_DISTANCE;

    private int subscribeTrackingCounter = 0;
    private int subscribeCompassCounter = 0;

    private Activity activity;
    private CookieLocationListener locationListener;
    private LocationManager locationManager;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private CookieSensorListener cookieSensorListener;


    protected CookieLocationProvider(Activity activity) {
        this.activity = activity;
        sensorManager = (SensorManager) activity.getSystemService(activity.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        cookieSensorListener = new CookieSensorListener();
        locationListener = new CookieLocationListener();
    }

    private void startCompass() {
        if(subscribeCompassCounter > 1) {
            return;
        }
            sensorManager.registerListener(cookieSensorListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
            sensorManager.registerListener(cookieSensorListener, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    private void stopCompass() {
        if(subscribeCompassCounter < 1) {
            sensorManager.unregisterListener(cookieSensorListener);
        }
    }


    public void startTracking() {
        if(subscribeTrackingCounter > 1) {
            return;
        }
        PermissionRequester.verifyGPSPermissions(activity);


        locationManager = (LocationManager) activity.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(provider, minimumTime, minimumDistance, locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Security error: ", e);
        }
    }

    public void stopTracking() {
        if (subscribeTrackingCounter > 0) {
            return;
        }
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Security Error: ", e);
        }
    }

    public Observable<Location> getLocationChangeObservable() {
        subscribeTrackingCounter++;
        startTracking();
        return locationListener.getLocationChangedObservable().map(new Func1<LocationChangedEvent, Location>() {
            @Override
            public Location call(LocationChangedEvent locationChangedEvent) {
                return locationChangedEvent.location;
            }
        }).doOnUnsubscribe(new unsubscribeTrackingAction());
    }

    public Observable<Location> getFilteredLocationChangeObservable(Func1<Location, Location> func1) {
        return getLocationChangeObservable().map(func1);
    }

    public Observable<Location> getSimpleFilteredLocationChangeObservable() {
        return getFilteredLocationChangeObservable(new CookieSimpleLocationFilter());
    }

    public Observable<LocationProviderStatusChangedEvent> getLocationProviderStatusChangedObservable() {
        subscribeTrackingCounter++;
        startTracking();
        return locationListener.getLocationProviderStatusChangedObservable()
                .doOnUnsubscribe(new unsubscribeTrackingAction());
    }

    public Observable<LocationProviderEnabledEvent> getLocationProviderEnabledObservable() {
        subscribeTrackingCounter++;
        startTracking();
        return locationListener.getLocationProviderEnabledEvent()
                .doOnUnsubscribe(new unsubscribeTrackingAction());
    }

    public Observable<LocationProviderDisabledEvent> getLocationProviderDisabledObservable() {
        subscribeTrackingCounter++;
        startTracking();
        return locationListener.getLocationProviderDisabledEvent()
                .doOnUnsubscribe(new unsubscribeTrackingAction());
    }

    public Observable<CookieSensorChangedEvent> getSensorChangeObservable() {
        startCompass();
        subscribeCompassCounter++;
        return cookieSensorListener.getLocationChangedObservable()
                .doOnUnsubscribe(new unsubscribeCompassAction());
    }

    public Observable<CookieSensorChangedEvent> getFilteredSensorChangeObservable() {
        return getSensorChangeObservable().map(new CookieSimpleSensorFilter());
    }

    public Observable<CookieSensorAccuracyChanged> getSensorAccuracyChangedObservable() {
        startCompass();
        subscribeCompassCounter++;
        return cookieSensorListener.getSensorAccuracyChangedObservable()
                .doOnUnsubscribe(new unsubscribeCompassAction());
    }

    private class unsubscribeTrackingAction implements Action0 {
        @Override
        public void call() {
            subscribeTrackingCounter--;
                stopTracking();
        }
    }

    private class unsubscribeCompassAction implements Action0 {
        @Override
        public void call() {
            subscribeCompassCounter--;
            stopCompass();
        }
    }
}
