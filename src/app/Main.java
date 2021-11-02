package app;

import java.util.*;

import Utils.*;

public class Main {
    public static void main(String[] args) {

        Map<String, Restaurant> allRestaurants = new HashMap<>();
        FileParser fileParser = new FileParser(allRestaurants);

        // start by running a test with a predefined location
        AbstractTest test = new BucketSortTest(fileParser);
        System.out.println("Running initial test");
        runDistanceTest(-1, fileParser, test);

        // Run 2 more randomised tests with randomised location
        for (int i = 1; i < 0; i++) {
            System.out.println("Running randomised tests");
            runDistanceTest(i, fileParser, test);
        }

        test = new HeapSortTest(fileParser);
        runDistanceTest(-1, fileParser, test);

        for (int i = 1; i < 0; i++) {
            System.out.println();
            System.out.println("Running randomised tests");
            runDistanceTest(i, fileParser, test);
        }

        test = new MergeSortTest(fileParser);
        runDistanceTest(-1, fileParser, test);

        for (int i = 1; i < 0; i++) {
            System.out.println();
            System.out.println("Running randomised tests");
            runDistanceTest(i, fileParser, test);
        }

        test = new QuickSortTest(fileParser);
        runDistanceTest(-1, fileParser, test);

        for (int i = 1; i < 0; i++) {
            System.out.println();
            System.out.println("Running randomised tests");
            runDistanceTest(i, fileParser, test);
        }

    }

    private static void runDistanceTest(int randomise, FileParser fileParser, AbstractTest test) {
        if (randomise >= 0) {
            fileParser.randomise(randomise);
        } else {
            fileParser.resetValues();
        }

        int[] distanceTests = { 100, 500, 1000, 1500 };
        for (int i : distanceTests) {
            fileParser.retrieveData(true, i);

            // First perform a test to only sort review. Then sort review then distance
            test.runTests();
        }
    }

}