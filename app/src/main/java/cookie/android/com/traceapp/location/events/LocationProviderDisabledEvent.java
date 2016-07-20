package cookie.android.com.traceapp.location.events;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public class LocationProviderDisabledEvent extends LocationProviderEvent {
    public String provider;

    public LocationProviderDisabledEvent(String provider) {
        this.provider = provider;
    }
}
