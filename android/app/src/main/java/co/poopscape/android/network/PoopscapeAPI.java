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
        mContext = context;
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
     * Send request to create a new user
     *
     * @param firstName User first name
     * @param lastInit User last initial
     * @param email User email
     * @param listener Listener called on success
     */
    public void postNewUser(String firstName, String lastInit, String email, Response.Listener listener) {
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
