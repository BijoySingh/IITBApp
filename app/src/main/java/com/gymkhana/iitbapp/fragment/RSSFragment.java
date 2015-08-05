package com.gymkhana.iitbapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.activity.RSSViewerActivity;
import com.gymkhana.iitbapp.feed.RSSFeedChannel;
import com.gymkhana.iitbapp.feed.RSSFeedConstants;
import com.gymkhana.iitbapp.feed.RSSFeedFetcher;
import com.gymkhana.iitbapp.feed.RSSFeedItem;
import com.gymkhana.iitbapp.lvadapter.LVAdapterRSS;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;


@SuppressLint("NewApi")
public class RSSFragment extends Fragment {

    public static RSSFeedConstants.Feed mFeed = null;
    private Context mContext;
    private MainActivity mActivity;
    private String mLink;
    private boolean mFileExists = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridview_layout, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    RSSFeedItem rssFeedItem = (RSSFeedItem) parent.getAdapter().getItem(position);
                    Intent rssIntent = new Intent(mContext, RSSViewerActivity.class);
                    rssIntent.putExtra(RSSViewerActivity.INTENT_RSS, rssFeedItem);
                    startActivity(rssIntent);
                } catch (Exception e) {
                    ;
                }
            }
        });

        String title = mContext.getString(R.string.drawer_feed);
        String fileName = mFeed.filename();
        String username = null;
        String password = null;

        if (mFeed.authenticated) {
            username = SharedPreferenceManager.getUsername(mContext);
            password = SharedPreferenceManager.getPassword(mContext);
        }

        if (fileName != null) {
            String contents = Functions.offlineDataReader(mContext, fileName);
            if (contents != null || !contents.isEmpty()) {
                mFileExists = true;
                RSSFeedChannel feedChannel = RSSFeedFetcher.parseFeed(contents);
                gridView.setAdapter(new LVAdapterRSS(mContext, feedChannel.entries));
            }
        }

        if (!mFileExists) {
            RSSFeedFetcher.getFeed(
                    mContext,
                    mFeed.url,
                    username,
                    password,
                    gridView,
                    mFeed
            );
        }
        Functions.setActionBarTitle(mActivity, title);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = (MainActivity) activity;
    }
}
