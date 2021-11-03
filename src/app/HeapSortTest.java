package app;

import Utils.*;
import algo.HeapSort;

public class HeapSortTest extends AbstractTest {
    private HeapSort h;

    public HeapSortTest(FileParser parser) {
        super(parser, "HeapSort");
        h = new HeapSort();
    }

    @Override
    public void runTests() {
        System.out.println();
        System.out.println("======= Commencing Heap Sort Test ========");

        double timeTaken1 = performDistanceSortThenRating();
        double timeTaken2 = performRatingSortThenDistance();

        if (parser.getFilteredRestaurants().length == 0) {
            return;
        }

        // Fancy printing of the benchmarking with Java
        // benchmarkWithJava();

        System.out.println("======= End of Heap Sort Test ========");
        System.out.println();

    }

    private double performDistanceSortThenRating() {
        double cur_lat = parser.getCurrLat();
        double cur_long = parser.getCurrLong();
        Restaurant[] allRestaurants = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        top_rated = h.pqSortDistance(allRestaurants, cur_lat, cur_long);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort distance = %.10fs", (sort_1_end - start_time) / 1000));

        if (top_rated.length == 0) {
            System.out.println("No restaurants found");
            return sort_1_end - start_time;
        }

        double sort_2_end = searchNearestAndBest();
        return sort_2_end - start_time;
    }

    private double performRatingSortThenDistance() {
        double currLat = parser.getCurrLat();
        double currLong = parser.getCurrLong();
        Restaurant[] allRestaurant = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        Restaurant[] result = h.pqSortRatingThenDistance(allRestaurant, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort by rating = %.10fs", (sort_1_end - start_time) / 1000));

        if (result.length == 0) {
            System.out.println("No restaurants found");
        }
        System.out.println(result[0]);
        return sort_1_end - start_time;
    }

}
