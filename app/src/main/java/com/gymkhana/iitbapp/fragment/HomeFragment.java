package com.gymkhana.iitbapp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.FeedSubscriptionItem;
import com.gymkhana.iitbapp.items.NowCardItem;
import com.gymkhana.iitbapp.lvadapter.HomeRecyclerViewAdapter;
import com.gymkhana.iitbapp.util.ApiUtil;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.LocalData;
import com.gymkhana.iitbapp.util.ServerUrls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bijoy on 7/21/15.
 */
public class HomeFragment extends Fragment {
    public static List<NowCardItem> mCards = new ArrayList<>();
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    HomeRecyclerViewAdapter mAdapter;
    MainActivity mActivity;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        setupRecycler(rootView);

        createCards(false);

        Functions.setActionBarTitle(mActivity, mContext.getString(R.string.drawer_home));
        return rootView;
    }


    private void createCards(boolean isRefreshCall) {
        addEventCard(isRefreshCall);
        addNewsCard(isRefreshCall);
        addNoticeCard(isRefreshCall);
        addFeedCard(isRefreshCall);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void addOnlineCard(NowCardMetaContent metaContent, String link, int dataType, String fileName) {
        ApiUtil.makeApiCallForHome(
                link,
                mContext,
                dataType,
                fileName,
                this,
                metaContent);

    }

    public void addOfflineCard(final NowCardMetaContent metaContent,
                               final int dataType,
                               final String fileName) {

        String json = Functions.offlineDataReader(mContext, fileName);
        if (json != null || !json.isEmpty()) {
            addCard(metaContent, ApiUtil.getEventListFromJson(mContext, json, dataType));
        }

    }

    public void addEventCard(boolean isRefreshCall) {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_events),
                Constants.Colors.PRIMARY_DARK_EVENT,
                Constants.DATA_TYPE_EVENT, MainActivity.SHOW_EVENTS,
                R.drawable.drawer_icon_events, 0);
        if (!isRefreshCall) {
            addOfflineCard(
                    metaContent,
                    Constants.DATA_TYPE_EVENT,
                    Constants.Filenames.EVENT
            );
        }
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().EVENTS,
                Constants.DATA_TYPE_EVENT,
                Constants.Filenames.EVENT
        );
    }

    public void addNoticeCard(boolean isRefreshCall) {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_notices),
                Constants.Colors.PRIMARY_NOTICES,
                Constants.DATA_TYPE_NOTICE, MainActivity.SHOW_NOTICES,
                R.drawable.drawer_icon_notice, 1);

        if (!isRefreshCall) {
            addOfflineCard(
                    metaContent,
                    Constants.DATA_TYPE_NOTICE,
                    Constants.Filenames.NOTICE
            );
        }
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().NOTICES,
                Constants.DATA_TYPE_NOTICE,
                Constants.Filenames.NOTICE
        );
    }

    public void addNewsCard(boolean isRefreshCall) {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_news),
                Constants.Colors.PRIMARY_DARK_NEWS,
                Constants.DATA_TYPE_NEWS, MainActivity.SHOW_NEWS,
                R.drawable.drawer_icon_news, 2);
        if (!isRefreshCall) {
            addOfflineCard(
                    metaContent,
                    Constants.DATA_TYPE_NEWS,
                    Constants.Filenames.NEWS
            );
        }
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().NEWS,
                Constants.DATA_TYPE_NEWS,
                Constants.Filenames.NEWS
        );
    }

    public void addFeedCards(boolean isRefreshCall) {
        List<FeedSubscriptionItem> feedList = Functions.getSubscriptions(mContext);

        int position = 3;
        for (FeedSubscriptionItem feed : feedList) {
            if (LocalData.getBoolean(mContext, feed.prefKey(), true)) {
                NowCardMetaContent metaContent = new NowCardMetaContent(
                        feed.title,
                        Constants.Colors.PRIMARY_DARK_FEED,
                        Constants.DATA_TYPE_FEED,
                        MainActivity.SHOW_FEED,
                        R.drawable.drawer_icon_feed, position);

                if (!isRefreshCall) {
                    addOfflineCard(
                            metaContent,
                            Constants.DATA_TYPE_FEED,
                            feed.filename()
                    );
                }
                addOnlineCard(
                        metaContent,
                        ServerUrls.getInstance().FEED_ENTRIES + "?id=" + feed.feed_id,
                        Constants.DATA_TYPE_FEED,
                        feed.filename()
                );
            }
            position++;
        }
    }

    // This adds a single card with the feed
    public void addFeedCard(boolean isRefreshCall) {
        String link = ServerUrls.getInstance().getFeedUrl(mContext);

        if (link == null) {
            return;
        }

        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_feed),
                Constants.Colors.PRIMARY_DARK_FEED,
                Constants.DATA_TYPE_FEED, MainActivity.SHOW_FEED,
                R.drawable.drawer_icon_feed, 3);
        if (!isRefreshCall) {
            addOfflineCard(
                    metaContent,
                    Constants.DATA_TYPE_FEED,
                    Constants.Filenames.FEED
            );
        }

        addOnlineCard(
                metaContent,
                link,
                Constants.DATA_TYPE_FEED,
                Constants.Filenames.FEED
        );
    }

    public void addCard(NowCardMetaContent metaContent, List<ApiItem> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (list == null || list.isEmpty()) {
            return;
        }

        NowCardItem<ApiItem> nowCardItem = new NowCardItem(metaContent, list);

        int position = 0;
        for (NowCardItem<ApiItem> card : mCards) {
            if (card.mUniqueId == metaContent.uniqueLocation) {
                mCards.set(position, nowCardItem);
                mAdapter.notifyItemChanged(position);
                return;
            }
            position++;
        }
        mCards.add(nowCardItem);
        mAdapter.notifyItemInserted(mCards.size());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = (MainActivity) activity;
    }

    public void setupRecycler(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(false);

        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HomeRecyclerViewAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                createCards(true);
            }
        });
        mSwipeRefreshLayout.setEnabled(true);
    }

    public class NowCardMetaContent {
        public Integer type;
        public String title, description;
        public Integer color, fragmentId, iconResource, uniqueLocation;
        public FeedSubscriptionItem feed;

        public NowCardMetaContent(String title, Integer color, Integer type, Integer fragmentId,
                                  Integer iconResource, int uniqueLocation) {
            this.title = title;
            this.color = color;
            this.type = type;
            this.fragmentId = fragmentId;
            this.iconResource = iconResource;
            this.uniqueLocation = uniqueLocation;
        }

        public void setFeed(FeedSubscriptionItem feed) {
            this.feed = feed;
        }

    }

}
