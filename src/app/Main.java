package app;

import java.util.*;

import Utils.*;

public class Main {
    public static void main(String[] args) {

<<<<<<< HEAD
        Map<String, Restaurant> allRestaurants = new HashMap<>();
        FileParser fileParser = new FileParser(allRestaurants);
=======
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
>>>>>>> ed46108 (src/app/Main.java: Implemented an option for user to toggle whether they want to sort by rating or by distance)

        // start by running a test with a predefined location
        boolean isRandomised = false;
        System.out.println("Running initial test");
        runDistanceTest(isRandomised, fileParser);

<<<<<<< HEAD
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
=======
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
>>>>>>> ed46108 (src/app/Main.java: Implemented an option for user to toggle whether they want to sort by rating or by distance)

        int[] distanceTests = { 10, 100, 500, 1000, 1500 };
        for (int i : distanceTests) {
            fileParser.retrieveData(true, i);

<<<<<<< HEAD
            // First perform a test to only sort review. Then sort review then distance
            BubbleSortTest test = new BubbleSortTest(fileParser);
            test.runTests();
        }
=======
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
>>>>>>> ed46108 (src/app/Main.java: Implemented an option for user to toggle whether they want to sort by rating or by distance)
    }

}