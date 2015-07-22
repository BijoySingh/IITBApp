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
import android.widget.ListView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.GenericItem;
import com.iitblive.iitblive.util.Functions;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class GenericListFragment extends Fragment {

    public static List<GenericItem> mList = new ArrayList<>();
    public static AdapterView.OnItemClickListener mOnItemClickListener;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_layout, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(mOnItemClickListener);
        Functions.setupList(mList, listView, mContext);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
}
