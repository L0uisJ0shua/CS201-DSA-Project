package app;

import java.util.*;

import Utils.*;

public class Main {
    final static int NUMBER_OF_TESTS = 1;
    final static int[] distanceTests = { 500, 1000, 1500 };

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
        testList.add(new HeapSortTest(fileParser));
        testList.add(new MergeSortTest(fileParser));
        testList.add(new QuickSortTest(fileParser));
        testList.add(new BucketSortTest(fileParser));

        for (int i = 0; i < NUMBER_OF_TESTS; i++) {

            fileParser.setCurrTestNum(i);
            System.out.printf("Commencing Test #%d ... %n%n", i + 1);

            for (AbstractTest test : testList) {
                Results r = runDistanceTest(fileParser, test, i);
                resultsMap.put(test.getType(), r);
            }

            System.out.printf("Benchmark consistent: %s%n", resultsMap.get("Benchmark").verifyResults());
            System.out.printf("Heap Sort consistent: %s%n", resultsMap.get("HeapSort").verifyResults());
            System.out.printf("Merge Sort consistent: %s%n", resultsMap.get("MergeSort").verifyResults());
            System.out.printf("Quick Sort consistent: %s%n", resultsMap.get("QuickSort").verifyResults());
            System.out.printf("Bucket Sort consistent: %s%n", resultsMap.get("BucketSort").verifyResults());

            String bestRestaurant = resultsMap.get("Benchmark").getBestRestaurant();

            boolean allAgree = true;
            for (Results res : resultsMap.values()) {
                System.out.println(res.getBestRestaurant());
                if (!res.getBestRestaurant().equals(bestRestaurant)) {
                    allAgree = false;
                    // break;
                }
            }
            if (!allAgree) {
                System.out.println("Not the same!");
            } else {
                System.out.println("Every sort agrees that this is the best and closest: " + bestRestaurant);
            }

            // Clean up the list for next test
            for (Results r : resultsMap.values()) {
                r.clearBestList();
            }

            System.out.printf("End of Test #%d %n%n", i + 1);
        }

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
     * @param i
     */
    private static Results runDistanceTest(FileParser fileParser, AbstractTest test, int i) {
        System.out.printf("Commencing %s Sort Tests...", test.getType());

        for (int j = 0; j < distanceTests.length; j++) {
            fileParser.setRound(j);

            test.runTests();
        }

        System.out.printf("End of %s Sort Test%n", test.getType());

        return test.getResults();
    }

}