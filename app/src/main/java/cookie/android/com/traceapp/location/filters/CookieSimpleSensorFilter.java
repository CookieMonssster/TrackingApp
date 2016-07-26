package cookie.android.com.traceapp.location.filters;

import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import rx.functions.Func1;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieSimpleSensorFilter implements Func1<CookieSensorChangedEvent, CookieSensorChangedEvent> {

    private static final float DEFAULT_VALUE = 3000;
    private static final float SAMPLES = 3;

    private double azimuth = DEFAULT_VALUE;
    private double pitch = DEFAULT_VALUE;
    private double roll = DEFAULT_VALUE;


    @Override
    public CookieSensorChangedEvent call(CookieSensorChangedEvent cookieSensorChangedEvent) {
        if(azimuth == DEFAULT_VALUE || pitch == DEFAULT_VALUE || roll == DEFAULT_VALUE) {
            updateFields(cookieSensorChangedEvent);
        }
        azimuth = ((cookieSensorChangedEvent.azimuth * SAMPLES) + azimuth) / (SAMPLES + 1);
        pitch = ((cookieSensorChangedEvent.pitch * SAMPLES) + pitch) / (SAMPLES + 1);
        roll = ((cookieSensorChangedEvent.roll * SAMPLES) + roll) / (SAMPLES + 1);
        return new CookieSensorChangedEvent(new double[]{azimuth, pitch, roll}, cookieSensorChangedEvent.accRange, cookieSensorChangedEvent.geoRange);
    }

    private void updateFields(CookieSensorChangedEvent cookieSensorChangedEvent) {
        azimuth = cookieSensorChangedEvent.azimuth;
        pitch = cookieSensorChangedEvent.pitch;
        roll = cookieSensorChangedEvent.roll;
    }
}
