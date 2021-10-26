package algo;

import java.util.*;
import java.util.Collections;

import app.Restaurant;

public class BucketSort {

    public Restaurant[] bucketSortStars(Map<String, Restaurant> allRestaurants) {
        int n = allRestaurants.size();
        
        if (n <= 0)
            return null;

        Restaurant[] array = allRestaurants.values().toArray(new Restaurant[allRestaurants.values().size()]);

        // 1) Create n empty buckets
        @SuppressWarnings("unchecked")
        Vector<Restaurant>[] buckets = new Vector[6];

        for (int i = 0; i < 6; i++) {
            buckets[i] = new Vector<Restaurant>();
        }

        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            float rating = array[i].getStars();
            buckets[(int) rating].add(array[i]);
        }

        // 3) Sort individual buckets
        for (int i = 0; i < 6; i++) {
            sortHelper(buckets[i]);
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                array[index++] = buckets[i].get(j);
            }
        }

        float top_rating = array[array.length-1].getStars();
        int counter = array.length-1;
        while (array[counter].getStars() == top_rating) {
            counter--;
        }

        return Arrays.copyOfRange(array, counter, array.length-1);
    }

    public Restaurant bubbleSortDistAndGet(Restaurant[] array, double origin_lat, double origin_long) {
        int n = array.length;

        if (n <= 0)
            return null;

        // 1) Create n empty buckets
        @SuppressWarnings("unchecked")
        Vector<Restaurant>[] buckets = new Vector[10000];

        for (int i = 0; i < 10000; i++) {
            buckets[i] = new Vector<Restaurant>();
        }

        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            double dist = array[i].calculateDistanceFrom(origin_lat, origin_long);
            buckets[(int) dist].add(array[i]);
        }

        // 3) Sort individual buckets
        for (int i = 0; i < 10000; i++) {
            sortHelper(buckets[i]);
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                array[index++] = buckets[i].get(j);
            }
        }

        return array[0];
        
    }

    // Insertion Sort
    private void sortHelper(Vector<Restaurant> vec) {
        for (int i = 1; i < vec.size(); i++) {
                int j = i - 1;
                Restaurant key = vec.get(i);
                while (j >= 0 && !vec.get(j).compareRating(key)) {
                    Restaurant temp = vec.remove(j+1);
                    vec.add(j, temp);
                    j--;
                }
        }
    }
}
