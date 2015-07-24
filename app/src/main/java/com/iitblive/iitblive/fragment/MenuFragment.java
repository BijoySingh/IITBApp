package com.iitblive.iitblive.fragment;

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

import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.activity.ArticleActivity;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.util.ApiUtil;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.iitblive.iitblive.util.ServerUrls;


@SuppressLint("NewApi")
public class MenuFragment extends Fragment {

    private Context mContext;
    private MainActivity mActivity;
    private Integer mMode;
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
                    ArticleActivity.mArticle = (ApiItem) parent.getAdapter().getItem(position);
                    Intent articleIntent = new Intent(mContext, ArticleActivity.class);
                    startActivity(articleIntent);
                } catch (Exception e) {
                    ;
                }
            }
        });

        String title = mContext.getString(R.string.drawer_home);
        String fileName = null;
        String link = null;
        Integer dataType = null;

        Bundle bundle = this.getArguments();
        mMode = bundle.getInt(MainActivity.FRAME_TYPE_KEY, MainActivity.SHOW_NEWS);

        if (mMode == MainActivity.SHOW_NEWS) {
            title = mContext.getString(R.string.drawer_news);
            link = ServerUrls.getInstance().NEWS;
            dataType = Constants.DATA_TYPE_NEWS;
            fileName = Constants.Filenames.NEWS;
        } else if (mMode == MainActivity.SHOW_EVENTS) {
            title = mContext.getString(R.string.drawer_events);
            link = ServerUrls.getInstance().EVENTS;
            dataType = Constants.DATA_TYPE_EVENT;
            fileName = Constants.Filenames.EVENT;
        } else if (mMode == MainActivity.SHOW_NOTICES) {
            title = mContext.getString(R.string.drawer_notices);
            link = ServerUrls.getInstance().NOTICES;
            dataType = Constants.DATA_TYPE_NOTICE;
            fileName = Constants.Filenames.NOTICE;
        }

        if (fileName != null) {
            String json = Functions.offlineDataReader(mContext, fileName);
            if (json != null || !json.isEmpty()) {
                mFileExists = true;
                ApiUtil.onGetDataResult(json, dataType, mContext, gridView);
            }
        }

        if (!mFileExists) {
            ApiUtil.makeApiCall(
                    link,
                    mContext,
                    dataType,
                    Constants.DATA_COUNT_MULTIPLE,
                    gridView,
                    null,
                    fileName,
                    mFileExists
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
