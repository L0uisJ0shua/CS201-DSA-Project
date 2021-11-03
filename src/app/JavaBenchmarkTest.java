package app;

import Utils.*;
import java.util.*;

public class JavaBenchmarkTest extends AbstractTest {

    public JavaBenchmarkTest(FileParser parser) {
        super(parser, "Benchmark");
    }

    @Override
    public void runTests() {
        runBenchmark();
    }

    /**
     * A test to run the benchmark with Java's own implementations
     * 
     * @param testNum
     */
    public void runBenchmark() {
        double startTime = System.currentTimeMillis();
        int currTest = parser.getCurrTestNum();
        int currRound = parser.getCurrRound();

        javaTreeSortDistance();

        double endTime = System.currentTimeMillis();

        results.setResultsDistanceThenRating(endTime - startTime, currTest, currRound);
    }

    /**
     * Function to insert into a tree and perform inorder traversal to obtain the
     * results in an array
     * 
     * @return
     */
    private void javaTreeSortDistance() {
        double currLat = parser.getCurrLat();
        double currLong = parser.getCurrLong();
        Restaurant[] filteredRestaurants = parser.getFilteredRestaurants();

        Map<Double, Restaurant> sortedMap = new TreeMap<>();

        for (Restaurant restaurant : filteredRestaurants) {
            double dist = restaurant.calculateDistanceFrom(currLat, currLong);
            sortedMap.put(dist, restaurant);
        }

        Restaurant[] sortedByDistance = sortedMap.values().toArray(new Restaurant[0]);

        // Now, use timSort
        javaArrSort(sortedByDistance);
        results.addBestRestaurant(sortedByDistance[0]);
    }

    /**
     * Sort by rating based on reviews alone
     * 
     * @return
     */
    private void javaArrSort(Restaurant[] arrForSorting) {
        Arrays.sort(arrForSorting);
    }
}
