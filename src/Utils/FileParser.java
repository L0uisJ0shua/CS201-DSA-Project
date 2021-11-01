package Utils;

import com.google.gson.*;
import app.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileParser {

    private Map<String, Restaurant> allRestaurants;
    private Map<String, Restaurant> filteredRestaurants;
    private double acceptableRange = 5000;
    private double currLat = 39.778259;
    private double currLong = -105.417931;

    public FileParser(Map<String, Restaurant> container) {
        allRestaurants = container;
        getAllData();
    }

    // Randomise lat long
    public void randomise() {
        int totalSize = allRestaurants.size();
        if (allRestaurants == null || totalSize == 0) {
            return;
        }

        Random R = new Random();
        List<String> keysArr = new ArrayList<>(allRestaurants.keySet());
        String randomRest = keysArr.get(R.nextInt(totalSize));
        Restaurant random = allRestaurants.get(randomRest);
        currLat = random.getLatitude() + Math.random() * 10;
        currLong = random.getLongitude() - Math.random() * 10;
    }

    private void getAllData() {
        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);
                allRestaurants.put(restaurant.getName(), restaurant);
            }

            System.out.println("Total data size involved: " + allRestaurants.size());

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void retrieveData(boolean filter, int acceptableRange) {
        this.acceptableRange = acceptableRange;

        // Insert entry if there is no filtering intended or that the distance is lower
        System.out.println("Retrieving data for all entries with distance less than " + acceptableRange + "km:");
        filteredRestaurants = new HashMap<>();

        double startTime = System.currentTimeMillis();
        for (Restaurant restaurant : allRestaurants.values()) {
            if (!filter || LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                    restaurant.getLongitude()) <= acceptableRange) {
                filteredRestaurants.put(restaurant.getName(), restaurant);
            }
        }

        double endTime = System.currentTimeMillis();
        System.out.println(String.format("Time to filter dataset of size %d: %.10fs", filteredRestaurants.size(),
                ((endTime - startTime) / 1000)));
    }

    public double javaTreeSort() {
        Map<Double, Restaurant> sortedMap = new TreeMap<>();
        double startTime = System.currentTimeMillis();

        for (Restaurant restaurant : filteredRestaurants.values()) {
            double dist = restaurant.calculateDistanceFrom(currLat, currLong);
            sortedMap.put(dist, restaurant);
        }

        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    public double javaArrSort(Restaurant[] top_rated) {
        double startTime = System.currentTimeMillis();

        Arrays.sort(top_rated);

        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    public Map<String, Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    public void setAllRestaurants(Map<String, Restaurant> allRestaurants) {
        this.allRestaurants = allRestaurants;
    }

    public double getAcceptableRange() {
        return acceptableRange;
    }

    public Map<String, Restaurant> getFilteredRestaurants() {
        return filteredRestaurants;
    }

    public void setFilteredRestaurants(Map<String, Restaurant> filteredRestaurants) {
        this.filteredRestaurants = filteredRestaurants;
    }

    public void setAcceptableRange(double acceptableRange) {
        this.acceptableRange = acceptableRange;
    }

    public double getCurrLat() {
        return currLat;
    }

    public void setCurrLat(double currLat) {
        this.currLat = currLat;
    }

    public double getCurrLong() {
        return currLong;
    }

    public void setCurrLong(double currLong) {
        this.currLong = currLong;
    }
}
