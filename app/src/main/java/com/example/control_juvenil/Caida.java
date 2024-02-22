package com.example.control_juvenil;

import android.location.Location;

import java.time.LocalDateTime;

public class Caida {

    private String id;
    private Location location;
    private LocalDateTime fallTime;

    public Caida(String id, Location location, LocalDateTime fallTime) {
        this.id = id;
        this.location = location;
        this.fallTime = fallTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getFallTime() {
        return fallTime;
    }

    public void setFallTime(LocalDateTime fallTime) {
        this.fallTime = fallTime;
    }
}
