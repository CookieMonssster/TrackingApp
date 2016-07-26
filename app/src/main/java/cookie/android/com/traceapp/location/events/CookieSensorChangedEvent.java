package cookie.android.com.traceapp.location.events;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieSensorChangedEvent extends CookieSensorEvent {

    private static final int AZIMUTH_INDEX = 0;
    private static final int PITCH_INDEX = 1;
    private static final int ROLL_INDEX = 2;

    public double azimuth;
    public double pitch;
    public double roll;
    public double accRange;
    public double geoRange;


    public CookieSensorChangedEvent(double orientation[], double accRange, double geoRange) {
        azimuth = (orientation[AZIMUTH_INDEX]);
        pitch = (orientation[PITCH_INDEX]);
        roll = (orientation[ROLL_INDEX]);
        this.accRange = accRange;
        this.geoRange = geoRange;
    }
}
