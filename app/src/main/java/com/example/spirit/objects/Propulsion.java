package com.example.spirit.objects;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Propulsion implements Serializable {

    private String name;
    @SerializedName(value = "effective_exhaust_velocity")
    private double exhaustVelocity;
    @SerializedName(value = "structural_ratio")
    private double structuralRatio;
    @SerializedName(value = "payload_ratio")
    private double payloadRatio;
    @SerializedName(value = "mass_ratio")
    private double massRatio;
    @SerializedName(value = "travel_velocity")
    private double travelVelocity;
    @SerializedName(value = "max_velocity")
    private double maxVelocity;
    private String inventor;
    private String year;
    @SerializedName(value = "energy_source")
    private String energySource;
    private String description;
    private String status;
    private String highlights;
    private String risks;
    private String picture;

    public String getName() {
        return name;
    }

    public double getExhaustVelocity() {
        return exhaustVelocity;
    }

    public double getStructuralRatio() {
        return structuralRatio;
    }

    public double getPayloadRatio() {
        return payloadRatio;
    }

    public double getMassRatio() {
        return massRatio;
    }

    public double getTravelVelocity() {
        return travelVelocity;
    }

    public double getMaxVelocity() {
        return maxVelocity;
    }

    public String getInventor() {
        return inventor;
    }

    public String getYear() {
        return year;
    }

    public String getEnergySource() {
        return energySource;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getHighlights() {
        return highlights;
    }

    public String getRisks() {
        return risks;
    }

    public String getPicture() {
        return picture;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExhaustVelocity(double exhaustVelocity) {
        this.exhaustVelocity = exhaustVelocity;
    }

    public void setStructuralRatio(double structuralRatio) {
        this.structuralRatio = structuralRatio;
    }

    public void setPayloadRatio(double payloadRatio) {
        this.payloadRatio = payloadRatio;
    }

    public void setMassRatio(double massRatio) {
        this.massRatio = massRatio;
    }

    public void setTravelVelocity(double travelVelocity) {
        this.travelVelocity = travelVelocity;
    }

    public void setMaxVelocity(double maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setEnergySource(String energySource) {
        this.energySource = energySource;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    public void setRisks(String risks) {
        this.risks = risks;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @NonNull
    @Override
    public String toString() {
        return "Propulsion -- name: " + name +
                " exhaust velocity: " + exhaustVelocity +
                " structural ratio: " + structuralRatio +
                " patload ratio: " + payloadRatio +
                " mass ratio: " + massRatio +
                " travel velocity: " + travelVelocity +
                " max velocity: " + maxVelocity +
                " inventor: " + inventor +
                " year: " + year +
                " energy source: " + energySource +
                " description: " + description +
                " status: " + status +
                " highlights: " + highlights +
                " risks: " + risks +
                " picture: " + picture;
    }
}
