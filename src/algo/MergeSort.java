package algo;

import java.util.*;
import app.Restaurant;

public class MergeSort {
    
    public void mergeArray(Restaurant[] restaurants, int start, int mid, int end, double currLat, double currLong){
        // 1) Create a temp array to store the sorted result
        Restaurant[] mergedResult = new Restaurant[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;

        // Compare the distance of the restaurants from the origin location
        // Append the restaurant that has the smaller distance to the mergedResult list
        while (i <= mid && j <= end) {
            if(restaurants[i].calculateDistanceFrom(currLat, currLong) <= restaurants[j].calculateDistanceFrom(currLat, currLong)){
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
        while (i <= mid){
            mergedResult[k] = restaurants[i];
            k++;
            i++;
        }

        // Add the remaining elements in the array to the result
        while (j <= end){
            mergedResult[k] = restaurants[j];
            k++;
            j++;
        }

        // Overwrite the original array with the sorted array
        for (i = start; i <= end; i++){
            restaurants[i] = mergedResult[i - start];
        }
    }

    public void mergeSort(Restaurant[] restaurant, int leftIdx, int rightIdx, double currLat, double currLong){
        if (leftIdx < rightIdx){
            int middleIdx = (leftIdx + rightIdx) / 2;
            
            // Sort elements of the first half of the array according to their distance 
            mergeSort(restaurant, leftIdx, middleIdx, currLat, currLong);
            // Sort elements of the second half of the array
            mergeSort(restaurant, middleIdx + 1, rightIdx, currLat, currLong);

            mergeArray(restaurant, leftIdx, middleIdx, rightIdx, currLat, currLong);
        }
    }
}
