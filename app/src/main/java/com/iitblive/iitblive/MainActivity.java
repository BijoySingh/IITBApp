package com.iitblive.iitblive;

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

import com.iitblive.iitblive.activity.LoginActivity;
import com.iitblive.iitblive.fragment.DrawerFragment;
import com.iitblive.iitblive.fragment.GenericListFragment;
import com.iitblive.iitblive.fragment.HomeFragment;
import com.iitblive.iitblive.fragment.MenuFragment;
import com.iitblive.iitblive.items.GenericItem;
import com.iitblive.iitblive.service.GcmUtility;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.iitblive.iitblive.util.ListContent;
import com.iitblive.iitblive.util.LoginFunctions;

public class MainActivity extends ActionBarActivity implements DrawerFragment.OnFragmentInteractionListener {

    public static String FRAME_TYPE_KEY = "FRAME_TYPE";
    public static Integer SHOW_HOME = 0;
    public static Integer SHOW_EVENTS = 1;
    public static Integer SHOW_NEWS = 2;
    public static Integer SHOW_TIMETABLE = 3;
    public static Integer SHOW_ABOUT = 4;
    public static Integer SHOW_INFORMATION = 5;
    public static Integer SHOW_DEVELOPERS = 6;
    public static Integer SHOW_NOTICES = 7;

    private Context mContext;
    private DrawerFragment mDrawerFragment;
    private Integer mFragmentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        firstTimeSetup();
        ListContent.resetVariables();

        Functions.setActionBar(this);
        Functions.setActionBarTitle(this, getString(R.string.title_home));

        mDrawerFragment =
                (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_bar);
        mDrawerFragment.setup((DrawerLayout) findViewById(R.id.drawer_layout));
        ListView lv = mDrawerFragment.setupListView();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_TIMETABLE) {
                    displayFragment(SHOW_TIMETABLE);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_ABOUT) {
                    displayFragment(SHOW_ABOUT);
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_LOGIN) {
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
                    ((Activity) mContext).finish();
                } else if (glvi.tag == DrawerFragment.DRAWER_TAG_LOGOUT) {
                    LoginFunctions.logoutUser(mContext);
                    GcmUtility.unregistrationIdOnServer(mContext);
                    Intent loginIntent = new Intent(mContext, LoginActivity.class);
                    startActivity(loginIntent);
                    ((Activity) mContext).finish();
                }
            }
        });

        if (LoginFunctions.isUserLoggedIn(mContext)) {
            displayFragment(MainActivity.SHOW_HOME);
        } else {
            displayFragment(MainActivity.SHOW_INFORMATION);
        }
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
            GenericListFragment.mList = ListContent.mInformationList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mInformationOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_TIMETABLE) {
            ListContent.TimetableFragmentData(mContext);
            GenericListFragment.mList = ListContent.mTimetableList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mTimetableOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_ABOUT) {
            ListContent.SettingsFragmentData(mContext);
            GenericListFragment.mList = ListContent.mSettingsList;
            GenericListFragment.mOnItemClickListener =
                    ListContent.mSettingsOnItemClickListener;
            fragment = new GenericListFragment();
        } else if (position == SHOW_DEVELOPERS) {
            ListContent.DeveloperFragmentData(mContext);
            GenericListFragment.mList = ListContent.mDeveloperList;
            GenericListFragment.mOnItemClickListener = null;
            fragment = new GenericListFragment();
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
        super.onBackPressed();
        if (mFragmentPosition == SHOW_DEVELOPERS) {
            displayFragment(SHOW_ABOUT);
        } else if (mFragmentPosition != SHOW_NEWS) {
            displayFragment(SHOW_NEWS);
        } else {
            finish();
        }
    }

    public void firstTimeSetup() {
        if (!(LoginFunctions.isUserLoggedIn(mContext) || LoginFunctions.isUserOffline(mContext))) {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        if (!Functions.loadSharedPreference(
                mContext,
                Constants.SP_FIRST_TIME).contentEquals(getString(R.string.app_version))) {
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
