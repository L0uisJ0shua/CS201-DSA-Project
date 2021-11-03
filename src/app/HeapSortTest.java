package app;

import Utils.*;
import algo.HeapSort;

public class HeapSortTest extends AbstractTest {
    private HeapSort h;

    public HeapSortTest(FileParser parser) {
        super(parser, "HeapSort");
        h = new HeapSort();
    }

    @Override
    public void runTests() {
        double timeTaken1 = performDistanceSortThenRating();
        double timeTaken2 = performRatingSortThenDistance();

        if (parser.getFilteredRestaurants().length == 0) {
            saveTestResultsDistance(0);
            saveTestResultsRating(0);
            return;
        }

        saveTestResultsDistance(timeTaken1);
        saveTestResultsRating(timeTaken2);
    }

    private double performDistanceSortThenRating() {
        double currLat = parser.getCurrLat();
        double currLong = parser.getCurrLong();
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

    private double performRatingSortThenDistance() {
        double currLat = parser.getCurrLat();
        double currLong = parser.getCurrLong();
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
