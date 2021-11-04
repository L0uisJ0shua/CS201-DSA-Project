package app;

import algo.MergeSort;
import Utils.*;

public class MergeSortTest extends AbstractTest {
    private MergeSort m;

    public MergeSortTest(FileParser parser) {
        super(parser, "MergeSort");
        m = new MergeSort();
    }

    @Override
    protected double performDistanceSortThenRating(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();
        int n = filteredRestaurants.length;

        double start_time = System.currentTimeMillis();

        m.mergeSortByDistance(filteredRestaurants, 0, n - 1, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        top_rated = filteredRestaurants;
        if (filteredRestaurants.length == 0) {
            return sort_1_end - start_time;
        }

        double sort_2_end = searchNearestAndBest();
        return sort_2_end - start_time;
    }

    @Override
    protected double performRatingSortThenDistance(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();
        int n = filteredRestaurants.length;

        double start_time = System.currentTimeMillis();

        m.mergeSortByRating(filteredRestaurants, 0, n - 1, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (filteredRestaurants.length == 0) {
            return sort_1_end - start_time;
        }
        double sort_2_end = System.currentTimeMillis();

        results.addBestRestaurant(filteredRestaurants[0]);
        return sort_2_end - start_time;
    }

}
