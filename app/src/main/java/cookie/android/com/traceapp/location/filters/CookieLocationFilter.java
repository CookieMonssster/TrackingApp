package cookie.android.com.traceapp.location.filters;

import android.location.Location;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieLocationFilter implements Func1<Location, Location> {

    private static final long WILDLY_OUT = 15;
    private static final long TOO_OLD = 10000;
    private static final long NO_SAMPLES = 3;
    private static final double DEFAULT_DATA = 3000;

    double olderLon = DEFAULT_DATA;
    double olderLat = DEFAULT_DATA;
    double oldLon = DEFAULT_DATA;
    double oldLat = DEFAULT_DATA;

    @Override
    public Location call(Location location) {
        if(checkOlderData(location) && checkOldData(location)) {
            Location predictionLocation = new Location("unknown");
            Location filteredLocation = new Location("unknown");
            double dx = oldLon - olderLon;
            double dy = oldLat - olderLat;
            predictionLocation.setLongitude(oldLon + dx);
            predictionLocation.setLatitude(oldLat + dy);
            filteredLocation.setLongitude((predictionLocation.getLongitude() * NO_SAMPLES + location.getLongitude()) / (NO_SAMPLES + 1));
            filteredLocation.setLatitude((predictionLocation.getLatitude() * NO_SAMPLES + location.getLatitude()) / (NO_SAMPLES + 1));
            updateLocations(filteredLocation);
            return filteredLocation;
        } else {
            return location;
        }
    }


    private boolean checkOldData(Location location) {
        if(oldLon == DEFAULT_DATA || oldLon == DEFAULT_DATA) {
            oldLon = (olderLon * NO_SAMPLES + location.getLongitude()) / (NO_SAMPLES + 1);
            oldLat = (olderLat * NO_SAMPLES + location.getLatitude()) / (NO_SAMPLES + 1);
            return false;
        }
        return true;
    }

    private boolean checkOlderData(Location location) {
        if(olderLat == DEFAULT_DATA || olderLon == DEFAULT_DATA) {
            olderLon = location.getLongitude();
            olderLat = location.getLatitude();
            return false;
        }
        return true;
    }

    private void updateLocations(Location location) {
        olderLon = oldLon;
        olderLat = oldLat;
        oldLon = location.getLongitude();
        oldLat = location.getLatitude();
    }
}
