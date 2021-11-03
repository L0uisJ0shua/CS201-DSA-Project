package app;

import Utils.*;
import algo.HeapSort;

public class HeapSortTest extends AbstractTest {
    private HeapSort h;

    public HeapSortTest(FileParser parser) {
        super(parser, "HeapSort");
        h = new HeapSort();
    }

    protected double performDistanceSortThenRating(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        top_rated = h.pqSortDistance(filteredRestaurants, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (top_rated.length == 0) {
            results.addBestRestaurant(null);
            return sort_1_end - start_time;
        }

        double sort_2_end = searchNearestAndBest();
        return sort_2_end - start_time;
    }

    @Override
    protected double performRatingSortThenDistance(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        Restaurant[] sortedArr = h.pqSortRatingThenDistance(filteredRestaurants, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (sortedArr == null || sortedArr.length == 0) {
            results.addBestRestaurant(null);
        }

        results.addBestRestaurant(sortedArr[0]);
        return sort_1_end - start_time;
    }

}
