package com.gymkhana.iitbapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.util.ApiUtil;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.ServerUrls;

@SuppressLint("NewApi")
public class FeedSubscriptionFragment extends Fragment {

    public String mTitle;
    public AdapterView.OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private MainActivity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_layout, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(mOnItemClickListener);

        String link = ServerUrls.getInstance().FEEDS;
        String fileName = Constants.Filenames.INFO_FEED;
        Integer dataType = Constants.DATA_TYPE_FEED_INFO;
        boolean mFileExists = false;

        if (fileName != null) {
            String json = Functions.offlineDataReader(mContext, fileName);
            if (json != null && !json.isEmpty()) {
                mFileExists = true;
                ApiUtil.onSubscriptionDataResult(json, mContext, listView);
            }
        }

        ApiUtil.makeApiCall(
                link,
                mContext,
                dataType,
                listView,
                null,
                fileName,
                mFileExists
        );

        Functions.setActionBarTitle(mActivity, mContext.getString(R.string.title_subscription));
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = (MainActivity) activity;
    }
}
