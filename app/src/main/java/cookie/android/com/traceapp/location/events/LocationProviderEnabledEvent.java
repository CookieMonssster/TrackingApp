package cookie.android.com.traceapp.location.events;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class LocationProviderEnabledEvent extends LocationProviderEvent {
    public String provider;

    public LocationProviderEnabledEvent(String provider) {
        this.provider = provider;
    }
}
