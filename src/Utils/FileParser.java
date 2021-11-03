package Utils;

import com.google.gson.*;
import app.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

public class FileParser {

    private Map<String, Restaurant> allRestaurants;
    private Map<String, Restaurant> filteredRestaurants;
    private DateTimeComparator dateTimeComparator;
    private double acceptableRange = 5000;
    private double currLat = 39.778259;
    private double currLong = -105.417931;
    private double[] randomLatLong = new double[4];

    public FileParser() {
        allRestaurants = new HashMap<>();
        dateTimeComparator = new DateTimeComparator();
        getAllData();
        generateRandomLatLong();
    }

    private void generateRandomLatLong() {
        int totalSize = allRestaurants.size();
        if (allRestaurants == null || totalSize == 0) {
            return;
        }

        Random R = new Random();
        List<String> keysArr = new ArrayList<>(allRestaurants.keySet());

        for (int i = 0; i < 2; i++) {
            String randomRest = keysArr.get(R.nextInt(totalSize));
            Restaurant random = allRestaurants.get(randomRest);
            randomLatLong[i * 2] = random.getLatitude() + Math.random() * 10;
            randomLatLong[(i * 2) + 1] = random.getLongitude() - Math.random() * 10;
        }
    }

    // Randomise lat long
    public void randomise(int i) {
        currLat = randomLatLong[i * 2];
        currLong = randomLatLong[(i * 2) + 1];
    }

    public void resetValues() {
        currLat = 39.778259;
        currLong = -105.417931;
    }

    private void getAllData() {
        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        // can hardcode also for testing
        // int hour = 12;
        // int minute = 30;
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
        String cap = dayOfWeek.substring(0, 1) + dayOfWeek.substring(1).toLowerCase();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);

                // check if restaurant open
                try {
                    String operatingHrs = restaurant.getHours().get(cap);
                    if (dateTimeComparator.isOpen(operatingHrs, hour, minute)) {
                        allRestaurants.put(restaurant.getName(), restaurant);
                    }
                } catch (Exception e) {
                    continue;
                }
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
