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

class LatLongPair {
    private double currLat;
    private double currLong;

    public LatLongPair() {
        currLat = 39.778259;
        currLong = -105.417931;
    }

    public LatLongPair(double currLat, double currLong) {
        this.currLat = currLat;
        this.currLong = currLong;
    }

    public double getCurrLat() {
        return currLat;
    }

    public double getCurrLong() {
        return currLong;
    }
}

public class FileParser {

    private Map<String, Restaurant> allRestaurants = new HashMap<>();
    private Map<String, Restaurant> filteredRestaurants;
    private DateTimeComparator dateTimeComparator = new DateTimeComparator();
    private List<LatLongPair> latLongPairs = new ArrayList<>();
    private double acceptableRange;
    private int testNum;

    /**
     * Generate an arrayList of lat-long with the default values and n number of
     * random ones
     * 
     * @param numTests
     */
    public FileParser(final int numTests) {
        getAllData();
        generateRandomLatLong(numTests);
    }

    /**
     * Function to generate random values based on number of intended tests to be
     * ran
     * 
     * @param numTests
     */
    private void generateRandomLatLong(final int numTests) {
        latLongPairs.add(new LatLongPair());

        int totalSize = allRestaurants.size();
        if (allRestaurants == null || totalSize == 0 || numTests <= 1) {
            return;
        }

        Random R = new Random();
        List<String> keysArr = new ArrayList<>(allRestaurants.keySet());

        for (int i = 1; i < numTests; i++) {
            String randomRest = keysArr.get(R.nextInt(totalSize));
            Restaurant random = allRestaurants.get(randomRest);
            double randLat = random.getLatitude() + Math.random() * 10;
            double randLong = random.getLongitude() - Math.random() * 10;
            latLongPairs.add(new LatLongPair(randLat, randLong));
        }
    }

    /**
     * Save all the dataset that is relevant to the time the experiment is run by
     * taking into account all restaurants which are open at this particular hour
     * 
     */
    private void getAllData() {
        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
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

    /**
     * Function to obtain a filtered dataset based on the acceptableDistance
     * 
     * @param testNum         the index of test which is being ran
     * @param acceptableRange
     */
    public void retrieveData() {
        double currLat = latLongPairs.get(testNum).getCurrLat();
        double currLong = latLongPairs.get(testNum).getCurrLong();

        System.out.println("Retrieving data for all entries with distance less than " + acceptableRange + "km:");
        filteredRestaurants = new HashMap<>();

        double startTime = System.currentTimeMillis();
        for (Restaurant restaurant : allRestaurants.values()) {
            if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                    restaurant.getLongitude()) <= acceptableRange) {
                filteredRestaurants.put(restaurant.getName(), restaurant);
            }
        }

        double endTime = System.currentTimeMillis();
        System.out.println(String.format("Time to filter dataset of size %d: %.10fs", filteredRestaurants.size(),
                ((endTime - startTime) / 1000)));
    }

    /**
     * Function to insert into a tree and perform inorder traversal to obtain the
     * results in an array
     * 
     * @return
     */
    public double javaTreeSort() {
        double currLat = getCurrLat();
        double currLong = getCurrLong();

        Map<Double, Restaurant> sortedMap = new TreeMap<>();
        double startTime = System.currentTimeMillis();

        for (Restaurant restaurant : filteredRestaurants.values()) {
            double dist = restaurant.calculateDistanceFrom(currLat, currLong);
            sortedMap.put(dist, restaurant);
        }

        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    /**
     * Sort by rating based on reviews alone
     * 
     * @param restaurant
     * @return
     */
    public double javaArrSort(Restaurant[] restaurant) {
        double startTime = System.currentTimeMillis();

        Arrays.sort(restaurant);

        double endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    /**
     * Getters and setters below
     * 
     * @return
     */
    public Map<String, Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    public List<LatLongPair> getLatLongPairs() {
        return latLongPairs;
    }

    public void setAllRestaurants(Map<String, Restaurant> allRestaurants) {
        this.allRestaurants = allRestaurants;
    }

    public Map<String, Restaurant> getFilteredRestaurants() {
        return filteredRestaurants;
    }

    public double getAcceptableRange() {
        return acceptableRange;
    }

    public void setAcceptableRange(double acceptableRange) {
        this.acceptableRange = acceptableRange;
    }

    public int getTestNum() {
        return testNum;
    }

    public void setTestNum(int testNum) {
        this.testNum = testNum;
    }

    public double getCurrLat() {
        return latLongPairs.get(testNum).getCurrLat();
    }

    public double getCurrLong() {
        return latLongPairs.get(testNum).getCurrLong();
    }
}
