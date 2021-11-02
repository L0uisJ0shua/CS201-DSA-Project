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

        double sortTime1 = performDistanceSort();
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

    private double performRatingSort() {
        Map<String, Restaurant> allRestaurant = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        Restaurant[] result = h.pqSortRating(allRestaurant);

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort by rating = %.10fs", (sort_1_end - start_time) / 1000));

        if (result.length > 0) {
            System.out.println(result[0]);
            double sort_2_end = System.currentTimeMillis();
            return sort_2_end - start_time;

        } else {
            System.out.println("No restaurants found");
        }
        return sort_1_end - start_time;
    }

}
