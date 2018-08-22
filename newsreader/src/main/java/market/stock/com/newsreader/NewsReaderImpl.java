package market.stock.com.newsreader;

import android.content.Context;

import java.util.ArrayList;

import market.stock.com.newsreader.callback.CompanyDetailsCallback;
import market.stock.com.newsreader.callback.NewsListCallback;
import market.stock.com.newsreader.local.LocalNewsFeedReader;
import market.stock.com.newsreader.models.CompanyDetailsResponse;
import market.stock.com.newsreader.models.NewsFeed;
import market.stock.com.newsreader.models.NewsFeedResponse;
import market.stock.com.newsreader.network.CompanyDetailsFactory;
import market.stock.com.newsreader.network.ReadNewsFeedFactory;

/**
 * Created by Varshini on 18/08/2018.
 */

public class NewsReaderImpl implements NewsReader{
    private static NewsReaderImpl mInstance;
    private NewsListCallback newsListCallback;
    private CompanyDetailsCallback companyDetailsCallback;
    private Context mContext;
    public static synchronized NewsReader getInstance() {
        if (mInstance == null) {
            mInstance = new NewsReaderImpl();
        }
        return mInstance;
    }
    @Override
    public void getNewsList(NewsListCallback callback, Context context) {
        newsListCallback = callback;
        mContext = context;
        ArrayList<NewsFeed> newsFeedList = LocalNewsFeedReader.getInstance(context).readInfo();
        if(newsFeedList.size() > 0) {
            callback.OnNewsListReceived(newsFeedList);
        } else {
            ReadNewsFeedFactory.getInstance().getNewsList(newsFeedFromNetwork, context);
        }
    }

    @Override
    public void loadNewData(NewsListCallback callback, Context context) {
        ReadNewsFeedFactory.getInstance().getNewsList(newsFeedFromNetwork, context);
    }

    @Override
    public void getCompanyDetails(CompanyDetailsCallback callback, String companyId) {
        companyDetailsCallback = callback;
        CompanyDetailsFactory.getInstance().getCompanyDetails(companyId , companyDetailsReceivedCallback,mContext);
    }

    private ReadNewsFeedFactory.NewsFeedReceivedCallback newsFeedFromNetwork= new ReadNewsFeedFactory.NewsFeedReceivedCallback() {
        @Override
        public void OnNewsFeedReceived(NewsFeedResponse response) {
            LocalNewsFeedReader.getInstance(mContext).deleteValues();
            for(NewsFeed feed : response.getNewsFeeds()) {
                LocalNewsFeedReader.getInstance(mContext).insertValues(feed);
            }
            newsListCallback.OnNewsListReceived(response.getNewsFeeds());
        }

        @Override
        public void OnNewsListFailed(Exception exception) {

        }
    };

    private CompanyDetailsFactory.CompanyDetailsReceivedCallback companyDetailsReceivedCallback = new CompanyDetailsFactory.CompanyDetailsReceivedCallback() {
        @Override
        public void OnCompanyDetailsReceived(CompanyDetailsResponse response) {
            companyDetailsCallback.OnCompanyDetailsReceived(response.getCompanyDetails());
        }

        @Override
        public void OnCompanyDetailsFailed(Exception exception) {

        }
    };
}
