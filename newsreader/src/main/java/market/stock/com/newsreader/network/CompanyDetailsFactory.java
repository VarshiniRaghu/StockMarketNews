package market.stock.com.newsreader.network;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import market.stock.com.newsreader.models.CompanyDetails;
import market.stock.com.newsreader.models.CompanyDetailsResponse;
import market.stock.com.newsreader.models.NewsFeedResponse;
import market.stock.com.newsreader.util.GsonRequest;

/**
 * Created by Varshini on 22/08/2018.
 */

public class CompanyDetailsFactory {
    private static CompanyDetailsFactory mInstance;
    public static final String TAG = ReadNewsFeedFactory.class.getSimpleName();

    public static synchronized CompanyDetailsFactory getInstance() {
        if (mInstance == null) {
            mInstance = new CompanyDetailsFactory();
        }
        return mInstance;
    }
    public void getCompanyDetails(String companyId, CompanyDetailsFactory.CompanyDetailsReceivedCallback callback, final Context context) {

        String url = "https://api.iextrading.com/1.0/stock/" + companyId+ "/company";

        final CompanyDetailsFactory.CompanyDetailsReceivedCallback companyDetailsReceivedCallback = callback;

        GsonRequest<CompanyDetailsResponse> request = new GsonRequest<>(url, CompanyDetailsResponse.class, null, new Response.Listener<CompanyDetailsResponse>() {
            @Override
            public void onResponse(CompanyDetailsResponse response) {

                companyDetailsReceivedCallback.OnCompanyDetailsReceived(response);
            }
        },   new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                companyDetailsReceivedCallback.OnCompanyDetailsFailed(error);
            }
        });
        request.setTag(TAG);
        request.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                3,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestFactory.getInstance(context).addToRequestQueue(request);
    }

    public interface CompanyDetailsReceivedCallback {
        void OnCompanyDetailsReceived(CompanyDetailsResponse response);

        void OnCompanyDetailsFailed(Exception exception);
    }

}
