package com.stock.marketnews.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stock.marketnews.NewsFeedCollectionAdapter;
import com.stock.marketnews.R;
import com.stock.marketnews.StockMarketNewsApplication;

import java.util.ArrayList;

import market.stock.com.newsreader.NewsReader;
import market.stock.com.newsreader.NewsReaderImpl;
import market.stock.com.newsreader.callback.NewsListCallback;
import market.stock.com.newsreader.models.NewsFeed;


/**
 * Created by Varshini on 20/08/2018.
 */

public class NewsFeedFragment extends Fragment {

    NewsReader newsReader;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View rootView;
    private Context mContext;
    private NewsListCallback newsListCallback = new NewsListCallback() {
        @Override
        public void OnNewsListReceived(ArrayList<NewsFeed> newsList) {
            mAdapter = new NewsFeedCollectionAdapter(newsList);
            mRecyclerView.setAdapter(mAdapter);
        }

        @Override
        public void OnNewsListFailed(Exception exception) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(
                R.layout.fragment_feed_display, container, false);
        initList();
        return rootView;
    }

    public void initList() {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.NewsFeedRecyclerView);

        //init grid layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        if (mLayoutManager != null && mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            newsReader = NewsReaderImpl.getInstance();
            newsReader.getNewsList(newsListCallback, StockMarketNewsApplication.getAppContext());
        }
        FloatingActionButton fab = rootView.findViewById(R.id.fab_for_refresh);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newsReader.loadNewData(newsListCallback,mContext);
            }
        });
    }
}
