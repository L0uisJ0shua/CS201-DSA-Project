package Utils;

import com.google.gson.*;
import app.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FileParser {

    private Map<String, Restaurant> allRestaurants;

    public FileParser(Map<String, Restaurant> container) {
        allRestaurants = container;
    }

    public void retrieveData(boolean filter, Console options) {
        Gson gson = new Gson();
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        double acceptableRange = options.getAcceptableRange();
        double currLat = options.getCurrLat();
        double currLong = options.getCurrLong();

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            long time = System.currentTimeMillis();

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);

                // Insert entry if there is no filtering intended or that the distance is lower
                if (!filter || LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                        restaurant.getLongitude()) <= acceptableRange) {
                    allRestaurants.put(restaurant.getName(), restaurant);
                }
            }

            System.out.println("Total data size involved: " + allRestaurants.size());

            System.out.println("Total time consumed to parse the entire dataset = "
                    + (System.currentTimeMillis() - time) / 1000.0);

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public Map<String, Restaurant> getAllRestaurants() {
        return allRestaurants;
    }

    public void setAllRestaurants(Map<String, Restaurant> allRestaurants) {
        this.allRestaurants = allRestaurants;
    }
}
