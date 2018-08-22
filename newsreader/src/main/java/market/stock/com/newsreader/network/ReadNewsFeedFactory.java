package market.stock.com.newsreader.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import market.stock.com.newsreader.models.NewsFeedResponse;
import market.stock.com.newsreader.util.GsonRequest;

/**
 * Created by Varshini on 18/08/2018.
 */

public class ReadNewsFeedFactory {

    private static ReadNewsFeedFactory mInstance;
    public static final String TAG = ReadNewsFeedFactory.class.getSimpleName();

    public static synchronized ReadNewsFeedFactory getInstance() {
        if (mInstance == null) {
            mInstance = new ReadNewsFeedFactory();
        }
        return mInstance;
    }
    public void getNewsList(NewsFeedReceivedCallback callback, final Context context) {

        String url = "https://api.iextrading.com/1.0/stock/market/news";

        final NewsFeedReceivedCallback newsFeedReceivedCallback = callback;

        GsonRequest<NewsFeedResponse> request = new GsonRequest<>(url, NewsFeedResponse.class, null, new Response.Listener<NewsFeedResponse>() {
            @Override
            public void onResponse(NewsFeedResponse response) {

                newsFeedReceivedCallback.OnNewsFeedReceived(response);
            }
        },   new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                newsFeedReceivedCallback.OnNewsListFailed(error);
            }
        });
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestFactory.getInstance(context).addToRequestQueue(request);
    }

    public interface NewsFeedReceivedCallback {
        void OnNewsFeedReceived(NewsFeedResponse response);

        void OnNewsListFailed(Exception exception);
    }
}
