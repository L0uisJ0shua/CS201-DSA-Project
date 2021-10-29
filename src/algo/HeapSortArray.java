package algo;

import datastruct.DefaultComparator;
import datastruct.priorityQueue.*;
import java.util.Comparator;

/**
 * Instead of using the default HeapSort algo and data structs, this explores
 * the use of heap sort using an array of the nodes instead. This way, it is
 * done in place without the need to insert into a HeapPriorityQueue
 */
public class HeapSortArray<K, V> {
    private Comparator<K> comp = new DefaultComparator<>();

    private int compare(Entry<K, V> a, Entry<K, V> b) {
        return comp.compare(a.getKey(), b.getKey());
    }

    public void sort(Entry<K, V> arr[]) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root to end
            Entry<K, V> temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    void heapify(Entry<K, V> arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && compare(arr[l], arr[largest]) > 0)
            largest = l;

        // If right child is larger than largest so far
        if (r < n && compare(arr[r], arr[largest]) > 0)
            largest = r;

        // If largest is not root
        if (largest != i) {
            Entry<K, V> swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    // Driver code
    // public static void main(String args[])
    // {
    // int arr[] = { 12, 11, 13, 5, 6, 7 };
    // int n = arr.length;

    // HeapSort ob = new HeapSort();
    // ob.sort(arr);

    // System.out.println("Sorted array is");
    // printArray(arr);
    // }
}
