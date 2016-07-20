package cookie.android.com.traceapp.location.events;

import android.os.Bundle;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class LocationProviderStatusChangedEvent extends LocationProviderEvent {
    public String provider;
    public int status;
    public Bundle extras;

    public LocationProviderStatusChangedEvent(String provider, int status, Bundle extras) {
        this.provider = provider;
        this.status = status;
        this.extras = extras;
    }
}
