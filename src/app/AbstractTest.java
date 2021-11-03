package app;

import Utils.*;

public abstract class AbstractTest {
    protected Restaurant[] top_rated;
    protected FileParser parser;
    protected Results results;
    protected String type;

    public AbstractTest(FileParser parser, String type) {
        this.parser = parser;
        this.type = type;
        results = new Results(parser.getTestCount(), parser.getAcceptableRanges().length);
    }

    public abstract void runTests();

    /**
     * Just go through top down and look for the first 5 star one We can only do
     * this because heap sort is not stables
     * 
     * @param top_rated
     * @return
     */
    protected double searchNearestAndBest() {
        float highestRating = 0;
        for (Restaurant r : top_rated) {
            if (r.getStars() > highestRating) {
                highestRating = r.getStars();

                // short circuit if highest is 5.0
                if (highestRating == 5) {
                    break;
                }
            }
        }

        // print the first restaurant at the nearest and highest rating
        for (Restaurant r : top_rated) {
            if (r.getStars() == highestRating) {
                results.addBestRestaurant(r);
                // System.out.println(r.calculateDistanceFrom(parser.getCurrLat(),
                // parser.getCurrLong()));
                // System.out.println(r);
                break;
            }
        }
        return System.currentTimeMillis();
    }

    public Results getResults() {
        return results;
    }

    public String getType() {
        return type;
    }

    protected void saveTestResultsDistance(double time) {
        int currTest = parser.getCurrTestNum();
        int currRound = parser.getCurrRound();
        results.setResultsDistanceThenRating(time, currTest, currRound);
    }

    protected void saveTestResultsRating(double time) {
        int currTest = parser.getCurrTestNum();
        int currRound = parser.getCurrRound();
        results.setResultsRatingThenDistance(time, currTest, currRound);
    }

    /**
     * Internal function to print out the fancy stuff by comparing the sort time and
     * methods with Java own implementation
     */
    // protected void benchmarkWithJava() {
    // double[] comparedResults = results.benchmark(result, testNum, roundNum);

    // System.out.println("================Benchmark with Java=============");
    // System.out.println(String.format("Distance sort -> Rating sort takes %.10f
    // more seconds",
    // (timeTaken1 - benchmarkTime) / 1000));
    // System.out.println("================================================");
    // System.out.println();

    // System.out.println(String.format("Rating sort -> Distance sort takes %.10f
    // more seconds",
    // (timeTaken2 - benchmarkTime) / 1000));
    // System.out.println("==============End OF Benchmark=====================");
    // System.out.println();
    // }
}
