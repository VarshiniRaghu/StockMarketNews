package market.stock.com.newsreader.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;

import market.stock.com.newsreader.models.NewsFeed;

/**
 * Created by Varshini on 19/08/2018.
 */

public class LocalNewsFeedReader {
    private LocalNewsFeedReader() {}
    private static LocalNewsFeedReader mInstance;
    private static FeedReaderDbHelper mDbHelper;
    public static synchronized LocalNewsFeedReader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new LocalNewsFeedReader();
            mDbHelper = new FeedReaderDbHelper(context);
        }
        return mInstance;
    }
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "newsFeed";
        public static final String COLUMN_NAME_DATETIME = "datetime";
        public static final String COLUMN_NAME_HEADLINE = "headline";
        public static final String COLUMN_NAME_SOURCE = "source";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_SUMMARY = "summary";
        public static final String COLUMN_NAME_RELATED = "related";
        public static final String COLUMN_NAME_IMAGE = "image";
    }

    public void insertValues(NewsFeed feedValue){
        // Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_DATETIME, feedValue.getDatetime());
        values.put(FeedEntry.COLUMN_NAME_HEADLINE, feedValue.getHeadline());
        values.put(FeedEntry.COLUMN_NAME_SOURCE, feedValue.getSource());
        values.put(FeedEntry.COLUMN_NAME_URL, feedValue.getUrl());
        values.put(FeedEntry.COLUMN_NAME_SUMMARY, feedValue.getSummary());
        values.put(FeedEntry.COLUMN_NAME_RELATED, feedValue.getRelated());
        values.put(FeedEntry.COLUMN_NAME_IMAGE, feedValue.getImage());
        long newRowId = db.insert(FeedEntry.TABLE_NAME, null, values);
    }

    public void deleteValues(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(FeedEntry.TABLE_NAME,"",null);
    }
    public ArrayList<NewsFeed> readInfo(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                FeedEntry.COLUMN_NAME_DATETIME,
                FeedEntry.COLUMN_NAME_HEADLINE,
                FeedEntry.COLUMN_NAME_SOURCE,
                FeedEntry.COLUMN_NAME_URL,
                FeedEntry.COLUMN_NAME_IMAGE,
                FeedEntry.COLUMN_NAME_RELATED,
                FeedEntry.COLUMN_NAME_SUMMARY

        };

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        ArrayList feedItems = new ArrayList();
        while(cursor.moveToNext()) {
            NewsFeed feedEntry = new NewsFeed( cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_DATETIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_HEADLINE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SOURCE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_SUMMARY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_RELATED)),
                    cursor.getString(cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_IMAGE)));
            feedItems.add(feedEntry);
        }
        cursor.close();
        return feedItems;
    }
}
