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
    public void runTests() {

        double currLat = parser.getCurrLat();
        double currLong = parser.getCurrLong();

        double sortTime1 = performDistanceSortThenRating(currLat, currLong);
        double sortTime2 = performRatingSortThenDistance(currLat, currLong);

        if (parser.getFilteredRestaurants().length == 0) {
            saveTestResultsDistance(0);
            saveTestResultsRating(0);
            return;
        }

        saveTestResultsDistance(sortTime1);
        saveTestResultsRating(sortTime2);
    }

    public double performDistanceSortThenRating(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();
        int n = filteredRestaurants.length;

        double start_time = System.currentTimeMillis();

        m.mergeSortByDistance(filteredRestaurants, 0, n - 1, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (filteredRestaurants.length == 0) {
            results.addBestRestaurant(null);
            return sort_1_end - start_time;
        }

        top_rated = filteredRestaurants;
        double sort_2_end = searchNearestAndBest();
        return sort_2_end - start_time;
    }

    private double performRatingSortThenDistance(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();
        int n = filteredRestaurants.length;

        double start_time = System.currentTimeMillis();

        m.mergeSortByRating(filteredRestaurants, 0, n - 1, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (filteredRestaurants.length == 0) {
            results.addBestRestaurant(null);
            return sort_1_end - start_time;
        }
        double sort_2_end = System.currentTimeMillis();

        results.addBestRestaurant(filteredRestaurants[0]);
        return sort_2_end - start_time;
    }

}
