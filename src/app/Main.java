package app;

import java.util.*;

import Utils.*;

public class Main {
    public static void main(String[] args) {

        // start here
        Console console = new Console();
        console.getUserData();

        Map<String, Restaurant> allRestaurants = new HashMap<>();

        FileParser fileParser = new FileParser(allRestaurants);
        fileParser.retrieveData(true, console);

        BubbleSortTest test = new BubbleSortTest(fileParser, console);
        test.performSortUsingRating(false);

    }

}