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
    private DateTimeComparator dateTimeComparator = new DateTimeComparator();
    private List<LatLongPair> latLongPairs = new ArrayList<>();
    private Restaurant[][][] filteredRestaurants;
    private int[] acceptableRanges;
    private int currRound;
    private int testCount;
    private int currTestNum;

    /**
     * Generate an arrayList of lat-long with the default values and n number of
     * random ones
     * 
     * @param numTests
     */
    public FileParser(final int numTests, final int[] distanceTests) {
        acceptableRanges = distanceTests;
        testCount = numTests;
        filteredRestaurants = new Restaurant[numTests][distanceTests.length][];

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
     */
    public void retrieveData() {
        for (int i = 0; i < testCount; i++) {
            // Set it to the respective tests. Tests differ by long-lat. Rounds differ by
            // distance
            currTestNum = i;

            for (int j = 0; j < acceptableRanges.length; j++) {
                Restaurant[] filteredRestaurant = filterRestaurantByDistance(acceptableRanges[j]);
                filteredRestaurants[i][j] = filteredRestaurant;
            }
        }
    }

    /**
     * Filter restaurant by distance and save the result into the main array
     * 
     * @param distance
     */
    private Restaurant[] filterRestaurantByDistance(int distance) {
        double currLat = getCurrLat();
        double currLong = getCurrLong();
        List<Restaurant> restaurantList = new ArrayList<>();

        System.out.printf("Retrieving data for all entries with distance less than %d km for lat=%f, long=%f %n",
                distance, currLat, currLong);

        double startTime = System.currentTimeMillis();

        for (Restaurant restaurant : allRestaurants.values()) {
            if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                    restaurant.getLongitude()) <= distance) {
                restaurantList.add(restaurant);
            }
        }

        Restaurant[] filteredRestaurants = restaurantList.toArray(new Restaurant[0]);

        double endTime = System.currentTimeMillis();
        System.out.printf("Time to filter dataset of size %d: %.10fs %n", filteredRestaurants.length,
                ((endTime - startTime) / 1000));
        System.out.println();

        return filteredRestaurants;
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

    public Restaurant[] getFilteredRestaurants() {
        return filteredRestaurants[currTestNum][currRound].clone();
    }

    public Restaurant[][][] getAllFilteredRestaurants() {
        return filteredRestaurants;
    }

    public int getAcceptableRange() {
        return acceptableRanges[currRound];
    }

    public int[] getAcceptableRanges() {
        return acceptableRanges;
    }

    public int getCurrTestNum() {
        return currTestNum;
    }

    public void setCurrTestNum(int testNum) {
        currTestNum = testNum;
    }

    public double getCurrLat() {
        return latLongPairs.get(currTestNum).getCurrLat();
    }

    public double getCurrLong() {
        return latLongPairs.get(currTestNum).getCurrLong();
    }

    public int getTestCount() {
        return testCount;
    }

    public void setRound(int round) {
        currRound = round;
    }

    public int getCurrRound() {
        return currRound;
    }
}
