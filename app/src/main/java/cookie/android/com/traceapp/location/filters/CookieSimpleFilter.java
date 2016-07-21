package cookie.android.com.traceapp.location.filters;

import android.location.Location;

import cookie.android.com.traceapp.location.events.LocationChangedEvent;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieSimpleFilter implements Func1<LocationChangedEvent, LocationChangedEvent> {

    private static final long WILDLY_OUT = 15;
    private static final long TOO_OLD = 10000;
    private static final long NO_SAMPLES = 3;
    private static final double DEFAULT_DATA = 3000;

    long lastLocationTime;

    double oldLon = DEFAULT_DATA;
    double oldLat = DEFAULT_DATA;

    @Override
    public LocationChangedEvent call(LocationChangedEvent locationChangedEvent) {
        checkOldData(locationChangedEvent);

        double filteredLon = oldLon;
        double filteredLat = oldLat;

        if (locationChangedEvent.location.getAccuracy() < WILDLY_OUT) {
            filteredLon = (NO_SAMPLES * oldLon + locationChangedEvent.location.getLongitude()) / (NO_SAMPLES + 1);
            filteredLat = (NO_SAMPLES * oldLat + locationChangedEvent.location.getLatitude()) / (NO_SAMPLES + 1);
            lastLocationTime = System.currentTimeMillis() - lastLocationTime;
        }

        if (lastLocationTime > TOO_OLD) {
            filteredLon = locationChangedEvent.location.getLongitude();
            filteredLat = locationChangedEvent.location.getLatitude();
        }

        oldLon = filteredLon;
        oldLat = filteredLat;
        Location filteredLoc = new Location(locationChangedEvent.location.getProvider());
        filteredLoc.setLatitude(filteredLat);
        filteredLoc.setLongitude(filteredLon);

        return new LocationChangedEvent(filteredLoc);
    }

    private void checkOldData(LocationChangedEvent locationChangedEvent) {
        if(oldLon == DEFAULT_DATA || oldLon == DEFAULT_DATA) {
            oldLon = locationChangedEvent.location.getLongitude();
            oldLat = locationChangedEvent.location.getLatitude();
        }
    }
}
