package market.stock.com.newsreader.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Varshini on 18/08/2018.
 */

public class RequestFactory {
    private static RequestFactory mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private RequestFactory() {
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestFactory getInstance(Context context) {
        if (mInstance == null) {
            mContext = context;
            mInstance = new RequestFactory();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelRequest(String tag) {
        getRequestQueue().cancelAll(tag);
    }
}
