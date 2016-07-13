package cookie.android.com.traceapp;

import android.content.Context;

import cookie.android.com.traceapp.helpers.TrackingData;

/**
 * Copyright (C) TomTom International B.V., 2015
 * All rights reserved.
 */
public interface MainActivityAccessor {

    Context getContext();
    void locationChanged(TrackingData td);

}
