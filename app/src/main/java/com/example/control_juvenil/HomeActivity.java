package com.example.control_juvenil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private final float THRESHOLD = 4f; // Umbral de detección de caídas
    private boolean fallen = false; // Indica si se ha detectado una caída previamente, sirve para no iniciar mas de 1 activity de caida a la vez

    private Vibrator vibrator;
    private LocationManager locationManager;
    private Location lastFallLocation;
    private SensorManager sensorManager;


    private int fallCounter;
    private ListView fallView;
    private ArrayList<String> falls;
    //Lista para entrar a la activity donde se ve la localización de la caída
    private ArrayList<Caida> fallsDetail;

    private ArrayAdapter<String> adapter;

    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //LOCALIZACION
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //SENSORES
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> listaSensores = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : listaSensores) {
            System.out.println(sensor.getName());
        }

        Sensor acelerometter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometter != null) {
            sensorManager.registerListener((SensorEventListener) this, acelerometter, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscope != null) {
            sensorManager.registerListener((SensorEventListener) this, gyroscope, SensorManager.SENSOR_DELAY_UI);
        }

        fallView = (ListView) findViewById(R.id.falls);
        falls = new ArrayList<String>();
        falls.add("Camino Lebaniego");

        falls.sort(String::compareToIgnoreCase);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, falls);
        fallView.setAdapter(adapter);

        fallView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent;
                String opcion = falls.get(i);

                Toast.makeText(HomeActivity.this, "Test", Toast.LENGTH_SHORT).show();
                /*intent = new Intent(RoutesActivity.this, MapActivity.class);
                String msg = routes.get(i).toString();
                intent.putExtra(EXTRA_MSG, msg);
                startActivity(intent);*/

            }
        });

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

        }

    }

    // METODOS SENSORES


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Calcular la aceleración total
            float acceleration = (float) Math.sqrt(x * x + y * y + z * z);

            if (acceleration < THRESHOLD) {
                fallCounter++;
                //Si se ha superado el umbral y no se ha detectado una caída previamente

                //Toast
                Toast.makeText(this, "CAIDA DETECTADA", Toast.LENGTH_SHORT).show();
                //El telefono vibra
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.EFFECT_TICK));
                //Se comprueban los permisos de la localización
                 if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                     ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                }

                //Se recoge la localización
                lastFallLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //Se guarda en la lista la caida y se actualiza
                LocalDateTime fallTime = LocalDateTime.now();
                String fallID = "Caida " + fallCounter + ": " + fallTime.format(timeFormat);
                falls.add(fallID);
                adapter.notifyDataSetChanged();
                fallView.smoothScrollToPosition(falls.size() - 1);

                fallsDetail.add(new Caida(fallID, lastFallLocation, fallTime ));
            }
        } //else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }
}