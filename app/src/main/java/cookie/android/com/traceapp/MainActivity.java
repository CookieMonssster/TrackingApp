package cookie.android.com.traceapp;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cookie.android.com.traceapp.helpers.Filters;
import cookie.android.com.traceapp.helpers.PermissionsRequester;
import cookie.android.com.traceapp.location.CookieLocationProvider;
import cookie.android.com.traceapp.location.CookieLocationProviderBuilder;
import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import cookie.android.com.traceapp.location.filters.CookieLocationFilter;
import cookie.android.com.traceapp.location.math.CookieSensorRoundFilter;
import cookie.android.com.traceapp.location.math.CookieSensorToDegreesFilter;
import cookie.android.com.traceapp.location.filters.CookieSimpleLocationFilter;
import rx.Subscriber;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TRACKING_URL = "/sdcard/trackedFile.txt";
    private static final String FILTERED_URL = "/sdcard/filteredData.txt";

    private Button trackingButton;
    private Button compassButton;
    private Button filteringButton;
    private TextView state;
    private TextView lastLoc;
    private TextView lastCompass;
    private boolean isTracking = false;
    private boolean isCompass = false;

    private List<Location> trackingList;
    private List<Location> filteredTrackingList;

    Subscription locationChangeSubscriber;
    Subscription filteredLocationChangeSubscriber;
    Subscription compassChangeSubscriber;

    CookieLocationProvider cookieLocationProvider;

    private Filters filters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeComponents();
        trackingList = new ArrayList<>();
        filteredTrackingList = new ArrayList<>();
        cookieLocationProvider = new CookieLocationProviderBuilder(this).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionsRequester.verifyStoragePermissions(this);

    }

    private void startFiltering() {
        trackingList = filters.getExampleData3();
        filteredTrackingList.clear();
        //CookieSimpleLocationFilter sf = new CookieSimpleLocationFilter();
        CookieLocationFilter cf = new CookieLocationFilter();
        for (Location loc : trackingList) {
            filteredTrackingList.add(cf.call(loc));
        }
        saveData(filteredToString(), FILTERED_URL);

    }

    private void stopCompass() {
        setStopCompassUI();
        compassChangeSubscriber.unsubscribe();

    }

    private void startCompass() {
        Toast.makeText(this.getApplicationContext(), "Start Comass", Toast.LENGTH_SHORT).show();
        setCompassStartUI();
        compassChangeSubscriber = cookieLocationProvider.getFilteredSensorChangeObservable()
                .map(new CookieSensorToDegreesFilter())
                .map(new CookieSensorRoundFilter())
                .subscribe(new SensorChangedSubscriber());
    }

    private void stopTracking() {
        setDefaultUI();
        locationChangeSubscriber.unsubscribe();
        filteredLocationChangeSubscriber.unsubscribe();
        saveData(dataToString(), TRACKING_URL);
    }

    private void startTracking() {
        setTrackingUI();
        locationChangeSubscriber = cookieLocationProvider.getLocationChangeObservable().subscribe(new LocationChangedSubscriber());
        filteredLocationChangeSubscriber = cookieLocationProvider.getSimpleFilteredLocationChangeObservable()
                .subscribe(new FilteredLocationChangedSubscriber());

    }

    private void saveData(String data, String url) {
        try {

            File myFile = new File(url);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(), R.string.save_done_message + url, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Write error", e);
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void setDefaultUI() {
        state.setText(getString(R.string.hello_in_trace_app));
        lastLoc.setText(getString(R.string.press_button_to_start));
        trackingButton.setText(getString(R.string.start_tracking));
    }

    private void setTrackingUI() {
        state.setText(R.string.tracking);
        lastLoc.setText(R.string.looking_for_signal);
        trackingButton.setText(R.string.stop_tracking);
    }

    private void setStopCompassUI() {
        lastCompass.setText(R.string.no_compass);
        compassButton.setText(R.string.start_compass);
    }

    private void setCompassStartUI() {
        compassButton.setText(R.string.stop_compass);
    }


    private void initializeComponents() {
        state = (TextView) findViewById(R.id.state);
        lastLoc = (TextView) findViewById(R.id.last_loc);
        lastCompass = (TextView) findViewById(R.id.last_compass);


        trackingButton = (Button) findViewById(R.id.tracking_button);
        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTracking) {
                    stopTracking();
                    isTracking = false;
                } else {
                    startTracking();
                    isTracking = true;
                }
            }
        });
        compassButton = (Button) findViewById(R.id.compass_button);
        compassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCompass) {
                    stopCompass();
                    isCompass = false;
                } else {
                    startCompass();
                    isCompass = true;
                }
            }
        });

        filteringButton = (Button) findViewById(R.id.filtering_button);
        filteringButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFiltering();
            }
        });

    }

    private String dataToString() {
        StringBuilder str = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateAndTime = sdf.format(new Date());
        str.append(currentDateAndTime);
        str.append("\n");
        for (Location loc : trackingList) {
            str.append("data.add(newLocation(");
            str.append(printLoc(loc));
            str.append("));\n");
        }

        str.append("\n\n");

        for (Location loc : trackingList) {
            str.append(printLoc(loc));
        }

        return str.toString();
    }

    private String filteredToString() {
        StringBuilder str = new StringBuilder();
        for (Location loc : filteredTrackingList) {
            str.append(printLoc(loc));
            str.append("\n");
        }
        return str.toString();
    }

    private void locationChanged(Location loc) {
        lastLoc.setText(printLoc(loc));
    }

    private void compassChanged(CookieSensorChangedEvent sensorChangedEvent) {
        StringBuilder str = new StringBuilder();
        str.append("Azimuth: ");
        str.append(Math.round(sensorChangedEvent.azimuth));
        str.append("\nPitch: ");
        str.append(Math.round(sensorChangedEvent.pitch));
        str.append("\nRoll: ");
        str.append(Math.round(sensorChangedEvent.roll));
        lastCompass.setText(str.toString());
    }

    private String printLoc(Location loc) {
        return loc.getLatitude() + " " + loc.getLongitude();
    }


    private class LocationChangedSubscriber extends Subscriber<Location> {


        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Location location) {
            locationChanged(location);
            trackingList.add(location);
        }
    }

    private class FilteredLocationChangedSubscriber extends Subscriber<Location> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(Location location) {
            filteredTrackingList.add(location);
        }
    }

    private class SensorChangedSubscriber extends Subscriber<CookieSensorChangedEvent> {

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(CookieSensorChangedEvent sensorChangedEvent) {
            compassChanged(sensorChangedEvent);
        }
    }
}
