package co.poopscape.android.network.models;

/**
 * Created by jon on 12/29/15.
 */
public class Review {
    String _id;
    String lid;
    String uid;
    int rating;
    String review;
    String photo;

    public String getId() {
        return _id;
    }

    public String getLocationId() {
        return lid;
    }

    public String getUserId() {
        return uid;
    }

    public int getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getPhoto() {
        return photo;
    }
}
