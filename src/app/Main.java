package app;

import java.io.BufferedReader;

// import algo.*;
// import datastruct.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        // start here
        Gson gson = new Gson();
        Path path = Paths.get("./yelp_academic_dataset_business.json");
        Map<String, Restaurant> allRestaurant = new TreeMap<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);
                allRestaurant.put(restaurant.getName(), restaurant);
                // System.out.println(restaurant.toString());
                // System.out.println(restaurant.getHours().get("Monday"));
                // break;
            }

            System.out.println(allRestaurant.size());
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}