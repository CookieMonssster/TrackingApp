package cookie.android.com.traceapp.location.filters;

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
public class CookieSensorEventListener implements SensorEventListener {

    private static final String TAG = CookieSensorEventListener.class.getSimpleName();

    PublishSubject<CookieSensorEvent> sensorChangeSubject = PublishSubject.create();

    float azimuth;
    float pitch;
    float roll;
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
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
        }
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geometric = event.values;
        }
        if(gravity != null && geometric != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            if(SensorManager.getRotationMatrix(R, I, gravity, geometric)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0];
                pitch = orientation[1];
                roll = orientation[2];
                Log.d(TAG, "azimuth: " + azimuth + " pitch: " + pitch + "roll: " + roll);
                sensorChangeSubject.onNext(new CookieSensorChangedEvent(orientation));

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
