package algo;

import java.util.*;
import app.*;

public class HeapSort {
  public Restaurant[] pqSortDistance(Map<String, Restaurant> S, double origin_lat, double origin_long) {
    int n = S.size();

    PriorityQueue<Map.Entry<Double, Restaurant>> queue = new PriorityQueue<>((a, b) -> (int) (a.getKey() - b.getKey()));

    for (Restaurant restaurant : S.values()) {
      Double dist = restaurant.calculateDistanceFrom(origin_lat, origin_long);
      queue.add(new AbstractMap.SimpleEntry<Double, Restaurant>(dist, restaurant));
    }

    Restaurant[] sortedRestaurant = new Restaurant[n];
    for (int j = 0; j < n; j++) {
      sortedRestaurant[j] = queue.remove().getValue();
    }

    return sortedRestaurant;
  }
}
