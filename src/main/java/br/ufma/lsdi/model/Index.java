package br.ufma.lsdi.model;

import lombok.Data;


public class Index {

    private int good;
    private Double concentrationGood;

    private int moderate;
    private Double concentrationModerate;

    private int sensitive;
    private Double concentrationSensitive;

    private int unhealthy;
    private Double concentrationUnhealthy;

    private int veryUnhealthy;
    private Double concentrationVeryUnhealthy;

    private int hazardous;
    private Double concentrationHazardous;

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public Double getConcentrationGood() {
        return concentrationGood;
    }

    public void setConcentrationGood(Double concentrationGood) {
        this.concentrationGood = concentrationGood;
    }

    public int getModerate() {
        return moderate;
    }

    public void setModerate(int moderate) {
        this.moderate = moderate;
    }

    public Double getConcentrationModerate() {
        return concentrationModerate;
    }

    public void setConcentrationModerate(Double concentrationModerate) {
        this.concentrationModerate = concentrationModerate;
    }

    public int getSensitive() {
        return sensitive;
    }

    public void setSensitive(int sensitive) {
        this.sensitive = sensitive;
    }

    public Double getConcentrationSensitive() {
        return concentrationSensitive;
    }

    public void setConcentrationSensitive(Double concentrationSensitive) {
        this.concentrationSensitive = concentrationSensitive;
    }

    public int getUnhealthy() {
        return unhealthy;
    }

    public void setUnhealthy(int unhealthy) {
        this.unhealthy = unhealthy;
    }

    public Double getConcentrationUnhealthy() {
        return concentrationUnhealthy;
    }

    public void setConcentrationUnhealthy(Double concentrationUnhealthy) {
        this.concentrationUnhealthy = concentrationUnhealthy;
    }

    public int getVeryUnhealthy() {
        return veryUnhealthy;
    }

    public void setVeryUnhealthy(int veryUnhealthy) {
        this.veryUnhealthy = veryUnhealthy;
    }

    public Double getConcentrationVeryUnhealthy() {
        return concentrationVeryUnhealthy;
    }

    public void setConcentrationVeryUnhealthy(Double concentrationVeryUnhealthy) {
        this.concentrationVeryUnhealthy = concentrationVeryUnhealthy;
    }

    public int getHazardous() {
        return hazardous;
    }

    public void setHazardous(int hazardous) {
        this.hazardous = hazardous;
    }

    public Double getConcentrationHazardous() {
        return concentrationHazardous;
    }

    public void setConcentrationHazardous(Double concentrationHazardous) {
        this.concentrationHazardous = concentrationHazardous;
    }
}
