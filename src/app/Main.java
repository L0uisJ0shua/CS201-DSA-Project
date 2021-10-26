package app;

import java.io.BufferedReader;

// import algo.*;
// import datastruct.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.*;

public class Main {
    public static void main(String[] args) {
        // start here
        Gson gson = new Gson();
        Path path = Paths.get("./yelp_academic_dataset_business.json");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;

            while ((line = reader.readLine()) != null) {
                Restaurant restaurant = gson.fromJson(line, Restaurant.class);
                System.out.println(restaurant.toString());
                System.out.println(restaurant.getHours().get("Monday"));
                break;
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

}