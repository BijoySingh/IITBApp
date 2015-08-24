package com.gymkhana.iitbapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gymkhana.iitbapp.activity.LoginActivity;
import com.gymkhana.iitbapp.fragment.DrawerFragment;
import com.gymkhana.iitbapp.fragment.FeedSubscriptionFragment;
import com.gymkhana.iitbapp.fragment.GenericListFragment;
import com.gymkhana.iitbapp.fragment.HomeFragment;
import com.gymkhana.iitbapp.fragment.MenuFragment;
import com.gymkhana.iitbapp.items.GenericItem;
import com.gymkhana.iitbapp.util.AuthFunctions;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.ListContent;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;

public class MainActivity extends ActionBarActivity implements DrawerFragment.OnFragmentInteractionListener {

    public static String FRAME_TYPE_KEY = "FRAME_TYPE";
    public static String FRAGMENT_KEY = "FRAGMENT_TYPE";

    public static Integer SHOW_HOME = 0;
    public static Integer SHOW_EVENTS = 1;
    public static Integer SHOW_NEWS = 2;
    public static Integer SHOW_TIMETABLE = 3;
    public static Integer SHOW_ABOUT = 4;
    public static Integer SHOW_INFORMATION = 5;
    public static Integer SHOW_DEVELOPERS = 6;
    public static Integer SHOW_NOTICES = 7;
    public static Integer SHOW_FEED = 8;
    public static Integer SHOW_FEED_PREFERENCES = 9;

    private Context mContext;
    private DrawerFragment mDrawerFragment;
    private Integer mFragmentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        startupFunctions();
        firstTimeSetup();
        ListContent.resetVariables();

        int startState = SHOW_INFORMATION;
        if (AuthFunctions.isUserLoggedIn(mContext)) {
            startState = SHOW_HOME;
        }

        try {
            Integer tempState = getIntent().getExtras().getInt(FRAGMENT_KEY);
            if (tempState != null) {
                startState = tempState;
            }
        } catch (Exception e) {
            ;
        }

        Functions.setActionBar(this);
        Functions.setActionBarTitle(this, getString(R.string.title_home));

        mDrawerFragment =
                (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_bar);
        mDrawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout));
        ListView lv = mDrawerFragment.setupListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerFragment.mDrawerLayout.closeDrawers();
                GenericItem glvi = (GenericItem) parent.getItemAtPosition(position);
                if (glvi.tag == DrawerFragment.DRAWER_TAG_HOME) {
                    displayFragment(SHOW_HOME);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_NEWS) {
                    displayFragment(SHOW_NEWS);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_NOTICES) {
                    displayFragment(SHOW_NOTICES);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_EVENTS) {
                    displayFragment(SHOW_EVENTS);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_INFORMATION) {
                    displayFragment(SHOW_INFORMATION);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_FEED) {
                    displayFragment(SHOW_FEED);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_TIMETABLE) {
                    displayFragment(SHOW_TIMETABLE);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_ABOUT) {
                    displayFragment(SHOW_ABOUT);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_LOGIN) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
                    ((Activity) mContext).finish();
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_LOGOUT) {
                    AuthFunctions.logout(mContext);
                }
            }
        });

        displayFragment(startState);
    }

    public void displayFragment(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        mFragmentPosition = position;

        if (position == SHOW_HOME) {
            fragment = new HomeFragment();
        } else if (position == SHOW_NEWS) {
            fragment = new MenuFragment();
            bundle.putInt(FRAME_TYPE_KEY, SHOW_NEWS);
        } else if (position == SHOW_EVENTS) {
            fragment = new MenuFragment();
            bundle.putInt(FRAME_TYPE_KEY, SHOW_EVENTS);
        } else if (position == SHOW_NOTICES) {
            fragment = new MenuFragment();
            bundle.putInt(FRAME_TYPE_KEY, SHOW_NOTICES);
        } else if (position == SHOW_INFORMATION) {
            ListContent.InformationFragmentData(mContext);
            GenericListFragment.mTitle = getString(R.string.drawer_information);
            GenericListFragment.mList = ListContent.mInformationList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mInformationOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_TIMETABLE) {
            GenericListFragment.mTitle = getString(R.string.drawer_timetable);
            ListContent.TimetableFragmentData(mContext);
            GenericListFragment.mList = ListContent.mTimetableList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mTimetableOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_ABOUT) {
            GenericListFragment.mTitle = getString(R.string.drawer_settings);
            ListContent.SettingsFragmentData(mContext);
            GenericListFragment.mList = ListContent.mSettingsList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mSettingsOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_DEVELOPERS) {
            ListContent.DeveloperFragmentData(mContext);
            GenericListFragment.mTitle = getString(R.string.settings_about_us_title);
            GenericListFragment.mList = ListContent.mDeveloperList;
            GenericListFragment.mOnItemClickListener = null;
            fragment = new GenericListFragment();
        } else if (position == SHOW_FEED) {
            fragment = new MenuFragment();
            bundle.putInt(FRAME_TYPE_KEY, SHOW_FEED);
        } else if (position == SHOW_FEED_PREFERENCES) {
            fragment = new FeedSubscriptionFragment();
        }

        fragment.setArguments(bundle);

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.page_fragment, fragment)
                    .commit();
            mDrawerFragment.mDrawerLayout.closeDrawers();
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (mFragmentPosition == SHOW_DEVELOPERS) {
            displayFragment(SHOW_ABOUT);
        } else if (mFragmentPosition != SHOW_HOME) {
            displayFragment(SHOW_HOME);
        } else {
            finish();
        }
    }

    public void startupFunctions() {
        if (!(AuthFunctions.isUserLoggedIn(mContext) || AuthFunctions.isUserOffline(mContext))) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (SharedPreferenceManager.load(mContext, SharedPreferenceManager.Tags.REGISTRATION_ID).contentEquals(SharedPreferenceManager.Tags.EMPTY) ||
                !SharedPreferenceManager.getBoolean(mContext, SharedPreferenceManager.Tags.GCM_REGISTERED, false)) {

        }
    }

    public void firstTimeSetup() {
        if (!SharedPreferenceManager.load(
                mContext,
                SharedPreferenceManager.Tags.FIRST_TIME).contentEquals(getString(R.string.app_version))) {
            // This is the first time setup
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (mDrawerFragment.mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerFragment.mDrawerLayout.closeDrawers();
            } else {
                mDrawerFragment.mDrawerLayout.openDrawer(Gravity.LEFT);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
