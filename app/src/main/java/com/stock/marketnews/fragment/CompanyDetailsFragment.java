package com.stock.marketnews.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stock.marketnews.R;

import market.stock.com.newsreader.NewsReader;
import market.stock.com.newsreader.NewsReaderImpl;
import market.stock.com.newsreader.callback.CompanyDetailsCallback;
import market.stock.com.newsreader.models.CompanyDetails;

/**
 * Created by Varshini on 21/08/2018.
 */

public class CompanyDetailsFragment extends Fragment {
    private final static String COMPANY_ID = "CompanyId";
    private View rootView;
    private String mCompanyId;

    public static CompanyDetailsFragment newInstance(String companyId) {
        CompanyDetailsFragment fragment = new CompanyDetailsFragment();
        Bundle args = new Bundle();
        args.putString(COMPANY_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.fragment_company_details, container, false);
        if (getArguments() != null) {
            mCompanyId = getArguments().getString(COMPANY_ID);
        }
        initUI();
        return rootView;
    }

    private void initUI(){
        NewsReader newsReader = NewsReaderImpl.getInstance();
        newsReader.getCompanyDetails(new CompanyDetailsCallback() {
            @Override
            public void OnCompanyDetailsReceived(CompanyDetails companyDetails) {
                displayDetails(companyDetails);
            }

            @Override
            public void OnCompanyDetailsFailed(Exception exception) {

            }
        }, mCompanyId);
    }

    private void displayDetails(CompanyDetails companyDetails){
        if( companyDetails.getCompanyName().equals("")) {
            rootView.findViewById(R.id.company_name_layout).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.company_name)).setText(companyDetails.getCompanyName());
        }

        if(companyDetails.getExchange().equals("")) {
            rootView.findViewById(R.id.exchange_layout).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.exchange)).setText(companyDetails.getExchange());
        }

        if(companyDetails.getWebsite().equals("")) {
            rootView.findViewById(R.id.company_website_layout).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.company_website)).setText(companyDetails.getWebsite());
        }

        if(companyDetails.getCEO().equals("")) {
            rootView.findViewById(R.id.ceo_layout).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.ceo)).setText(companyDetails.getCEO());
        }

        if(companyDetails.getSector().equals("")) {
            rootView.findViewById(R.id.sector_layout).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.sector)).setText(companyDetails.getSector());
        }

        if(companyDetails.getDescription().equals("")) {
            rootView.findViewById(R.id.description).setVisibility(View.GONE);
        } else {
            ((TextView) rootView.findViewById(R.id.description)).setText(companyDetails.getDescription());
        }
    }
}
