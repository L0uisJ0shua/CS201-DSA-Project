package algo;

import java.util.*;
import app.Restaurant;

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
  public Restaurant[] pqSortDistance(Restaurant[] restaurants, double origin_lat, double origin_long) {
    int n = restaurants.length;

    PriorityQueue<Map.Entry<Double, Restaurant>> queue = new PriorityQueue<>(
        (a, b) -> a.getKey().compareTo(b.getKey()));

    for (Restaurant restaurant : restaurants) {
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
   * @param restaurants
   * @param origin_lat
   * @param origin_long
   * @return
   */
  public Restaurant[] pqSortRatingThenDistance(Restaurant[] restaurants, double origin_lat, double origin_long) {
    int n = restaurants.length;

    if (n == 0) {
      return new Restaurant[0];
    }

    PriorityQueue<Map.Entry<Float, Restaurant>> queue = new PriorityQueue<>((a, b) -> b.getKey().compareTo(a.getKey()));

    for (Restaurant restaurant : restaurants) {
      queue.add(new AbstractMap.SimpleEntry<Float, Restaurant>(restaurant.getStars(), restaurant));
    }

    List<Restaurant> sortedRestaurant = new ArrayList<>();

    // make sure the resulting are all highest rated restaurants
    float highestRating = queue.peek().getKey();
    for (int j = 0; j < n; j++) {
      if (queue.peek().getKey() < highestRating) {
        break;
      }
      sortedRestaurant.add(queue.remove().getValue());
    }

    Restaurant[] bestRated = sortedRestaurant.toArray(new Restaurant[0]);
    return pqSortDistance(bestRated, origin_lat, origin_long);
  }
}
