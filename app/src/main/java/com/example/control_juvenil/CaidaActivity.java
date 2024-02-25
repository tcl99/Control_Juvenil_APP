package com.example.control_juvenil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

public class CaidaActivity extends AppCompatActivity implements LocationListener {

    public static final String EXTRA_MSG = "com.example.caida.MESSAGE";

    private Caida c;

    private TextView idCaida;
    private TextView locationCaida;
    private  TextView distanceToPanel;

    private LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caida);

        //LOCALIZACION
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MSG)) {
            c = (Caida) intent.getSerializableExtra(EXTRA_MSG);
        }

        idCaida = findViewById(R.id.textViewIdCaida);
        locationCaida = findViewById(R.id.textViewLocationCaida);
        distanceToPanel = findViewById(R.id.textViewDistancia);

        idCaida.setText(c.getId());

        Double latitud = c.getLat();
        Double longitud = c.getLongi();

        locationCaida.setText("Lat: " + latitud.toString() + ", Long: " + longitud.toString());

        distanceToPanel.setText("Distancia a panel cercano: " + distance(c.getLat(), c.getLongi(),42.99997725, -4.13617072));

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        distanceToPanel.setText("Distancia a panel cercano: " + distance(c.getLat(), c.getLongi(),42.99997725, -4.13617072) + " metros");
    }

    public double distance(double lat1, double lon1, double lat2, double lon2) {
        // Convertir coordenadas de grados a radianes
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        // Diferencia de latitud y longitud
        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        // Calcular la distancia
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        //Radio Tierra
        double distance = 6371000 * c;

        return distance;
    }
}