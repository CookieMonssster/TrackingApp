package cookie.android.com.traceapp.helpers;

/**
 * Created by CookieMonssster on 2016-07-12.
 */

public class TrackingData {

    private double lat;
    private double lon;

    public TrackingData(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String toString() {
        return lat + " " + lon;
    }
}
