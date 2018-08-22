package com.stock.marketnews;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stock.marketnews.fragment.CompanyDetailsFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import market.stock.com.newsreader.models.NewsFeed;

/**
 * Created by Varshini on 19/08/2018.
 */
public class NewsFeedCollectionAdapter extends RecyclerView.Adapter<NewsFeedCollectionAdapter.ViewHolder> {

    private ArrayList<NewsFeed> mDataset;
    private Context mContext;

    public NewsFeedCollectionAdapter(ArrayList<NewsFeed> data) {
        mDataset = data;
    }

    public ArrayList<NewsFeed> getData() {
        return mDataset;
    }

    public void setData(ArrayList<NewsFeed> data) {
        mDataset = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_new_feed_each_row, parent, false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        populateUIWithEpisode(mDataset.get(position), holder);
    }

    private void populateUIWithEpisode(NewsFeed episode, ViewHolder holder) {

        // load image
        Picasso.with(holder.imageView.getContext()).load(episode.getUrl()).fit().into(holder.imageView);
        holder.dateTimeTextView.setText(parseDateTimeString(episode.getDatetime()));
        holder.headLineTextView.setText(episode.getHeadline());
        holder.sourceTextView.setText(episode.getSource());
        holder.summaryTextView.setText(episode.getSummary());
        holder.relatedKeywords.setText(episode.getRelated());
        TextView textView = createTextViewForRelated("UEUR");
        if (textView != null) {
            holder.relatedLinearLayout.addView(textView);
        }
    }

    private String parseDateTimeString(String dateTime) {
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        Date stringDate = new Date();
        try {
            stringDate = simpledateformat.parse(dateTime);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return stringDate.toString();
    }

    private TextView createTextViewForRelated(String relatedItems) {
        List<String> listOfRelatedItems = Arrays.asList(relatedItems.split(","));
        for (String eachItem : listOfRelatedItems) {
            if (eachItem.length() <= 4 && eachItem.equals(eachItem.toUpperCase())) {
                return createTextViewForEachCompany(eachItem);
            }
        }
        return null;
    }

    private TextView createTextViewForEachCompany(String companyName) {
        TextView textView = new TextView(StockMarketNewsApplication.getAppContext());
        textView.setPadding(10, 10, 10, 10);
        textView.setText(companyName);
        textView.setTag(companyName);
        textView.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
        textView.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String companyId = "";
                if (view.getTag() != null) {
                    companyId = view.getTag().toString();
                }
                CompanyDetailsFragment companyDetailsFragment = CompanyDetailsFragment.newInstance(companyId);
                ((LoginActivity) mContext).getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, companyDetailsFragment)
                        .addToBackStack("company details")
                        .commit();
            }
        });
        return textView;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
//        return mDataset.size() + (mIsLandscape ? 4 : 2);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
//        return mIsLandscape ? position - 3 : position - 1;
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView dateTimeTextView;
        public TextView headLineTextView;
        public TextView sourceTextView;
        public TextView summaryTextView;
        public LinearLayout relatedLinearLayout;
        public ImageView imageView;
        public TextView relatedKeywords;

        public ViewHolder(View v) {
            super(v);

            imageView = v.findViewById(R.id.imageView);
            dateTimeTextView = v.findViewById(R.id.datetime);
            headLineTextView = v.findViewById(R.id.headline);
            sourceTextView = v.findViewById(R.id.source);
            summaryTextView = v.findViewById(R.id.summary);
            relatedLinearLayout = v.findViewById(R.id.related);
            relatedKeywords = v.findViewById(R.id.related_text);
        }
    }

}
