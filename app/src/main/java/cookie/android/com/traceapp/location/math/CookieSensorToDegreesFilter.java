package cookie.android.com.traceapp.location.math;

import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieSensorToDegreesFilter implements Func1<CookieSensorChangedEvent, CookieSensorChangedEvent> {

    @Override
    public CookieSensorChangedEvent call(CookieSensorChangedEvent cookieSensorChangedEvent) {
        double azimuth = Math.toDegrees(cookieSensorChangedEvent.azimuth);
        double pitch = Math.toDegrees(cookieSensorChangedEvent.pitch);
        double roll = Math.toDegrees(cookieSensorChangedEvent.roll);
        double accRange = cookieSensorChangedEvent.accRange;
        double geoRange = cookieSensorChangedEvent.geoRange;
        return new CookieSensorChangedEvent(new double[]{azimuth, pitch, roll}, accRange, geoRange);
    }
}
