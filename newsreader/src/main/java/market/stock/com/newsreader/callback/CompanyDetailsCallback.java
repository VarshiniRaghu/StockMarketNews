package market.stock.com.newsreader.callback;

import market.stock.com.newsreader.models.CompanyDetails;

/**
 * Created by Varshini on 22/08/2018.
 */

public interface CompanyDetailsCallback {

    void OnCompanyDetailsReceived(CompanyDetails companyDetails);

    void OnCompanyDetailsFailed(Exception exception);

}
