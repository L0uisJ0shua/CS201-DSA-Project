package app;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.*;

import Utils.*;
import algo.BucketSort;

public class Main {
    public static void main(String[] args) {

        // start here
        Gson gson = new Gson();
        Console console = new Console();

        Map<String, Double> userInput = console.getUserData();
        double acceptableRange = userInput.get("distance");
        double currLat = userInput.get("lat");
        double currLong = userInput.get("long");

        // Make sure your yelp dataset is located within the same directory as your
        // project but not within your project file
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        Map<String, Restaurant> allRestaurant = new TreeMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            long time = System.currentTimeMillis();

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);
                if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                        restaurant.getLongitude()) <= acceptableRange) {
                    allRestaurant.put(restaurant.getName(), restaurant);
                    System.out.println(restaurant.toString());
                }
            }

            System.out.println("Total time consumed to parse the entire dataset = "
                    + (System.currentTimeMillis() - time) / 1000.0);
            System.out.println(allRestaurant.size());

            // Bubble Sort
            System.out.println("======= Commencing Bubble Sort Test ========");

            double start_time = System.currentTimeMillis();
            BucketSort b = new BucketSort();
            Restaurant[] top_rated = b.bucketSortStars(allRestaurant);
            Restaurant top_and_close = b.bubbleSortDistAndGet(top_rated, currLat, currLong);
            System.out.println(top_and_close.toString());
            System.out.println("Total Time take to sort = "
                    + String.format("%.10f", (System.currentTimeMillis() - start_time) / 1000));
            System.out.println("\n====== End of Bubble Sort Run ========");
        } catch (IOException e) {
            e.getStackTrace();
        }

        System.out.println("===== End of Default TreeMap Test =====");

    }

}