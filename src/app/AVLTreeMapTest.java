package app;

import java.io.BufferedReader;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.*;

import Utils.LatLongComparison;
import algo.BucketSort;
import datastruct.AVLTreeMap;

public class AVLTreeMapTest {
    public static void main(String[] args) {
        System.out.println("%n");
        
        System.out.println("===== Now Running AVL TreeMap Test =====");
        
        // start here
        Gson gson = new Gson();

        // Make sure your yelp dataset is located within the same directory as your
        // project
        // but not within your project file
        Path path = Paths.get(System.getProperty("user.dir") + "/yelp_academic_dataset_business.json");

        Scanner sc = new Scanner(System.in);
        AVLTreeMap<String, Restaurant> allRestaurant = new AVLTreeMap<>();

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
                if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(), restaurant.getLongitude()) <= acceptableRange) {
                    allRestaurant.put(restaurant.getName(), restaurant);
                    System.out.println(restaurant.toString());
                }
                // System.out.println(restaurant.getHours().get("Monday"));
                // break;
            }

            System.out.println("Total time consumed to parse the entire dataset = "
                    + (System.currentTimeMillis() - time) / 1000.0);
            System.out.println(allRestaurant.size());

            // Bubble Sort
            // System.out.println("======= Commencing Bubble Sort Test ========");

            // double start_time = System.currentTimeMillis();
            // BucketSort b = new BucketSort();
            // Restaurant[] top_rated = b.bucketSortStars(allRestaurant);
            // Restaurant top_and_close = b.bubbleSortDistAndGet(top_rated, currLat, currLong);
            // System.out.println(top_and_close.toString());
            // System.out.println("Total Time take to sort = " + String.format("%.10f", (System.currentTimeMillis() - start_time)/1000));
            // System.out.println("\n====== End of Bubble Sort Run ========");
            
            
        } catch (IOException e) {
            e.getStackTrace();
        }

        System.out.println("===== End of AVL TreeMap Test =====");

    }

}