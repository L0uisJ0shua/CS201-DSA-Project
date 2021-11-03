package app;

import Utils.FileParser;
import algo.BucketSort;

import java.util.Map;

public class BucketSortTest extends AbstractTest {

    private FileParser data;
    private BucketSort b;

    public BucketSortTest(FileParser data) {
        this.data = data;
        b = new BucketSort();
    }

    private double performSortUsingDistance() {
        double start_time = System.currentTimeMillis();
        Map<String, Restaurant> Restaurant_map = data.getFilteredRestaurants();
        Restaurant[] allRestaurant = Restaurant_map.values().toArray(new Restaurant[Restaurant_map.values().size()]);

        Restaurant[] top_distance = b.bucketSortDistAndGet(allRestaurant, data.getCurrLat(), data.getCurrLong());

        double sort_1_end = System.currentTimeMillis();

        System.out
                .println(String.format("Total Time take to sort distance = %.10fs", (sort_1_end - start_time) / 1000));

        if (top_distance.length == 0) {
            System.out.println("No restaurant found");
        } else {
            System.out.println(top_distance[top_distance.length - 1].toString());
        }

        return sort_1_end - start_time;
    }

    private double performSortUsingRatingAndDistance() {

        double start_time = System.currentTimeMillis();
        Map<String, Restaurant> Restaurant_map = data.getFilteredRestaurants();
        Restaurant[] allRestaurant = Restaurant_map.values().toArray(new Restaurant[Restaurant_map.values().size()]);

        double currLat = data.getCurrLat();
        double currLong = data.getCurrLong();

        Restaurant[] top_rated = b.bucketSortStars(allRestaurant);
        Restaurant[] top_and_close = b.bucketSortDistAndGet(top_rated, currLat, currLong);

        double sort_2_end = System.currentTimeMillis();
        double result = sort_2_end - start_time;

        System.out.println(String.format("Total Time take to sort ratings then distance = %.10fs", result / 1000));
        System.out.println(top_and_close[0]);

        return result;
    }

    @Override
    public void runTests() {
        System.out.println();
        System.out.println("======= Commencing Bucket Sort Test ========");
        // Runs the tests.
        double sortTime1 = performSortUsingDistance();
        System.out.println();
        double sortTime2 = performSortUsingRatingAndDistance();
        System.out.println();

        // iterate through entire data set to find the top x

        if (data.getFilteredRestaurants().size() == 0) {
            return;
        }

        Restaurant[] restArr = data.getFilteredRestaurants().values().toArray(new Restaurant[0]);
        double javaArrBenchmark = data.javaArrSort(restArr);
        System.out.println("================Benchmark with Java=============");
        System.out.println(String.format("Benchmark rating sorting with Java TimSort: %.10fs", javaArrBenchmark));
        System.out
                .println(String.format("Current sort takes %.10f more seconds", (sortTime1 - javaArrBenchmark) / 1000));
        System.out.println("================================================");
        System.out.println();

        double javaTreeBenchmark = data.javaTreeSort();
        System.out.println("================Benchmark with Java=============");
        System.out.println(String.format("Benchmark distance sorting with Java RBTree: %.10fs", javaTreeBenchmark));
        System.out.println(
                String.format("Current sort takes %.10f more seconds", (sortTime2 - javaTreeBenchmark) / 1000));
        System.out.println("================================================");
        System.out.println();

        System.out.println("====== End of Bucket Sort Test ========");
        System.out.println();

    }

}
