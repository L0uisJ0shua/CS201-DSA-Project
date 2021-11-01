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

import Utils.DateTimeComparator;
import Utils.LatLongComparison;
import algo.BucketSort;
import algo.MergeSort;
import algo.QuickSort;

public class Main {
    public static void main(String[] args) {
        // start here
        Gson gson = new Gson();

        // Make sure your yelp dataset is located within the same directory as your
        // project but not within your project file
        Path path = Paths.get("../yelp_academic_dataset_business.json");

        Scanner sc = new Scanner(System.in);
        Map<String, Restaurant> allRestaurant = new TreeMap<>();

        System.out.print("Acceptable distance(Km) --->  ");
        double acceptableRange = sc.nextDouble();
        sc.nextLine();

        System.out.print("Do you prefer a higer rating restaurant? [Y/n] --->  ");
        String sortingOption = sc.nextLine();

        // Hard code your current location's latitude and longitude here
        System.out.print("We recommend this: 39.778259,-105.417931. Use this? [Y/n] ");
        String res = sc.nextLine();
        double currLat;
        double currLong;

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
            double time = System.currentTimeMillis();
            DateTimeComparator dateTimeComparator = new DateTimeComparator();

            System.out.println("Parsing JSON dataset into a tree map...");
            while ((line = reader.readLine()) != null) {
                try {
                    Restaurant restaurant = gson.fromJson(line, Restaurant.class);

                    // Comparing the opening hours of the restaurants
                    if (restaurant.getHours().containsKey(dateTimeComparator.getDay())
                            && dateTimeComparator.isOpen(restaurant.getHours().get(dateTimeComparator.getDay()))) {

                        if (LatLongComparison.distanceDifference(currLat, currLong, restaurant.getLatitude(),
                                restaurant.getLongitude()) <= acceptableRange) {
                            allRestaurant.put(restaurant.getName(), restaurant);
                            // System.out.println(restaurant.toString());
                        }
                        // System.out.println(restaurant.getHours().get("Monday"));
                        // break;
                    }
                } catch (NullPointerException ex) {

                }
            }
            System.out.println("Completed JSON -> tree map conversion.");
            System.out.println("Total time consumed to parse the entire dataset = "
                    + String.format("%.10f", (System.currentTimeMillis() - time) / 1000) + "\n\n");

            System.out.println("Total available data --> " + allRestaurant.size());

            // Bubble Sort
            System.out.println("======= Commencing Bubble Sort Test ========");

            double start_time = System.currentTimeMillis();
            BucketSort b = new BucketSort();
            Restaurant[] top_rated = b.bucketSortStars(allRestaurant);
            Restaurant top_and_close = b.bubbleSortDistAndGet(top_rated, currLat, currLong);
            System.out.println(top_and_close.toString());
            System.out.println("Total Time take to sort = "
                    + String.format("%.10f", (System.currentTimeMillis() - start_time) / 1000));
            System.out.println("\n====== End of Bubble Sort Run ========\n");

            // Merge Sorting
            performMergeSort(sortingOption, allRestaurant, currLat, currLong);

            //Quick Sorting
            performQuickSort(sortingOption, allRestaurant, currLat, currLong);

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public static void performMergeSort(String sortingOption, Map<String, Restaurant> restaurant, double currLat,
            double currLong) {
        if (restaurant.size() == 0) {
            return;
        }

        // Initialise the merge sort function
        MergeSort mergeSort = new MergeSort();

        System.out.println("======= Commencing Merge Sort Test ========");
        double startTime = System.currentTimeMillis();

        // First we have to parse the map into an array
        Restaurant[] restaurants = restaurant.values().toArray(new Restaurant[restaurant.values().size()]);

        System.out.println("Total size of data required to perform Merge Sort = " + restaurants.length);

        if (sortingOption.equals("Y") || sortingOption.equals("y")) {
            System.out.println("Sorting data based on rating...");
            // Perform Merge Sort by highest rating and closest restaurant
            mergeSort.mergeSortByRating(restaurants, 0, restaurants.length - 1, currLat, currLong);

        } else {

            System.out.println("Sorting data based on distance...");
            // Perform Merge Sort by distance on the given array
            mergeSort.mergeSortByDistance(restaurants, 0, restaurants.length - 1, currLat, currLong);
        }

        // Display time taken to perform merge sort on the data given
        System.out.println("Nearest restaurant availble = " + restaurants[0]);
        System.out.println("Total time taken to perform Merge Sort = "
                + String.format("%.10f", (System.currentTimeMillis() - startTime) / 1000));
        System.out.println("\n====== End of Merge Sort Test ========");
    }

    public static void performQuickSort(String sortingOption, Map<String, Restaurant> restaurant, double currLat,
            double currLong) {
        if (restaurant.size() == 0) {
            return;
        }

        System.out.println("======= Commencing Quick Sort Test ========");
        double startTime = System.currentTimeMillis();

        // First we have to parse the map into an array
        Restaurant[] restaurants = restaurant.values().toArray(new Restaurant[restaurant.values().size()]);

        System.out.println("Total size of data required to perform Quick Sort = " + restaurants.length);

        if (sortingOption.equals("Y") || sortingOption.equals("y")) {
            System.out.println("Sorting data based on rating...");
            // Perform Merge Sort by highest rating and closest restaurant
            QuickSort.quickSortByRating(restaurants, 0, restaurants.length - 1, currLat, currLong);

        } else {

            System.out.println("Sorting data based on distance...");
            // Perform Merge Sort by distance on the given array
            QuickSort.quickSortByDistance(restaurants, 0, restaurants.length - 1, currLat, currLong);
        }

        // Display time taken to perform merge sort on the data given
        System.out.println("Nearest restaurant availble = " + restaurants[0]);
        System.out.println("Total time taken to perform Quick Sort = "
                + String.format("%.10f", (System.currentTimeMillis() - startTime) / 1000));
        System.out.println("\n====== End of Quick Sort Test ========");
    }


}