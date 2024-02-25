package com.example.control_juvenil;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Caida implements Serializable {

    private String id;
    private Double lat;
    private Double longi;
    private LocalDateTime fallTime;

    public Caida(String id, Double lat, Double longi, LocalDateTime fallTime) {
        this.id = id;
        this.lat = lat;
        this.longi = longi;
        this.fallTime = fallTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getFallTime() {
        return fallTime;
    }

    public void setFallTime(LocalDateTime fallTime) {
        this.fallTime = fallTime;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }
}
