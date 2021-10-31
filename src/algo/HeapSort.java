package algo;

import datastruct.priorityQueue.*;

public class HeapSort {
    public static <E> void pqSort(PositionalList<E> S, PriorityQueue<E,?> P) {
        int n = S.size();
        for (int j=0; j < n; j++) {
          E element = S.remove(S.first());
          P.insert(element, null);             // element is key; null value
        }
        for (int j=0; j < n; j++) {
          E element = P.removeMin().getKey();
          S.addLast(element);                  // the smallest key in P is next placed in S
        }
}
