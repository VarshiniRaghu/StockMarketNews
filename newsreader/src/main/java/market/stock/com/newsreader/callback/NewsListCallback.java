package market.stock.com.newsreader.callback;

import java.util.ArrayList;

import market.stock.com.newsreader.models.NewsFeed;

/**
 * Created by Varshini on 21/08/2018.
 */

public interface NewsListCallback {

    void OnNewsListReceived(ArrayList<NewsFeed> newsList);

    void OnNewsListFailed(Exception exception);

}
