package co.poopscape.android.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import co.poopscape.android.BuildConfig;
import co.poopscape.android.network.models.Location;
import co.poopscape.android.network.models.LocationDistResponse;
import co.poopscape.android.network.models.Review;
import co.poopscape.android.network.models.User;

/**
 * PoopscapeAPI service.
 *
 * Example usage:
 *
 *     PoopscapeAPI.getInstance(this).postNewUser(
 *         "android_first", "a", "android_first@example.com",
 *         new Response.Listener<User>() {
 *             @Override
 *             public void onResponse(User response) {
 *                 // do whatever with the success response here
 *             }
 *         });
 */
public class PoopscapeAPI {

    // Available API base URLs
    private final String STAGING_URL = "http://poopscape-staging.herokuapp.com";
    private final String PRODUCTION_URL = "http://poopscape-production.herokuapp.com";

    // Resource URL endpoints
    private final String LOCATIONS_RES = "/locations";
    private final String REVIEWS_RES = "/reviews";
    private final String USERS_RES = "/users";

    private static PoopscapeAPI mInstance;
    private Context mContext;

    private PoopscapeAPI(Context context) {
        mContext = context.getApplicationContext();
    }

    public static synchronized PoopscapeAPI getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PoopscapeAPI(context.getApplicationContext());
        }

        return mInstance;
    }

    /**
     * Get the base url based on the build type.
     *
     * @return String
     */
    private String getBaseUrl() {
        if (BuildConfig.BUILD_TYPE.equals("debug")) {
            return STAGING_URL;
        }
        else {
            return PRODUCTION_URL;
        }
    }

    /**
     * Send request to get an array of location info near a point.
     *
     * @param lat Latitude value
     * @param lng Longitude value
     * @param listener Listener called on success
     */
    public void getLocationNearPoint(float lat, float lng, Response.Listener listener) {
        int method = Request.Method.GET;
        String url = getBaseUrl() + LOCATIONS_RES + "?lat=" + String.valueOf(lat) + "&lng=" + String.valueOf(lng);
        Class responseClass = LocationDistResponse[].class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, null, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Send request to get a location's info by its id.
     *
     * @param id Location id
     * @param listener Listener called on success
     */
    public void getLocationById(String id, Response.Listener<Location> listener) {
        int method = Request.Method.GET;
        String url = getBaseUrl() + LOCATIONS_RES + "/" + id;
        Class responseClass = Location.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, null, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Send request to add a new location.
     *
     * @param name Location name
     * @param street Street address
     * @param city City address
     * @param state State address - should be the two-letter abbreviation
     * @param lat Location's latitude
     * @param lng Location's longitude
     * @param listener Listener called on success
     */
    public void postNewLocation(String name, String street, String city, String state, float lat,
                                float lng, Response.Listener listener) {
        int method = Request.Method.POST;
        String url = getBaseUrl() + LOCATIONS_RES + "/new";
        JsonObject data = new JsonObject();
        data.addProperty("name", name);
        data.addProperty("street", street);
        data.addProperty("city", city);
        data.addProperty("state", state);
        data.addProperty("lat", lat);
        data.addProperty("lng", lng);
        Class responseClass = Location.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, data, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Send request to submit a new review.
     *
     * @param locationId Location id
     * @param userId User id
     * @param rating Submitted rating
     * @param review Submitted review, if any
     * @param listener Listener called on success
     */
    public void postNewReview(String locationId, String userId, int rating, String review, Response.Listener<Review> listener) {
        int method = Request.Method.POST;
        String url = getBaseUrl() + REVIEWS_RES + "/new";
        JsonObject data = new JsonObject();
        data.addProperty("lid", locationId);
        data.addProperty("uid", userId);
        data.addProperty("rating", rating);
        if (review != null) {
            data.addProperty("review", review);
        }
        Class responseClass = Review.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, data, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Send request to create a new user.
     *
     * @param firstName User first name
     * @param lastInit User last initial
     * @param email User email
     * @param listener Listener called on success
     */
    public void postNewUser(String firstName, String lastInit, String email, Response.Listener<User> listener) {
        int method = Request.Method.POST;
        String url = getBaseUrl() + USERS_RES + "/new";
        JsonObject data = new JsonObject();
        data.addProperty("fname", firstName);
        data.addProperty("linit", lastInit);;
        data.addProperty("email", email);
        Class responseClass = User.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, data, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Send request to update a user.
     *
     * @param id User id
     * @param updates Updates to be made to this user
     * @param listener Listener called on success
     */
    public void updateUser(String id, User updates, Response.Listener<User> listener) {
        int method = Request.Method.PUT;
        String url = getBaseUrl() + USERS_RES + "/" + id;
        JsonObject data = new JsonObject();
        if (updates.getEmail() != null) {
            data.addProperty("email", updates.getEmail());
        }
        if (updates.getFirstName() != null) {
            data.addProperty("fname", updates.getFirstName());
        }
        if (updates.getLastInit() != null) {
            data.addProperty("linit", updates.getLastInit());
        }
        Class responseClass = User.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, data, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Retrieve a user by its email.
     *
     * @param email User email
     * @param listener Listener called if one is found
     */
    public void getUserByEmail(String email, Response.Listener listener) {
        int method = Request.Method.GET;
        String url = getBaseUrl() + USERS_RES + "/email/" + email;
        Class responseClass = User.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, null, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Retrieve a user by its id.
     *
     * @param id User id
     * @param listener Listener called if one is found
     */
    public void getUserById(String id, Response.Listener listener) {
        int method = Request.Method.GET;
        String url = getBaseUrl() + USERS_RES + "/id/" + id;
        Class responseClass = User.class;
        Map<String, String> headers = null;

        GsonRequest request = new GsonRequest(method, url, null, responseClass, headers, listener, new PoopscapeErrorListener());
        NetworkHelper.getInstance(mContext).addToRequestQueue(request);
    }

    /**
     * Custom error listener for all PoopscapeAPI requests.
     */
    private class PoopscapeErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError volleyError) {
            try {
                String responseBody = new String(volleyError.networkResponse.data, "utf-8");
                JSONObject json = new JSONObject(responseBody);
                String errorMessage = json.getString("error");

                Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();

                Log.e("PoopscapeAPI", "Status Code: " + volleyError.networkResponse.statusCode + " / Error Message: " + errorMessage);
            }
            catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
