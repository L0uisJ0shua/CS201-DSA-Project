package app;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.*;

import Utils.*;

public class PriorityQueueTest extends AbstractInsertionTest {

    public void runTests() {
        System.out.println("===== Now Running Java Priority Queue Test =====");

        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        PriorityQueue<Restaurant> allRestaurants = new PriorityQueue<>(
                (r1, r2) -> r1.getName().compareTo(r2.getName()));

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            double startTime = System.currentTimeMillis();

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);

                // check if restaurant open
                try {
                    if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                            restaurant.getLongitude()) <= acceptableRange) {
                        allRestaurants.add(restaurant);
                    }
                } catch (Exception e) {
                    continue;
                }
            }

            double endTime = System.currentTimeMillis();

            System.out.printf("%nTime taken to parse dataset of size %d: %.10fs %n%n", allRestaurants.size(),
                    (endTime - startTime) / 1000);

        } catch (IOException e) {
            e.getStackTrace();
        }

        System.out.println("===== End of Java Priority Queue Test =====");
        System.out.println();
    }

}