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
    public String[] benchmarkForDistance(Results result, int testNum) {
        return populateArr(result, testNum, testResultsDistanceThenRating);
    }

    public String[] benchmarkForRating(Results result, int testNum) {
        return populateArr(result, testNum, testResultsRatingThenDistance);
    }

    /**
     * Internal function to make the string needed for display on the console
     * 
     * @param result
     * @param testNum
     * @param testResults
     * @return
     */
    private String[] populateArr(Results result, int testNum, double[][] testResults) {
        int size = testResults[testNum].length;
        String benchmarkedArr[] = new String[size];

        for (int roundNum = 0; roundNum < size; roundNum++) {
            double benchTime = result.getBenchmarkExactTime(testNum, roundNum);
            double currTime = testResults[testNum][roundNum];
            double diff = currTime - benchTime;
            double percentage = benchTime == 0 ? 100 : currTime * 100 / benchTime;

            benchmarkedArr[roundNum] = String.format((diff > 0 ? "+" : "") + "%.5f / %.2f%%", diff / 1000, percentage);
        }

        return benchmarkedArr;
    }

    public String[] getBenchmarkArr(int currTestNum) {
        double[] currBench = testResultsDistanceThenRating[currTestNum];
        int size = currBench.length;
        String benchmarkArr[] = new String[size];

        for (int i = 0; i < size; i++) {
            benchmarkArr[i] = String.format("%.5f / 100.00%%", currBench[i] / 1000);
        }

        return benchmarkArr;
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

    public String getBestRestaurant() {
        if (bestRestaurantsList.size() == 0) {
            return null;
        }
        return bestRestaurantsList.get(0).getName();
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
        } catch (NullPointerException e) {
            // Most likely NPE
            System.out.println(e);
            return false;
        }

        return true;
    }

    public void clearBestList() {
        bestRestaurantsList.clear();
    }

}
