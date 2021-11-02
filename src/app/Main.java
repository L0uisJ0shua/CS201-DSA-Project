package app;

import java.util.*;

import Utils.*;

public class Main {
    public static void main(String[] args) {

        Map<String, Restaurant> allRestaurants = new HashMap<>();
        FileParser fileParser = new FileParser(allRestaurants);

        // start by running a test with a predefined location
        boolean isRandomised = false;
        System.out.println("Running initial test");
        runDistanceTest(isRandomised, fileParser);

        // Run 3 more randomised tests with randomised location
        isRandomised = true;
        for (int i = 0; i < 3; i++) {
            System.out.println("Running randomised tests");
            runDistanceTest(isRandomised, fileParser);
        }

    }

    private static void runDistanceTest(boolean randomise, FileParser fileParser) {
        if (randomise) {
            fileParser.randomise();
        }

        int[] distanceTests = { 10, 100, 500, 1000, 1500 };
        for (int i : distanceTests) {
            fileParser.retrieveData(true, i);

            // First perform a test to only sort review. Then sort review then distance
            BubbleSortTest test = new BubbleSortTest(fileParser);
            test.runTests();
        }
    }

    // public static void performQuickSort(String sortingOption, Map<String, Restaurant> restaurant, double currLat,
    //         double currLong) {
    //     if (restaurant.size() == 0) {
    //         return;
    //     }

    //     System.out.println("======= Commencing Quick Sort Test ========");
    //     double startTime = System.currentTimeMillis();

    //     // First we have to parse the map into an array
    //     Restaurant[] restaurants = restaurant.values().toArray(new Restaurant[restaurant.values().size()]);

    //     System.out.println("Total size of data required to perform Quick Sort = " + restaurants.length);

    //     if (sortingOption.equals("Y") || sortingOption.equals("y")) {
    //         System.out.println("Sorting data based on rating...");
    //         // Perform Merge Sort by highest rating and closest restaurant
    //         QuickSort.quickSortByRating(restaurants, 0, restaurants.length - 1, currLat, currLong);

    //     } else {

    //         System.out.println("Sorting data based on distance...");
    //         // Perform Merge Sort by distance on the given array
    //         QuickSort.quickSortByDistance(restaurants, 0, restaurants.length - 1, currLat, currLong);
    //     }

    //     // Display time taken to perform merge sort on the data given
    //     System.out.println("Nearest restaurant availble = " + restaurants[0]);
    //     System.out.println("Total time taken to perform Quick Sort = "
    //             + String.format("%.10f", (System.currentTimeMillis() - startTime) / 1000));
    //     System.out.println("\n====== End of Quick Sort Test ========");
    // }


}