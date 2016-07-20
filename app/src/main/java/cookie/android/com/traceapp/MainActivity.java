package cookie.android.com.traceapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.StringBuilderPrinter;
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
import cookie.android.com.traceapp.helpers.TraceAppLocationListener;

public class MainActivity extends AppCompatActivity implements MainActivityAccessor {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TRACKING_URL = "/sdcard/trackedFile.txt";
    private static final String FILTERED_URL = "/sdcard/filteredData.txt";

    private Button trackingButton;
    private Button filteringButton;
    private TextView state;
    private TextView lastLoc;
    private boolean isTracking = false;

    private List<Location> trackingList;
    private List<Location> filteredTrackingList;

    LocationManager locationManager;
    LocationListener locationListener;
    private Filters filters;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeComponents();
        trackingList = new ArrayList<>();
        filteredTrackingList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionsRequester.verifyStoragePermissions(this);
        PermissionsRequester.verifyGPSPermissions(this);
    }

    private void startFiltering() {
        Filters filters = new Filters(Filters.getFirstLoc());
        trackingList = filters.getExampleData2();
        filteredTrackingList = filters.simpleFilterWithList(trackingList);
        saveData(filteredToString(), FILTERED_URL);

    }

    private void stopTracking() {
        setDefaultUI();
        stopGPSTracking();
        saveData(dataToString(), TRACKING_URL);
    }

    private void startTracking() {
        setTrackingUI();
        startGPSTracking();
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


    private void initializeComponents() {
        state = (TextView) findViewById(R.id.state);
        lastLoc = (TextView) findViewById(R.id.last_loc);


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


    private void startGPSTracking() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new TraceAppLocationListener(this);
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Seciurity error", e);
        }
    }

    private void stopGPSTracking() {
        try {
            locationManager.removeUpdates(locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Seciuruty Error: ", e);
        }
    }

    @Override
    public Context getContext() {
        return getContext();
    }

    @Override
    public void locationChanged(Location loc) {
        if(filters == null) {
            filters = new Filters(loc);
        }
        lastLoc.setText(printLoc(loc));
        trackingList.add(loc);
        Location filteredLoc = filters.simpleFilter(loc);
        filteredTrackingList.add(filteredLoc);
        filters.setOldLoc(loc);
    }

    private String printLoc(Location loc) {
        return loc.getLatitude() + " " + loc.getLongitude();
    }
}
