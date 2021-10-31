package app;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.*;

import Utils.LatLongComparison;
import datastruct.BasicTreeMap;

public class BasicTreeMapTest {
    public static void main(String[] args) {
        System.out.println("===== Now Running Basic TreeMap Test =====");

        // start here
        Gson gson = new Gson();

        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        Scanner sc = new Scanner(System.in);
        BasicTreeMap<String, Restaurant> allRestaurant = new BasicTreeMap<>();

        System.out.print("Acceptable distance(Km) --->  ");
        double acceptableRange = sc.nextDouble();
        sc.nextLine();
        // Hard code your current location's latitude and longitude here
        System.out.print("We recommend this: 39.778259,-105.417931. Use this? [Y/n] ");
        String res = sc.nextLine();
        double currLat;
        double currLong;
        System.out.println(res);
        if (res.equals("n") || res.equals("N")) {
            System.out.print("Your current location latitude ---> ");
            currLat = sc.nextDouble();

            System.out.print("Your current location longtitude ---> ");
            currLong = sc.nextDouble();
            sc.nextLine();
        } else {
            currLat = 39.778259;
            currLong = -105.417931;
        }
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

        } catch (IOException e) {
            e.getStackTrace();
        }

        System.out.println("===== End of Basic TreeMap Test =====");
    }

}