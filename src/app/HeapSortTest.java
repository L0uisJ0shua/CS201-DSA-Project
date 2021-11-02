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
        System.out.println();
        System.out.println("======= Commencing Heap Sort Test ========");

        performDistanceSort();

        System.out.println("======= End of Heap Sort Test ========");
        System.out.println();

    }

    private double performDistanceSort() {
        Map<String, Restaurant> allRestaurant = parser.getFilteredRestaurants();
        double cur_lat = parser.getCurrLat();
        double cur_long = parser.getCurrLong();

        double start_time = System.currentTimeMillis();

        top_rated = h.pqSortDistance(allRestaurant, cur_lat, cur_long);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort distance = %.10fs", (sort_1_end - start_time) / 1000));

        if (top_rated.length > 0) {
            double sort_2_end = searchNearestAndBest(top_rated);
            return sort_2_end - start_time;

        } else {
            System.out.println("No restaurants found");
        }
        return sort_1_end - start_time;
    }

    /**
     * Just go through top down and look for the first 5 star one We can only do
     * this because heap sort is not stables
     * 
     * @param top_rated
     * @return
     */
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
