package cookie.android.com.traceapp.helpers;

import android.location.Location;

/**
 * Created by CookieMonssster on 2016-07-12.
 */

public class TrackingData {

    private double lat;
    private double lon;

    public TrackingData(Location location) {
        this.lat = location.getLatitude();
        this.lon = location.getLongitude();
    }

    public String toString() {
        return lat + " " + lon;
    }
}
