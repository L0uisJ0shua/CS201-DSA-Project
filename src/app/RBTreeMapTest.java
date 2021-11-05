package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import com.google.gson.*;

import Utils.DateTimeComparator;
import datastruct.RBTreeMap;

public class RBTreeMapTest {

    public void runTests() {
        System.out.println("===== Now Running Custom Red Black TreeMap Test =====");

        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");
        int hour = LocalDateTime.now().getHour();
        int minute = LocalDateTime.now().getMinute();
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().toString();
        String cap = dayOfWeek.substring(0, 1) + dayOfWeek.substring(1).toLowerCase();

        RBTreeMap<String, Restaurant> allRestaurants = new RBTreeMap<>();
        DateTimeComparator dateTimeComparator = new DateTimeComparator();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            double startTime = System.currentTimeMillis();

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

            double endTime = System.currentTimeMillis();

            System.out.printf("%nTime taken to parse dataset of size %d: %.10fs %n%n", allRestaurants.size(),
                    (endTime - startTime) / 1000);

        } catch (IOException e) {
            e.getStackTrace();
        }

        System.out.println("===== End of Custom Red Black TreeMap Test =====");
        System.out.println();
    }

}