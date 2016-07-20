package cookie.android.com.traceapp.location.events;

import android.location.Location;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class LocationChangedEvent extends LocationEvent {
    public Location location;

    public LocationChangedEvent(Location location) {
        this.location = location;
    }
}
