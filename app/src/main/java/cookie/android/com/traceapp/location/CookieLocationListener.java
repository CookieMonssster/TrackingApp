package cookie.android.com.traceapp.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import cookie.android.com.traceapp.location.events.LocationChangedEvent;
import cookie.android.com.traceapp.location.events.LocationEvent;
import cookie.android.com.traceapp.location.events.LocationProviderDisabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderEnabledEvent;
import cookie.android.com.traceapp.location.events.LocationProviderEvent;
import cookie.android.com.traceapp.location.events.LocationProviderStatusChangedEvent;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieLocationListener implements LocationListener {

    private static final String TAG = "CookieLocationListener";

    PublishSubject<LocationEvent> locationChangeSubject = PublishSubject.create();

    public Observable<LocationChangedEvent> getLocationChangedObservable(){
        return locationChangeSubject.ofType(LocationChangedEvent.class).asObservable();
    }

    public Observable<LocationProviderDisabledEvent> getLocationProviderDisabledEvent(){
        return locationChangeSubject.ofType(LocationProviderDisabledEvent.class).asObservable();
    }

    public Observable<LocationProviderEnabledEvent> getLocationProviderEnabledEvent(){
        return locationChangeSubject.ofType(LocationProviderEnabledEvent.class).asObservable();
    }

    public Observable<LocationProviderStatusChangedEvent> getLocationProviderStatusChangedObservable(){
        return locationChangeSubject.ofType(LocationProviderStatusChangedEvent.class).asObservable();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Location changed");
        locationChangeSubject.onNext(new LocationChangedEvent(location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "Provider changed: " + provider);
        locationChangeSubject.onNext(new LocationProviderStatusChangedEvent(provider, status, extras));
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "Provider Enabled: " + provider);
        locationChangeSubject.onNext(new LocationProviderEnabledEvent(provider));
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "Provider Disabled: " + provider);
        locationChangeSubject.onNext(new LocationProviderDisabledEvent(provider));
    }


}
