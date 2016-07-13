package cookie.android.com.traceapp;

import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
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
import cookie.android.com.traceapp.helpers.TraceAppLocationListener;
import cookie.android.com.traceapp.helpers.TrackingData;

public class MainActivity extends AppCompatActivity implements MainActivityAccessor {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button trackingButton;
    private TextView state;
    private TextView lastLoc;
    private boolean isTracking = false;

    private List<TrackingData> trackingList;
    private List<TrackingData> filtederedTrackingList;

    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeComponents();
        trackingList = new ArrayList<>();
        filtederedTrackingList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionsRequester.verifyStoragePermissions(this);
        PermissionsRequester.verifyGPSPermissions(this);
    }

    private void stopTracking() {
        setDefaultUI();
        stopGPSTracking();
        saveData(listsToString());
    }

    private void startTracking() {
        setTrackingUI();
        startGPSTracking();
    }

    private void saveData(String data) {
        try {

            File myFile = new File("/sdcard/trackedFile.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append(data);
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(), R.string.save_done_message, Toast.LENGTH_SHORT).show();
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
    }

    private String listsToString() {
        StringBuilder str = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        str.append(currentDateandTime);
        for (int i = 0; i < trackingList.size(); i++) {
            str.append(trackingList.get(i).toString());
            str.append(" ");
            str.append(filtederedTrackingList.get(i).toString());
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
    public void locationChanged(TrackingData td) {
        lastLoc.setText(td.toString());
        trackingList.add(td);
        filtederedTrackingList.add(Filters.simpleFilter(td));
    }
}
