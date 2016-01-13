package co.poopscape.android.network.models;

/**
 * Created by jon on 12/29/15.
 */
public class Location {
    String _id;
    String name;
    String street_address;
    String city_address;
    String state_address;
    String zip_address;
    float avg_rating;
    int total_ratings;
    LocCoords loc;

    class LocCoords {
        String type;
        float[] coordinates;
    }

    public String getId() {
        return _id;
    }

    public String toString() {
        return "_id: " + _id + "\n" +
               "name: " + name + "\n" +
               "street_address: " + street_address + "\n" +
               "city_address: " + city_address + "\n" +
               "state_address: " + state_address + "\n" +
               "zip_address: " + zip_address + "\n" +
               "avg_rating: " + avg_rating + "\n" +
               "total_ratings: " + total_ratings + "\n" +
               "coords: (" + loc.coordinates[0] + ", " + loc.coordinates[1] + ")";
    }
}
