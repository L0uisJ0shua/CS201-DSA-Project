package algo;

import app.Restaurant;

public class QuickSort {
    // A utility function to swap two elements
    static void swap(Restaurant[] arr, int i, int j)
    {
        Restaurant temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /* This function takes last element as pivot, places
    the pivot element at its correct position in sorted
    array, and places all smaller (smaller than pivot)
    to left of pivot and all greater elements to right
    of pivot */
    public static int partitionByRating(Restaurant[] arr, int low, int high, double currLat, double currLong)
    {
        
        // pivot
        Restaurant pivot = arr[high]; 
        
        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1); 
    
        for(int j = low; j <= high - 1; j++)
        {
            
            // If current element is smaller 
            // than the pivot
            if (arr[j].getStars() > pivot.getStars() || (arr[j].getStars() == pivot.getStars() && arr[j].calculateDistanceFrom(currLat, currLong) < pivot.calculateDistanceFrom(currLat, currLong))) 
            {
                
                // Increment index of 
                // smaller element
                i++; 
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    public static int partitionByDistance(Restaurant[] arr, int low, int high, double currLat, double currLong)
    {
        
        // pivot
        Restaurant pivot = arr[high]; 
        
        // Index of smaller element and
        // indicates the right position
        // of pivot found so far
        int i = (low - 1); 
    
        for(int j = low; j <= high - 1; j++)
        {
            
            // If current element is smaller 
            // than the pivot
            if (arr[j].calculateDistanceFrom(currLat, currLong) < pivot.calculateDistanceFrom(currLat, currLong))
            {
                
                // Increment index of 
                // smaller element
                i++; 
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    /* The main function that implements QuickSort
            arr[] --> Array to be sorted,
            low --> Starting index,
            high --> Ending index
    */
    public static void quickSortByRating(Restaurant[] arr, int low, int high, double currLat, double currLong)
    {
        if (low < high) 
        {
        
            // pi is partitioning index, arr[p]
            // is now at right place 
            int pi = partitionByRating(arr, low, high, currLat, currLong);

            // Separately sort elements before
            // partition and after partition
            quickSortByRating(arr, low, pi - 1, currLat, currLong);
            quickSortByRating(arr, pi + 1, high, currLat, currLong);
        }
    }

    public static void quickSortByDistance(Restaurant[] arr, int low, int high, double currLat, double currLong)
    {
        if (low < high) 
        {
        
            // pi is partitioning index, arr[p]
            // is now at right place 
            int pi = partitionByDistance(arr, low, high, currLat, currLong);

            // Separately sort elements before
            // partition and after partition
            quickSortByDistance(arr, low, pi - 1, currLat, currLong);
            quickSortByDistance(arr, pi + 1, high, currLat, currLong);
        }
    }
}
