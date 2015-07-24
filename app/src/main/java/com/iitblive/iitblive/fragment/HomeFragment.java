package com.iitblive.iitblive.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.items.NowCardItem;
import com.iitblive.iitblive.lvadapter.HomeRecyclerViewAdapter;
import com.iitblive.iitblive.util.ApiUtil;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.iitblive.iitblive.util.ServerUrls;

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
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout, container, false);
        setupRecycler(rootView);
        addEventCard();
        addNewsCard();
        addNoticeCard();

        Functions.setActionBarTitle(mActivity, mContext.getString(R.string.drawer_home));
        return rootView;
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


    public void addOfflineCard(NowCardMetaContent metaContent, int dataType, String
            fileName) {
        String json = Functions.offlineDataReader(mContext, fileName);
        if (json != null || !json.isEmpty()) {
            addCard(metaContent, ApiUtil.getEventListFromJson(mContext, json, dataType));
        }
    }

    public void addEventCard() {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_events),
                Constants.Colors.PRIMARY_DARK_EVENT,
                0, MainActivity.SHOW_EVENTS,
                R.drawable.drawer_icon_events);
        addOfflineCard(
                metaContent,
                Constants.DATA_TYPE_EVENT,
                Constants.Filenames.EVENT
        );
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().EVENTS,
                Constants.DATA_TYPE_EVENT,
                Constants.Filenames.EVENT
        );
    }

    public void addNoticeCard() {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_notices),
                Constants.Colors.PRIMARY_NOTICES,
                1, MainActivity.SHOW_NOTICES,
                R.drawable.drawer_icon_notice);
        addOfflineCard(
                metaContent,
                Constants.DATA_TYPE_NOTICE,
                Constants.Filenames.NOTICE
        );
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().NOTICES,
                Constants.DATA_TYPE_NOTICE,
                Constants.Filenames.NOTICE
        );
    }

    public void addNewsCard() {
        NowCardMetaContent metaContent = new NowCardMetaContent(
                getString(R.string.drawer_news),
                Constants.Colors.PRIMARY_DARK_NEWS, 2, MainActivity.SHOW_NEWS,
                R.drawable.drawer_icon_news);
        addOfflineCard(
                metaContent,
                Constants.DATA_TYPE_NEWS,
                Constants.Filenames.NEWS
        );
        addOnlineCard(
                metaContent,
                ServerUrls.getInstance().NEWS,
                Constants.DATA_TYPE_NEWS,
                Constants.Filenames.NEWS
        );
    }

    public void addCard(NowCardMetaContent metaContent, List<ApiItem> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        NowCardItem nowCardItem = new NowCardItem(metaContent, list);

        int position = 0;
        for (NowCardItem card : mCards) {
            if (card.mType == metaContent.type) {
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
    }

    public class NowCardMetaContent {
        public Integer type;
        public String title, description;
        public Integer color, fragmentId, iconResource;

        public NowCardMetaContent(String title, Integer color, Integer type, Integer fragmentId,
                                  Integer iconResource) {
            this.title = title;
            this.color = color;
            this.type = type;
            this.fragmentId = fragmentId;
            this.iconResource = iconResource;
        }
    }

}