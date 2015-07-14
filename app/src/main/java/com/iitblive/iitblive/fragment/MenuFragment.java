package com.iitblive.iitblive.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.internet.DownloadJson;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.DownloadJsonUtil;
import com.iitblive.iitblive.util.Functions;


@SuppressLint("NewApi")
public class MenuFragment extends Fragment {

    private Context mContext;
    private MainActivity mActivity;
    private Integer mMode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gridview_layout, container, false);

        GridView gridView = (GridView) rootView.findViewById(R.id.grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArticleFragment.mArticle = (EventListViewItem) parent.getAdapter().getItem(position);
                mActivity.displayFragment(MainActivity.SHOW_ARTICLE);
            }
        });

        String fileName = null;
        String link = null;
        Integer dataType = null;

        Bundle bundle = this.getArguments();
        mMode = bundle.getInt(MainActivity.FRAME_TYPE_KEY, MainActivity.SHOW_NEWS);

        if (mMode == MainActivity.SHOW_NEWS) {
            link = Constants.URL_NEWS;
            dataType = Constants.DATA_TYPE_NEWS;
            fileName = Constants.FILENAME_NEWS;
        } else if (mMode == MainActivity.SHOW_EVENTS) {
            link = Constants.URL_EVENTS;
            dataType = Constants.DATA_TYPE_EVENT;
            fileName = Constants.FILENAME_EVENT;
        } else if (mMode == MainActivity.SHOW_NOTICES) {
            link = Constants.URL_NOTICES;
            dataType = Constants.DATA_TYPE_NOTICE;
            fileName = Constants.FILENAME_NOTICE;
        }

        if (fileName != null) {
            String json = Functions.offlineDataReader(mContext, fileName);
            if (json != null || !json.isEmpty()) {
                DownloadJsonUtil.onGetDataResult(json, dataType, mContext, gridView);
            }
        }

        DownloadJson downloadJson = new DownloadJson(
                link,
                mContext,
                dataType,
                Constants.DATA_COUNT_MULTIPLE,
                gridView
        );
        downloadJson.setStorageFile(fileName);
        downloadJson.execute();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = (MainActivity) activity;
    }
}
