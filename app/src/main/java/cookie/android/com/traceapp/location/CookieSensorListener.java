package cookie.android.com.traceapp.location;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import cookie.android.com.traceapp.location.events.CookieSensorAccuracyChanged;
import cookie.android.com.traceapp.location.events.CookieSensorChangedEvent;
import cookie.android.com.traceapp.location.events.CookieSensorEvent;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class CookieSensorListener implements SensorEventListener {

    private static final String TAG = CookieSensorListener.class.getSimpleName();

    PublishSubject<CookieSensorEvent> sensorChangeSubject = PublishSubject.create();

    float[] gravity;
    float[] geometric;

    public Observable<CookieSensorChangedEvent> getLocationChangedObservable() {
        return sensorChangeSubject.ofType(CookieSensorChangedEvent.class).asObservable();
    }

    public Observable<CookieSensorAccuracyChanged> getSensorAccuracyChangedObservable() {
        return sensorChangeSubject.ofType(CookieSensorAccuracyChanged.class).asObservable();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float accRange = 0;
        float geoRange = 0;
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accRange = event.sensor.getMaximumRange();
            gravity = event.values;
            //printTab("ACC range: " + accRange, gravity);
        }
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geoRange = event.sensor.getMaximumRange();
            geometric = event.values;
            printTab("GEO range: " + geoRange, geometric);
        }
        if (gravity != null && geometric != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            if (SensorManager.getRotationMatrix(R, I, gravity, geometric)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                sensorChangeSubject.onNext(new CookieSensorChangedEvent(
                        new double[]{orientation[0], orientation[1], orientation[2]}, accRange, geoRange));
            }
        }
    }


    private void printTab(String head, float[] tab) {
        StringBuilder str = new StringBuilder();
        str.append(head);
        str.append(": ");
        for (float value : tab) {
            str.append(value);
            str.append(", ");
        }
        Log.d(TAG, "" + str.toString());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
