package app;

import java.util.*;

import Utils.*;

public class Main {
    final static int NUMBER_OF_TESTS = 1;
    final static int[] distanceTests = { 100, 500, 1000, 1500 };

    public static void main(String[] args) {
        /**
         * This main function will be in charge of running all the tests. The function
         * first creates a fileParser to obtain all the data into a hashMap. It also
         * handles the passing of the filtered dataset based on the distance. Lastly, it
         * handles the holding of the long-lat pairs.
         * 
         * Out of the multiple tests, the first test will be a predefined
         * longitude-latitude pair which we use as a control so that the experiments
         * will return reproducable results for this data point. The next 2 point will
         * be randomised per run to produce results which simulate user input and usage
         */
        FileParser fileParser = new FileParser(NUMBER_OF_TESTS, distanceTests);
        Map<String, Results> resultsMap = new HashMap<>();

        fileParser.retrieveData();

        List<AbstractTest> testList = new ArrayList<>();
        testList.add(new JavaBenchmarkTest(fileParser));
        // testList.add(new BucketSortTest(fileParser));
        // testList.add(new HeapSortTest(fileParser));
        // testList.add(new MergeSortTest(fileParser));
        // testList.add(new QuickSortTest(fileParser));

        for (AbstractTest test : testList) {
            Results r = runDistanceTest(fileParser, test);
            resultsMap.put(test.getType(), r);
        }

        System.out.println(resultsMap.get("Benchmark").verifyResults());

    }

    /**
     * This internal function will run all the tests with a user predefined number
     * of times.
     * 
     * First perform a test to only sort by distance, then traverse to find the one
     * with highest rating. Next, prform a test to sort by rating, then distance
     * within those with the highest rating Compare the results with the benchmark
     * tests ran previously.
     * 
     * @param fileParser
     * @param test
     */
    private static Results runDistanceTest(FileParser fileParser, AbstractTest test) {

        for (int i = 0; i < NUMBER_OF_TESTS; i++) {
            for (int j = 0; j < distanceTests.length; j++) {
                fileParser.setCurrTestNum(i);
                fileParser.setRound(j);

                test.runTests();
            }
        }

        return test.getResults();
    }

}