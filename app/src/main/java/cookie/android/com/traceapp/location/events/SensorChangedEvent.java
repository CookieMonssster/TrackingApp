package cookie.android.com.traceapp.location.events;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public class SensorChangedEvent extends SensorEvent {
    public float orientation[];

    public SensorChangedEvent(float orientation[]) {
        this.orientation = orientation;
    }
}
