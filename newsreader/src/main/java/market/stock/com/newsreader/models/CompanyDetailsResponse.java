package market.stock.com.newsreader.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Varshini on 22/08/2018.
 */

public class CompanyDetailsResponse {

    @SerializedName("response")
    private CompanyDetails companyDetails;

    public CompanyDetails getCompanyDetails() {
        return companyDetails;
    }

}
