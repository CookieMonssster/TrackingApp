package cookie.android.com.traceapp.location.events;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public class CookieSensorChangedEvent extends CookieSensorEvent {
    public float orientation[];

    public CookieSensorChangedEvent(float orientation[]) {
        this.orientation = orientation;
    }
}
