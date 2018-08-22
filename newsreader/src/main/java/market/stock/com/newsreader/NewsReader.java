package market.stock.com.newsreader;

import android.content.Context;

import market.stock.com.newsreader.callback.CompanyDetailsCallback;
import market.stock.com.newsreader.callback.NewsListCallback;

/**
 * Created by Varshini on 18/08/2018.
 */

public interface NewsReader {

    void getNewsList(NewsListCallback callback, Context context);

    void loadNewData(NewsListCallback callback, Context context);

    void getCompanyDetails(CompanyDetailsCallback callback, String companyId);

}
