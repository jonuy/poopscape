package co.poopscape.android.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by jon on 11/24/15.
 */
public class NetworkHelper {

    // Singleton instance
    private static NetworkHelper mInstance;

    // Application context
    private static Context mContext;

    // Request queue to add requests to
    private RequestQueue mRequestQueue;

    private NetworkHelper(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkHelper(context);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // Uses application context so memory doesn't leak like it would for an Activity or Broadcast
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
