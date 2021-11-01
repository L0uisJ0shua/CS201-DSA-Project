package app;

import java.util.*;

import Utils.*;

public class Main {
    public static void main(String[] args) {

        Map<String, Restaurant> allRestaurants = new HashMap<>();
        FileParser fileParser = new FileParser(allRestaurants);
        AbstractTest test = new BubbleSortTest(fileParser);

        // start by running a test with a predefined location
        boolean isRandomised = false;
        System.out.println("Running initial test");
        runDistanceTest(isRandomised, fileParser, test);

        // Run 3 more randomised tests with randomised location
        isRandomised = true;
        for (int i = 0; i < 3; i++) {
            System.out.println("Running randomised tests");
            runDistanceTest(isRandomised, fileParser, test);
        }

    }

    private static void runDistanceTest(boolean randomise, FileParser fileParser, AbstractTest test) {
        if (randomise) {
            fileParser.randomise();
        }

        int[] distanceTests = { 10, 100, 500, 1000, 1500 };
        for (int i : distanceTests) {
            fileParser.retrieveData(true, i);

            // First perform a test to only sort review. Then sort review then distance
            test.runTests();
        }
    }

}