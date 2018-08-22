package com.stock.marketnews;

import android.app.Application;
import android.content.Context;

/**
 * Created by Varshini on 18/08/2018.
 */

public class StockMarketNewsApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getAppContext() {
        return StockMarketNewsApplication.context;
    }
}
