package co.poopscape.android.network.models;

/**
 * Created by jon on 1/4/16.
 */
public class LocationDistResponse {
    float dis;
    Location obj;

    public float getDistance() {
        return dis;
    }

    public Location getLocation() {
        return obj;
    }

    public String toString() {
        return "Distance: " + dis + "\n" + obj.toString();
    }
}
