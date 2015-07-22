package com.iitblive.iitblive.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.GenericItem;
import com.iitblive.iitblive.lvadapter.LVAdapterDrawer;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.iitblive.iitblive.util.LoginFunctions;

import java.util.ArrayList;
import java.util.List;

public class DrawerFragment extends Fragment {
    public static int DRAWER_TAG_LOGIN = 0;
    public static int DRAWER_TAG_LOGOUT = 1;
    public static int DRAWER_TAG_NEWS = 2;
    public static int DRAWER_TAG_EVENTS = 3;
    public static int DRAWER_TAG_TIMETABLE = 4;
    public static int DRAWER_TAG_INFORMATION = 5;
    public static int DRAWER_TAG_ABOUT = 6;
    public static int DRAWER_TAG_NOTICES = 7;
    public static int DRAWER_TAG_HOME = 8;

    public DrawerLayout mDrawerLayout;
    public ListView mListView = null;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<GenericItem> mDrawerListView = new ArrayList<GenericItem>();
    private View mRootView;
    private OnFragmentInteractionListener mListener;
    private Context mContext;

    public DrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrawerFragment newInstance(String param1, String param2) {
        DrawerFragment fragment = new DrawerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.drawer_layout,
                container, false);
        return mRootView;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setup(DrawerLayout dl) {
        mDrawerLayout = dl;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), dl,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public ListView setupListView() {
        if (!LoginFunctions.isUserLoggedIn(mContext)) {
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_information,
                    getString(R.string.drawer_information),
                    DRAWER_TAG_INFORMATION));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_timetable,
                    getString(R.string.drawer_timetable),
                    DRAWER_TAG_TIMETABLE));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_about,
                    getString(R.string.drawer_about),
                    DRAWER_TAG_ABOUT));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_login,
                    getString(R.string.drawer_login),
                    DRAWER_TAG_LOGIN));
        } else {
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_home,
                    getString(R.string.drawer_home),
                    DRAWER_TAG_HOME));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_news,
                    getString(R.string.drawer_news),
                    DRAWER_TAG_NEWS));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_events,
                    getString(R.string.drawer_events),
                    DRAWER_TAG_EVENTS));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_notice,
                    getString(R.string.drawer_notices),
                    DRAWER_TAG_NOTICES));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_information,
                    getString(R.string.drawer_information),
                    DRAWER_TAG_INFORMATION));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_timetable,
                    getString(R.string.drawer_timetable),
                    DRAWER_TAG_TIMETABLE));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_about,
                    getString(R.string.drawer_about),
                    DRAWER_TAG_ABOUT));
            mDrawerListView.add(new GenericItem(
                    R.drawable.drawer_icon_login,
                    getString(R.string.drawer_logout),
                    DRAWER_TAG_LOGOUT));
        }

        GenericItem[] darray = new GenericItem[mDrawerListView.size()];
        mDrawerListView.toArray(darray);

        LVAdapterDrawer LVAdapterDrawer = new LVAdapterDrawer(getActivity(), darray);

        RelativeLayout rl = (RelativeLayout) mDrawerLayout.findViewById(R.id.drawer_title);
        TextView name = (TextView) rl.findViewById(R.id.name);

        name.setText(Functions.loadSharedPreference(getActivity(),
                Constants.SP_NAME));

        mListView = (ListView) mDrawerLayout.findViewById(R.id.drawer_listview);

        if (LVAdapterDrawer != null && mListView != null) {
            mListView.setAdapter(LVAdapterDrawer);
        } else if (LVAdapterDrawer == null) {
        } else if (mListView == null) {
        }
        return mListView;
    }

    public interface OnFragmentInteractionListener {
    }
}
