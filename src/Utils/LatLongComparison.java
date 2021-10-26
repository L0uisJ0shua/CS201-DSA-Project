package Utils;

import java.lang.Math;

public class LatLongComparison {
    
    public static double distanceDifference(double currLat, double currLong, double restaurantLat, double restaurantLong) {

        // Conversion of latitudes and longitude to radians
        // since the earth is a sphere and not FLAT!!!
        currLat = Math.toRadians(currLat);
        currLong = Math.toRadians(currLong);
        restaurantLat = Math.toRadians(restaurantLat);
        restaurantLong = Math.toRadians(restaurantLong);

        // Using the Haversine formula to compute the distance in kilometers
        double distanceLong = restaurantLong - currLong;
        double distanceLat = restaurantLat - currLat;
        double a = Math.pow(Math.sin(distanceLat / 2), 2)
                 + Math.cos(currLat) * Math.cos(restaurantLat)
                 * Math.pow(Math.sin(distanceLong / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius constant of the earth in Kilometers
        double radiusInKm = 6371;

        return c * radiusInKm;
    }
}
