package co.poopscape.android.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jon on 12/27/15.
 */
public class GsonRequest<T> extends JsonRequest<T> {
    private final Gson mGson = new Gson();
    private final Class<T> mClazz;
    private final Map<String, String> mHeaders;
    private final Response.Listener<T> mListener;
    private final Response.ErrorListener mErrorListener;

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param method Request method @see com.android.volley.Request.Method
     * @param url URL of the request to make
     * @param jsonRequest Request data
     * @param clazz Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, JsonObject jsonRequest, Class<T> clazz, Map<String,
                       String> headers, Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(
                method,
                url,
                jsonRequest == null ? null : jsonRequest.toString(),
                listener,
                errorListener
        );

        mClazz = clazz;
        mHeaders = headers;
        mListener = listener;
        mErrorListener = errorListener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> h = mHeaders;
        if (h == null) {
            h = new HashMap<String, String>();
        }

        h.put("Accept", "application/json");
        h.put("Content-Type", "application/json; charset=utf-8");

        return h;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    mGson.fromJson(json, mClazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
