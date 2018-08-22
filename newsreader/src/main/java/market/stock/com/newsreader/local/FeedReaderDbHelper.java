package market.stock.com.newsreader.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Varshini on 19/08/2018.
 */

public class FeedReaderDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LocalNewsFeedReader.FeedEntry.TABLE_NAME + " (" +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_URL + " TEXT PRIMARY KEY," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_DATETIME + " TEXT," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_HEADLINE + " TEXT," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_SOURCE + " TEXT," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_SUMMARY + " TEXT," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_RELATED + " TEXT," +
                    LocalNewsFeedReader.FeedEntry.COLUMN_NAME_IMAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LocalNewsFeedReader.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
