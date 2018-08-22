package market.stock.com.newsreader.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Varshini on 18/08/2018.
 */

public class NewsFeed {

    @SerializedName("datetime")
    private String datetime;
    @SerializedName("headline")
    private String headline;
    @SerializedName("source")
    private String source;
    @SerializedName("summary")
    private String summary;
    @SerializedName("url")
    private String url;
    @SerializedName("related")
    private String related;
    @SerializedName("image")
    private String image;

    public String getDatetime() {
        return datetime;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSource() {
        return source;
    }

    public String getSummary() {
        return summary;
    }

    public String getUrl() {
        return url;
    }

    public String getRelated() {
        return related;
    }

    public String getImage() {
        return image;
    }

    public NewsFeed(String datetime,String headline,String source, String url, String summary ,String related, String image){
        this.datetime = datetime;
        this.headline = headline;
        this.source = source;
        this.url = url;
        this.summary = summary;
        this.related = related;
        this.image = image;
    }

}
