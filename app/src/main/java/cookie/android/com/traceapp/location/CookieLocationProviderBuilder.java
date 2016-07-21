package cookie.android.com.traceapp.location;

import android.app.Activity;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */

public class CookieLocationProviderBuilder {
    private CookieLocationProvider cookieLocationProvider;

    public CookieLocationProviderBuilder(Activity activity) {
        cookieLocationProvider = new CookieLocationProvider(activity);
    }

    public CookieLocationProviderBuilder withProvider(String provider) {
        cookieLocationProvider.provider = provider;
        return this;
    }

    public CookieLocationProviderBuilder withMinimumTime(long minimumTime) {
        cookieLocationProvider.minimumDistance = minimumTime;
        return this;
    }

    public CookieLocationProviderBuilder withMinimumDistance(long minimumDistance) {
        cookieLocationProvider.minimumDistance = minimumDistance;
        return this;
    }

    public CookieLocationProvider build() {
        return cookieLocationProvider;
    }
}
