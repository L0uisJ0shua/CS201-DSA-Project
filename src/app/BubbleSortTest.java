package app;

import Utils.FileParser;
import algo.BucketSort;

import java.util.Map;

public class BubbleSortTest {

    private FileParser data;
    private BucketSort b;
    private Restaurant[] top_rated;

    public BubbleSortTest(FileParser data) {
        this.data = data;
        b = new BucketSort();
    }

    private double performSortUsingRating(boolean sortDistance) {
        System.out.println();
        System.out.println("======= Commencing Bubble Sort Test ========");
        double start_time = System.currentTimeMillis();

        Map<String, Restaurant> allRestaurant = data.getFilteredRestaurants();

        top_rated = b.bucketSortStars(allRestaurant);

        double sort_1_end = System.currentTimeMillis();

        System.out.println(String.format("Total Time take to sort ratings = %.10fs", (sort_1_end - start_time) / 1000));

        /**
         * Only done if the user requires it. Else, just print the top of the list. This
         * is because bubble sort is stable, meaning the sort will honor the previous
         * sort order, allowing for sort chaining
         */
        double res = sort_1_end - start_time;
        if (sortDistance) {
            performSortUsingRatingAndDistance();

            double sort_2_end = System.currentTimeMillis();
            res = sort_2_end - sort_1_end;
            System.out.println(String.format("Total Time take to sort distance = %.10fs", res / 1000));

            System.out.println(String.format("Total Time for both = %.10fs", (sort_2_end - start_time) / 1000));
        } else {
            if (top_rated.length == 0) {
                System.out.println("No restaurant found");
                return 0;
            } else {
                System.out.println(top_rated[top_rated.length - 1].toString());
            }
        }

        System.out.println("====== End of Bubble Sort Test ========");
        System.out.println();

        return res;
    }

    private void performSortUsingRatingAndDistance() {
        if (top_rated.length == 0) {
            System.out.println("No restaurant found");
            return;
        }

        double currLat = data.getCurrLat();
        double currLong = data.getCurrLong();

        Restaurant[] top_and_close = b.bubbleSortDistAndGet(top_rated, currLat, currLong);
        System.out.println(top_and_close[0].toString());
    }

    public void runTests() {
        double sortTime1 = performSortUsingRating(false);
        double sortTime2 = performSortUsingRating(true);

        // iterate through entire data set to find the top x

        if (data.getFilteredRestaurants().size() == 0) {
            return;
        }

        double javaArrBenchmark = data.javaArrSort(top_rated);
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

    }

}
