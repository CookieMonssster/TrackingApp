package cookie.android.com.traceapp.location.math;

import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */

public class CookieSensorRoundFilter implements Func1<CookieSensorChangedEvent, CookieSensorChangedEvent> {
    @Override
    public CookieSensorChangedEvent call(CookieSensorChangedEvent cookieSensorChangedEvent) {
        long azimuth = Math.round(cookieSensorChangedEvent.azimuth);
        long pitch = Math.round(cookieSensorChangedEvent.pitch);
        long roll = Math.round(cookieSensorChangedEvent.roll);
        double accRange = cookieSensorChangedEvent.accRange;
        double geoRange = cookieSensorChangedEvent.geoRange;
        return new CookieSensorChangedEvent(new double[]{azimuth, pitch, roll}, accRange, geoRange);
    }
}
