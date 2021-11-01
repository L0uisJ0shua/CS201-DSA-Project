package app;

import java.util.Map;

import Utils.FileParser;
import algo.HeapSort;

public class HeapSortTest implements AbstractTest {
    private FileParser parser;
    private HeapSort h;
    private Restaurant[] top_rated;

    public HeapSortTest(FileParser parser) {
        this.parser = parser;
        h = new HeapSort();
    }

    @Override
    public void runTests() {
        performRatingSort();
    }

    private double performRatingSort() {
        System.out.println();
        System.out.println("======= Commencing Heap Sort Test ========");
        double start_time = System.currentTimeMillis();

        Map<String, Restaurant> allRestaurant = parser.getFilteredRestaurants();
        double cur_lat = parser.getCurrLat();
        double cur_long = parser.getCurrLong();

        top_rated = h.pqSortDistance(allRestaurant, cur_lat, cur_long);

        double sort_1_end = System.currentTimeMillis();

        if (top_rated.length > 0) {
            System.out.println(top_rated[0]);
        } else {
            System.out.println("No restaurants found");
        }
        System.out.println(String.format("Total Time take to sort ratings = %.10fs", (sort_1_end - start_time) / 1000));

        return sort_1_end - start_time;
    }
}
