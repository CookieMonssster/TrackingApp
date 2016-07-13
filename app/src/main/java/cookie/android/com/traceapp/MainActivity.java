package cookie.android.com.traceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import cookie.android.com.traceapp.helpers.TrackingData;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button trackingButton;
    private TextView state;
    private TextView lastLoc;
    private boolean isTracking = false;

    private List<TrackingData> trackingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initializeComponents();
        trackingList = new ArrayList<>();
    }

    private void stopTracking() {
        setDefaultUI();
        saveData("klop klop");
    }

    private void startTracking() {
        setTrackingUI();
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
            Toast.makeText(getBaseContext(), "Done writing SD 'trackedFile.txt'", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
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
        state.setText("Tracking...");
        trackingButton.setText("Stop Tracking");
    }

    private void initializeComponents() {
        state = (TextView) findViewById(R.id.state);
        lastLoc = (TextView) findViewById(R.id.last_loc);


        trackingButton = (Button) findViewById(R.id.tracking_button);
        trackingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTracking) {
                    stopTracking();
                    isTracking = false;
                } else {
                    startTracking();
                    isTracking = true;
                }
            }
        });
    }
}
