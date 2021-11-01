package app;

import Utils.FileParser;
import algo.BucketSort;

import java.util.Map;

import Utils.*;

public class BubbleSortTest {

    private FileParser data;
    private Console input;
    private BucketSort b;

    public BubbleSortTest(FileParser data, Console input) {
        this.data = data;
        this.input = input;
        b = new BucketSort();
    }

    public void performSortUsingRating(boolean sortDistance) {
        System.out.println();
        System.out.println("======= Commencing Bubble Sort Test ========");
        double start_time = System.currentTimeMillis();

        Map<String, Restaurant> allRestaurant = data.getAllRestaurants();

        Restaurant[] top_rated = b.bucketSortStars(allRestaurant);

        double sort_1_end = System.currentTimeMillis();

        System.out.println(String.format("Total Time take to sort ratings = %.10fs", (sort_1_end - start_time) / 1000));

        /**
         * Only done if the user requires it. Else, just print the top of the list. This
         * is because bubble sort is stable, meaning the sort will honor the previous
         * sort order, allowing for sort chaining
         */
        if (sortDistance) {
            performSortUsingRatingAndDistance(top_rated);

            double sort_2_end = System.currentTimeMillis();
            System.out.println(
                    String.format("Total Time take to sort distance = %.10fs", (sort_2_end - sort_1_end) / 1000));

            System.out.println(String.format("Total Time for both = %.10fs", (sort_2_end - start_time) / 1000));
        } else {
            System.out.println(top_rated[top_rated.length - 1].toString());
        }

        System.out.println("====== End of Bubble Sort Test ========");
        System.out.println();
    }

    private void performSortUsingRatingAndDistance(Restaurant[] top_rated) {
        double currLat = input.getCurrLat();
        double currLong = input.getCurrLong();

        Restaurant top_and_close = b.bubbleSortDistAndGet(top_rated, currLat, currLong);
        System.out.println(top_and_close.toString());
    }
}
