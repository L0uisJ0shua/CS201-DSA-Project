package app;

import Utils.*;
import algo.QuickSort;

public class QuickSortTest extends AbstractTest {

    public QuickSortTest(FileParser parser) {
        super(parser, "QuickSort");
    }

    @Override
    protected double performDistanceSortThenRating(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();
        int n = filteredRestaurants.length;

        double start_time = System.currentTimeMillis();

        QuickSort.quickSortByDistance(filteredRestaurants, 0, n - 1, currLat, currLong);

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

        QuickSort.quickSortByRating(filteredRestaurants, 0, n - 1, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (filteredRestaurants.length == 0) {
            return sort_1_end - start_time;
        }
        double sort_2_end = System.currentTimeMillis();

        results.addBestRestaurant(filteredRestaurants[0]);
        return sort_2_end - start_time;
    }

}
