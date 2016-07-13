package cookie.android.com.traceapp.helpers;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public class Filters {

    public static TrackingData simpleFilter(TrackingData td) {
        return td;
    }



//    private static final long WILDLY_OUT = 15;
//    private static final long TOO_OLD = 30000;
//    private static final long NO_SAMPLES = 5;
//
//    double lastLocationTime;
//
//    double calclongitude(Location location, double oldLongitude) {
//        double newLongitude = oldLongitude;
//
//        if (location.getAccuracy() < WILDLY_OUT) {
//            newLongitude = (NO_SAMPLES * oldLongitude + location
//                    .getLongitude()) / (NO_SAMPLES + 1);
//            lastLocationTime = System.currentTimeMillis();
//        }
//
//        if (lastLocationTime > TOO_OLD) {
//            newLongitude = location.getLongitude();
//        }
//
//        return newLongitude;
//    }
}
