package com.example.spirit.objects;

import androidx.annotation.NonNull;

public class Planet {

    private String name;
    private float rightAscension;
    private float declination;
    private float distance;
    private int year;

    public Planet(){
        super();
    }

    public String getName() {
        return name;
    }

    public float getRightAscension() {
        return rightAscension;
    }

    public float getDeclination() {
        return declination;
    }

    public float getDistance() {
        return distance;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRightAscension(float rightAscension) {
        this.rightAscension = rightAscension;
    }

    public void setDeclination(float declination) {
        this.declination = declination;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @NonNull
    @Override
    public String toString() {
        return "Planet name: " + name + ", distance: " + distance + ", rightAscension: " + rightAscension + ", declination: " + declination + ", year: " + year + "\n";
    }
}
