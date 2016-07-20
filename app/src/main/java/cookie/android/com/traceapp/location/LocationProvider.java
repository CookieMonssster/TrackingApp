package cookie.android.com.traceapp.location;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import cookie.android.com.traceapp.location.events.LocationChangedEvent;
import cookie.android.com.traceapp.location.events.LocationProviderDisabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderEnabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderStatusChangedEvent;
import rx.Observable;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class LocationProvider {

    private static final String TAG = "LocationProvider";

    private static final long MINIMUM_TIME = 3000;
    private static final long MINIMUM_DISTANCE = 3;

    private GPSLocationListener locationListener;
    private LocationManager locationManager;

    private Context context;

    public LocationProvider(Context context) {
        this.context = context;
    }


    public void startGPSTracking() {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new GPSLocationListener();
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MINIMUM_TIME, MINIMUM_DISTANCE, locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Seciurity error", e);
        }
    }

    public void stopGPSTracking() {
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Seciuruty Error: ", e);
        }
    }

    public Observable<LocationChangedEvent> getLocationChangeObservable() {
        return locationListener.getLocationChangedObservable();
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
