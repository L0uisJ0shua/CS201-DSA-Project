package app;

import Utils.FileParser;
import algo.BucketSort;

public class BucketSortTest extends AbstractTest {
    private BucketSort b;

    public BucketSortTest(FileParser parser) {
        super(parser, "BucketSort");
        b = new BucketSort();
    }

    @Override
    protected double performDistanceSortThenRating(double currLat, double currLong) {
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();

        double start_time = System.currentTimeMillis();

        top_rated = b.bucketSortDistAndGet(filteredRestaurants, parser.getCurrLat(), parser.getCurrLong());

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

        Restaurant[] top_rated = b.bucketSortStars(filteredRestaurants);
        Restaurant[] top_and_close = b.bucketSortDistAndGet(top_rated, currLat, currLong);

        double sort_1_end = System.currentTimeMillis();

        if (top_and_close == null || top_and_close.length == 0) {
            results.addBestRestaurant(null);
        }

        results.addBestRestaurant(top_and_close[0]);
        return sort_1_end - start_time;

    }

}
