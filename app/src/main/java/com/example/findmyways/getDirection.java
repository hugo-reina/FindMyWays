package com.example.findmyways;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class getDirection extends AppCompatActivity implements SensorEventListener {



    private ImageView boussole;
    private float[] mGravity = new float[3];
    private float[] mGeo = new float[3];
    private float azimuth = 0f;
    private float currAzimuth = 0f;
    private SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_direction);

        boussole = (ImageView) findViewById(R.id.compass);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


    }


    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener((SensorEventListener) this);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this,sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener((SensorEventListener) this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        final float alpha = 0.97f;
        synchronized (this){
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                mGravity[0] = alpha*mGravity[0]+(1-alpha)*event.values[0];
                mGravity[1] = alpha*mGravity[1]+(1-alpha)*event.values[1];
                mGravity[2] = alpha*mGravity[2]+(1-alpha)*event.values[2];
            }

            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                mGeo[0] = alpha*mGeo[0]+(1-alpha)*event.values[0];
                mGeo[1] = alpha*mGeo[1]+(1-alpha)*event.values[1];
                mGeo[2] = alpha*mGeo[2]+(1-alpha)*event.values[2];
            }
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R,I,mGravity,mGeo);
            if(success){
                float orientation[] = new float[3];
                SensorManager.getOrientation(R,orientation);
                azimuth = (float)Math.toDegrees(orientation[0]);
                azimuth = (azimuth+360)%360;

                Animation animation = new RotateAnimation(-currAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.1f);
                currAzimuth = azimuth;

                animation.setDuration(500);
                animation.setRepeatCount(0);
                animation.setFillAfter(true);
                boussole.startAnimation(animation);
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int i){

    }
}