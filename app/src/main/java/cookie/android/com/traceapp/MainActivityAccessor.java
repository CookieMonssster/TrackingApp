package cookie.android.com.traceapp;

import android.content.Context;
import android.location.Location;
/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public interface MainActivityAccessor {

    Context getContext();
    void locationChanged(Location td);

}
