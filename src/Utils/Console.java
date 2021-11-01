package Utils;

import java.util.*;

public class Console {
    private double acceptableRange = 5000;
    private double currLat = 39.778259;
    private double currLong = -105.417931;

    public void getUserData() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Acceptable distance(Km) ---> ");
        acceptableRange = sc.nextDouble();
        sc.nextLine();

        // Hard code recommended latitude and longitude here
        System.out.print("We recommend this: 39.778259,-105.417931. Use this? [Y/n] ");
        String res = sc.nextLine();

        // If the user does not wish to use the defaults:
        if (!res.toLowerCase().equals("y") && !res.equals("")) {
            System.out.print("Your current location latitude ---> ");
            currLat = sc.nextDouble();

            System.out.print("Your current location longtitude ---> ");
            currLong = sc.nextDouble();
            sc.nextLine();
        }
    }

    public void randomise() {

    }

    public double getAcceptableRange() {
        return acceptableRange;
    }

    public void setAcceptableRange(double acceptableRange) {
        this.acceptableRange = acceptableRange;
    }

    public double getCurrLat() {
        return currLat;
    }

    public void setCurrLat(double currLat) {
        this.currLat = currLat;
    }

    public double getCurrLong() {
        return currLong;
    }

    public void setCurrLong(double currLong) {
        this.currLong = currLong;
    }

    @Override
    public String toString() {
        return "Current Detaills: [acceptableRange=" + acceptableRange + ", currLat=" + currLat + ", currLong="
                + currLong + "]";
    }

}
