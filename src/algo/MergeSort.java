package algo;

import java.util.*;
import app.Restaurant;

public class MergeSort {

    public void mergeArrayByDistance(Restaurant[] restaurants, int start, int mid, int end, double currLat,
            double currLong) {
        // 1) Create a temp array to store the sorted result
        Restaurant[] mergedResult = new Restaurant[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;

        // Compare the distance of the restaurants from the origin location
        // Append the restaurant that has the smaller distance to the mergedResult list
        while (i <= mid && j <= end) {
            if (restaurants[i].calculateDistanceFrom(currLat, currLong) <= restaurants[j].calculateDistanceFrom(currLat,
                    currLong)) {
                mergedResult[k] = restaurants[i];
                k++;
                i++;

            } else {
                mergedResult[k] = restaurants[j];
                k++;
                j++;
            }
        }

        // Add the remaining elements in the array to the result
        while (i <= mid) {
            mergedResult[k] = restaurants[i];
            k++;
            i++;
        }

        // Add the remaining elements in the array to the result
        while (j <= end) {
            mergedResult[k] = restaurants[j];
            k++;
            j++;
        }

        // Overwrite the original array with the sorted array
        for (i = start; i <= end; i++) {
            restaurants[i] = mergedResult[i - start];
        }
    }

    public void mergeArrayByRating(Restaurant[] restaurants, int start, int mid, int end, double currLat,
            double currLong) {
        // 1) Create a temp array to store the sorted result
        Restaurant[] mergedResult = new Restaurant[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;

        // Compare the distance of the restaurants from the origin location
        // Append the restaurant that has the smaller distance to the mergedResult list
        while (i <= mid && j <= end) {
            if (restaurants[i].getStars() == restaurants[j].getStars()) {
                if (restaurants[i].calculateDistanceFrom(currLat, currLong) <= restaurants[j]
                        .calculateDistanceFrom(currLat, currLong)) {
                    mergedResult[k] = restaurants[i];
                    k++;
                    i++;

                } else {
                    mergedResult[k] = restaurants[j];
                    k++;
                    j++;
                }

            } else if (restaurants[i].getStars() < restaurants[j].getStars()) {
                mergedResult[k] = restaurants[j];
                k++;
                j++;

            } else {
                mergedResult[k] = restaurants[i];
                k++;
                i++;
            }
        }

        // Add the remaining elements in the array to the result
        while (i <= mid) {
            mergedResult[k] = restaurants[i];
            k++;
            i++;
        }

        // Add the remaining elements in the array to the result
        while (j <= end) {
            mergedResult[k] = restaurants[j];
            k++;
            j++;
        }

        // Overwrite the original array with the sorted array
        for (i = start; i <= end; i++) {
            restaurants[i] = mergedResult[i - start];
        }
    }

    public void mergeSortByDistance(Restaurant[] restaurant, int leftIdx, int rightIdx, double currLat,
            double currLong) {
        if (leftIdx < rightIdx) {
            int middleIdx = (leftIdx + rightIdx) / 2;

            // Sort elements of the first half of the array according to their distance
            mergeSortByDistance(restaurant, leftIdx, middleIdx, currLat, currLong);
            // Sort elements of the second half of the array
            mergeSortByDistance(restaurant, middleIdx + 1, rightIdx, currLat, currLong);

            mergeArrayByDistance(restaurant, leftIdx, middleIdx, rightIdx, currLat, currLong);
        }
    }

    public void mergeSortByRating(Restaurant[] restaurant, int leftIdx, int rightIdx, double currLat, double currLong) {
        if (leftIdx < rightIdx) {
            int middleIdx = (leftIdx + rightIdx) / 2;

            // Sort elements of the first half of the array according to their distance
            mergeSortByRating(restaurant, leftIdx, middleIdx, currLat, currLong);
            // Sort elements of the second half of the array
            mergeSortByRating(restaurant, middleIdx + 1, rightIdx, currLat, currLong);

            mergeArrayByRating(restaurant, leftIdx, middleIdx, rightIdx, currLat, currLong);
        }
    }

}
