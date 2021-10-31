package Utils;

import java.util.*;

public class Console {
    public Console() {

    }

    public Map<String, Double> getUserData() {
        Scanner sc = new Scanner(System.in);
        Map<String, Double> results = new HashMap<>();

        System.out.print("Acceptable distance(Km) --->  ");
        double acceptableRange = sc.nextDouble();
        sc.nextLine();
        results.put("distance", acceptableRange);

        // Hard code recommended latitude and longitude here
        System.out.print("We recommend this: 39.778259,-105.417931. Use this? [Y/n] ");
        String res = sc.nextLine();

        double currLat = 39.778259;
        double currLong = -105.417931;
        if (!res.toLowerCase().equals("y") && !res.equals("")) {
            System.out.print("Your current location latitude ---> ");
            currLat = sc.nextDouble();

            System.out.print("Your current location longtitude ---> ");
            currLong = sc.nextDouble();
            sc.nextLine();
        }

        results.put("lat", currLat);
        results.put("long", currLong);

        return results;
    }

}
