package cookie.android.com.traceapp.helpers;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import cookie.android.com.traceapp.MainActivityAccessor;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public class TraceAppLocationListener implements LocationListener {

    private static final String TAG = TraceAppLocationListener.class.getSimpleName();
    private MainActivityAccessor accessor;

    public TraceAppLocationListener(MainActivityAccessor accessor) {
        this.accessor = accessor;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Lat: " + location.getLatitude() + " Lon: " + location.getLongitude());
        accessor.locationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(accessor.getContext(), "provider status changed: " + provider, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(accessor.getContext(), "provider: " + provider + "is enabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(accessor.getContext(), "provider: " + provider + "is enabled", Toast.LENGTH_SHORT).show();
    }
}
