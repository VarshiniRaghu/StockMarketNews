package market.stock.com.newsreader.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Varshini on 22/08/2018.
 */

public class CompanyDetails {
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("companyName")
    private String companyName;
    @SerializedName("exchange")
    private String exchange;
    @SerializedName("website")
    private String website;
    @SerializedName("description")
    private String description;
    @SerializedName("CEO")
    private String CEO;
    @SerializedName("sector")
    private String sector;

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getExchange() {
        return exchange;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public String getCEO() {
        return CEO;
    }

    public String getSector() {
        return sector;
    }
}
