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
}
