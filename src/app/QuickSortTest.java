package app;

import Utils.FileParser;
import algo.QuickSort;
import java.util.*;

public class QuickSortTest implements AbstractTest {
    private FileParser parser;

    public QuickSortTest(FileParser fileParser) {
        this.parser = fileParser;
    }

    @Override
    public void runTests() {
        System.out.println();
        System.out.println("======= Commencing Quick Sort Test ========");

        double sortTime1 = performDistanceSort();
        System.out.println();
        double sortTime2 = performRatingSort();

        if (parser.getFilteredRestaurants().size() == 0) {
            return;
        }

        Restaurant[] restArr = parser.getFilteredRestaurants().values().toArray(new Restaurant[0]);
        double javaArrBenchmark = parser.javaArrSort(restArr);
        System.out.println("================Benchmark with Java=============");
        System.out.println(String.format("Benchmark rating sorting with Java TimSort: %.10fs", javaArrBenchmark));
        System.out
                .println(String.format("Current sort takes %.10f more seconds", (sortTime1 - javaArrBenchmark) / 1000));
        System.out.println("================================================");
        System.out.println();

        double javaTreeBenchmark = parser.javaTreeSort();
        System.out.println("================Benchmark with Java=============");
        System.out.println(String.format("Benchmark distance sorting with Java RBTree: %.10fs", javaTreeBenchmark));
        System.out.println(
                String.format("Current sort takes %.10f more seconds", (sortTime2 - javaTreeBenchmark) / 1000));
        System.out.println("================================================");
        System.out.println();

        System.out.println("======= End of Merge Sort Test ========");
        System.out.println();

    }

    private double performDistanceSort() {
        Map<String, Restaurant> allRestaurant = parser.getFilteredRestaurants();
        double cur_lat = parser.getCurrLat();
        double cur_long = parser.getCurrLong();
        int n = allRestaurant.size();
        Restaurant[] array = allRestaurant.values().toArray(new Restaurant[0]);

        double start_time = System.currentTimeMillis();

        QuickSort.quickSortByDistance(array, 0, n - 1, cur_lat, cur_long);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort distance = %.10fs", (sort_1_end - start_time) / 1000));

        if (array.length > 0) {
            double sort_2_end = searchNearestAndBest(array);
            return sort_2_end - start_time;

        } else {
            System.out.println("No restaurants found");
        }
        return sort_1_end - start_time;
    }

    private double performRatingSort() {
        Map<String, Restaurant> allRestaurant = parser.getFilteredRestaurants();
        int n = allRestaurant.size();
        Restaurant[] array = allRestaurant.values().toArray(new Restaurant[0]);
        double cur_lat = parser.getCurrLat();
        double cur_long = parser.getCurrLong();

        double start_time = System.currentTimeMillis();

        QuickSort.quickSortByRating(array, 0, n - 1, cur_lat, cur_long);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort by rating = %.10fs", (sort_1_end - start_time) / 1000));

        if (array.length > 0) {
            System.out.println(array[0]);
            double sort_2_end = System.currentTimeMillis();
            return sort_2_end - start_time;

        } else {
            System.out.println("No restaurants found");
        }
        return sort_1_end - start_time;
    }

    private double searchNearestAndBest(Restaurant[] top_rated) {
        for (Restaurant r : top_rated) {
            if (r.getStars() >= 5.0) {
                System.out.println(r);
                break;
            }
        }
        return System.currentTimeMillis();
    }
}
