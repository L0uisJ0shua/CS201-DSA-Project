package app;

import java.util.*;
import Utils.LatLongComparison;

public class Restaurant implements Comparable<Restaurant> {
    private float stars;
    private String name;
    private double latitude;
    private double longitude;
    private String categories;
    private Map<String, String> hours;

    public Restaurant() {

    }

    public Restaurant(float stars, String name, double latitude, double longitude, String categories,
            Map<String, String> hours) {
        this.stars = stars;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
        this.hours = hours;
    }

    @Override
    public String toString() {
        return "Restaurant [categories=" + categories + ", hours=" + hours + ", latitude=" + latitude + ", longitude="
                + longitude + ", name=" + name + ", stars=" + stars + "]";
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public Map<String, String> getHours() {
        return hours;
    }

    public void setHours(Map<String, String> hours) {
        this.hours = hours;
    }

    public boolean compareRating(Restaurant r2) {
        if (this.getStars() >= r2.getStars()) {
            return true;
        }
        return false;
    }

    public double calculateDistanceFrom(double origin_lat, double origin_long) {
        return LatLongComparison.distanceDifference(origin_lat, origin_long, this.latitude, this.longitude);
    }

    @Override
    public int compareTo(Restaurant arg1) {
        return Math.round(arg1.getStars() - stars);
    }

}
