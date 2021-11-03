package algo;

import java.util.*;
import app.*;

public class HeapSort {

  /**
   * Sort by distance only. A separate function will extract the first highest
   * rated restaurant.
   * 
   * @param S
   * @param origin_lat
   * @param origin_long
   * @return
   */
  public Restaurant[] pqSortDistance(Restaurant[] S, double origin_lat, double origin_long) {
    int n = S.length;

    PriorityQueue<Map.Entry<Double, Restaurant>> queue = new PriorityQueue<>((a, b) -> (int) (a.getKey() - b.getKey()));

    for (Restaurant restaurant : S) {
      Double dist = restaurant.calculateDistanceFrom(origin_lat, origin_long);
      queue.add(new AbstractMap.SimpleEntry<Double, Restaurant>(dist, restaurant));
    }

    Restaurant[] sortedRestaurant = new Restaurant[n];
    for (int j = 0; j < n; j++) {
      sortedRestaurant[j] = queue.remove().getValue();
    }

    return sortedRestaurant;
  }

  /**
   * Sort by rating, then distance using the method above.
   * 
   * @param S
   * @param origin_lat
   * @param origin_long
   * @return
   */
  public Restaurant[] pqSortRatingThenDistance(Restaurant[] S, double origin_lat, double origin_long) {
    int n = S.length;

    PriorityQueue<Map.Entry<Float, Restaurant>> queue = new PriorityQueue<>((a, b) -> b.getKey().compareTo(a.getKey()));

    for (Restaurant restaurant : S) {
      queue.add(new AbstractMap.SimpleEntry<Float, Restaurant>(restaurant.getStars(), restaurant));
    }

    Restaurant[] sortedRestaurant = new Restaurant[n];

    // make sure the resulting are all highest rated restaurants
    float highestRating = queue.peek().getKey();
    for (int j = 0; j < n; j++) {
      if (queue.peek().getKey() != highestRating) {
        break;
      }
      sortedRestaurant[j] = queue.remove().getValue();
    }

    return pqSortDistance(sortedRestaurant, origin_lat, origin_long);
  }
}
