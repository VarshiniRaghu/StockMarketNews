package market.stock.com.newsreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varshini on 18/08/2018.
 */

public class NewsFeedResponse implements Parcelable{
    public ArrayList<NewsFeed> getNewsFeeds() {
        return newsFeeds;
    }

    @SerializedName("response")
    private ArrayList<NewsFeed> newsFeeds;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
       // dest.writeList(data.shows);
    }

    private NewsFeedResponse(Parcel in) {
       // data.shows = in.readArrayList(NewsFeed.class.getClassLoader());
    }
    public static final Parcelable.Creator<NewsFeedResponse> CREATOR = new Parcelable.Creator<NewsFeedResponse>() {
        public NewsFeedResponse createFromParcel(Parcel in) {
            return new NewsFeedResponse(in);
        }

        public NewsFeedResponse[] newArray(int size) {
            return new NewsFeedResponse[size];
        }
    };
    private class NewsFeedData {
        private ArrayList<NewsFeed> shows;
    }
}
