package co.poopscape.android.network.models;

/**
 * Created by jon on 12/27/15.
 */
public class User {
    String _id;
    String fname;
    String linit;
    String email;
    // @TODO array Review reviews
    // @TODO array CheckIn checkins

    public String getId() {
        return _id;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastInit() {
        return linit;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "_id: " + _id + "\n" +
               "fname: " + fname + "\n" +
               "linit: " + linit + "\n" +
               "email: " + email + "\n";
    }
}
