package Utils;

import java.util.*;
import app.*;

public class Results {
    private double[][] testResultsRatingThenDistance;
    private double[][] testResultsDistanceThenRating;
    private List<Restaurant> bestRestaurantsList = new ArrayList<>();

    // Every result class will store a 2D array of results. The outer array is the
    // test number and the inner array is the result corrosponding to that
    // particular distance (round)
    public Results(final int numTests, final int testRounds) {
        testResultsRatingThenDistance = new double[numTests][testRounds];
        testResultsDistanceThenRating = new double[numTests][testRounds];
    }

    public double[][] getTestResultsRatingThenDistance() {
        return testResultsRatingThenDistance;
    }

    public double[][] getTestResultsDistanceThenRating() {
        return testResultsDistanceThenRating;
    }

    public void setResultsRatingThenDistance(double time, int currTestNum, int currRoundNum) {
        testResultsRatingThenDistance[currTestNum][currRoundNum] = time;
    }

    public void setResultsDistanceThenRating(double time, int currTestNum, int currRoundNum) {
        testResultsDistanceThenRating[currTestNum][currRoundNum] = time;
    }

    /**
     * Special method to benchmark against Java's own implementation. Java's result
     * will only be stored in distanceThenRating
     * 
     * @param result
     * @return
     */
    public double[] benchmark(Results result, int testNum, int roundNum) {
        double benchDistanceFirst = testResultsDistanceThenRating[testNum][roundNum]
                - result.getBenchmarkExactTime(testNum, roundNum);
        double benchRatingFirst = testResultsRatingThenDistance[testNum][roundNum]
                - result.getBenchmarkExactTime(testNum, roundNum);

        return new double[] { benchDistanceFirst, benchRatingFirst };
    }

    public double[][] getBenchmarkArr() {
        return testResultsDistanceThenRating;
    }

    public double getBenchmarkExactTime(int testNum, int roundNum) {
        return testResultsDistanceThenRating[testNum][roundNum];
    }

    public void addBestRestaurant(Restaurant r) {
        bestRestaurantsList.add(r);
    }

    public String getBestRestaurants() {
        return bestRestaurantsList.toString();
    }

    /**
     * A simple function to make sure all the sorts returned the same result
     * regardless of the order of sort or type of sort or datatype
     * 
     * @return
     */
    public boolean verifyResults() {
        if (bestRestaurantsList.size() == 0) {
            return true;
        }

        try {
            String initial = bestRestaurantsList.get(0).getName();
            for (Restaurant r : bestRestaurantsList) {
                if (!r.getName().equals(initial)) {
                    return false;
                }
            }
        } catch (Exception e) {
            // Most likely NPE
            System.out.println(e);
            return false;
        }

        return true;
    }

}
