package com.iitblive.iitblive.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.activity.GenericInformationListActivity;
import com.iitblive.iitblive.activity.IitbTimetableActivity;
import com.iitblive.iitblive.activity.LoginActivity;
import com.iitblive.iitblive.database.CourseDBHandler;
import com.iitblive.iitblive.items.GenericListViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijoy on 5/29/2015.
 */
public class FragmentListViewData {

  public static List<GenericListViewItem> mSettingsList = null;
  public static List<GenericListViewItem> mTimetableList = null;
  public static List<GenericListViewItem> mInformationList = null;
  public static List<GenericListViewItem> mDeveloperList = null;

  public static AdapterView.OnItemClickListener mSettingsOnItemClickListener = null;
  public static AdapterView.OnItemClickListener mTimetableOnItemClickListener = null;
  public static AdapterView.OnItemClickListener mInformationOnItemClickListener = null;

  public static void resetVariables() {
    mSettingsList = null;
    mTimetableList = null;
    mInformationList = null;
    mDeveloperList = null;

    mSettingsOnItemClickListener = null;
    mTimetableOnItemClickListener = null;
    mInformationOnItemClickListener = null;
  }

  public static void SettingsFragmentData(final Context context) {
    if (mSettingsList != null) {
      return;
    }

    mSettingsList = new ArrayList<>();
    mSettingsList.add(new GenericListViewItem(
        R.drawable.user_icon,
        context.getString(R.string.settings_about_iitbapp_title),
        context.getString(R.string.settings_about_iitbapp_description)));
    mSettingsList.add(new GenericListViewItem(
        R.drawable.settings_about_us,
        context.getString(R.string.settings_about_us_title),
        context.getString(R.string.settings_about_us_description)));
    mSettingsList.add(new GenericListViewItem(
        R.drawable.settings_feedback,
        context.getString(R.string.settings_rate_title),
        context.getString(R.string.settings_rate_description)));

    mSettingsOnItemClickListener = new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        switch (position) {
          case 0:
            return;
          case 1:
            ((MainActivity) context).displayFragment(MainActivity.SHOW_DEVELOPERS);
            return;
          case 2:
            intent = Functions.openAppStore(context.getPackageName());
            break;
        }
        context.startActivity(intent);
      }
    };
  }

  public static void TimetableFragmentData(final Context context) {
    if (mTimetableList != null) {
      return;
    }

    mTimetableList = new ArrayList<>();
    mTimetableList.add(new GenericListViewItem(
        R.drawable.timetable_weekly,
        context.getString(R.string.timetable_weekly_title),
        context.getString(R.string.timetable_weekly_department)));
    mTimetableList.add(new GenericListViewItem(
        R.drawable.timetable_academic_calendar,
        context.getString(R.string.timetable_acad_cal_title),
        context.getString(R.string.timetable_acad_cal_department)));
    mTimetableList.add(new GenericListViewItem(
        R.drawable.timetable_delete,
        context.getString(R.string.timetable_delete_title),
        context.getString(R.string.timetable_delete_department)));

    mTimetableOnItemClickListener = new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;

        switch (position) {
          case 0:
            intent = new Intent(context, IitbTimetableActivity.class);
            break;

          case 1:
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ACADEMIC_CAL_URL));
            break;

          case 2:
            CourseDBHandler db = new CourseDBHandler(context);
            db.deleteDatabase();
            Functions.makeToast(context, R.string.toast_timetable_deleted);
            return;
        }

        context.startActivity(intent);
      }
    };
  }

  public static void InformationFragmentData(final Context context) {
    if (mInformationList != null) {
      return;
    }

    mInformationList = new ArrayList<>();
    if (!LoginFunctions.isUserLoggedIn(context)) {
      GenericListViewItem loginButton = new GenericListViewItem(
          R.drawable.urgent_icon,
          context.getString(R.string.urgent_login_title),
          context.getString(R.string.urgent_login_description));
      loginButton.tag = GenericListViewItem.IS_URGENT;
      mInformationList.add(loginButton);
    }

    mInformationList.add(new GenericListViewItem(
        R.drawable.information_department,
        context.getString(R.string.information_department_title),
        context.getString(R.string.information_department_description)));
    mInformationList.add(new GenericListViewItem(
        R.drawable.information_groups,
        context.getString(R.string.information_groups_title),
        context.getString(R.string.information_groups_description)));
    mInformationList.add(new GenericListViewItem(
        R.drawable.information_contacts,
        context.getString(R.string.information_contacts_title),
        context.getString(R.string.information_contacts_description)));
    mInformationList.add(new GenericListViewItem(
        R.drawable.information_emergency,
        context.getString(R.string.information_emergency_title),
        context.getString(R.string.information_emergency_description)));
    mInformationList.add(new GenericListViewItem(
        R.drawable.information_maps,
        context.getString(R.string.information_maps_title),
        context.getString(R.string.information_maps_description)));

    mInformationOnItemClickListener = new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = null;
        if (!LoginFunctions.isUserLoggedIn(context)) {
          position = position - 1;
        }
        switch (position) {
          case -1:
            intent = new Intent(context, LoginActivity.class);
            break;
          case 0:
            intent = new Intent(context, GenericInformationListActivity.class);
            GenericInformationListActivity.mLink = "departments.txt";
            GenericInformationListActivity.mIconResource = R.drawable.information_department;
            GenericInformationListActivity.mFileName = Constants.FILENAME_INFO_DEPARTMENT;
            break;
          case 1:
            intent = new Intent(context, GenericInformationListActivity.class);
            GenericInformationListActivity.mLink = "clubs.txt";
            GenericInformationListActivity.mIconResource = R.drawable.information_groups;
            GenericInformationListActivity.mFileName = Constants.FILENAME_INFO_GROUPS;
            break;
          case 2:
            intent = new Intent(context, GenericInformationListActivity.class);
            GenericInformationListActivity.mLink = "contacts.txt";
            GenericInformationListActivity.mIconResource = R.drawable.information_contacts;
            GenericInformationListActivity.mFileName = Constants.FILENAME_INFO_CONTACTS;
            break;
          case 3:
            intent = new Intent(context, GenericInformationListActivity.class);
            GenericInformationListActivity.mLink = "emergency.txt";
            GenericInformationListActivity.mIconResource = R.drawable.information_emergency;
            GenericInformationListActivity.mFileName = Constants.FILENAME_INFO_EMERGENCY;
            break;
          case 4:
            intent = Functions.openApp(context, Constants.PACKAGE_NAME_INSTIMAP);
            break;
        }

        context.startActivity(intent);
      }
    };

  }

  public static void DeveloperFragmentData(Context context) {
    if (mDeveloperList != null) {
      return;
    }

    mDeveloperList = new ArrayList<>();

    mDeveloperList.add(new GenericListViewItem(
        R.drawable.developer_bijoy,
        context.getString(R.string.developer_bijoy),
        context.getString(R.string.developer_cse_iitb_4)));
    mDeveloperList.add(new GenericListViewItem(
        R.drawable.developer_dheerendra,
        context.getString(R.string.developer_dheerendra),
        context.getString(R.string.developer_cse_iitb_4)));
    mDeveloperList.add(new GenericListViewItem(
        R.drawable.developer_aman,
        context.getString(R.string.developer_aman),
        context.getString(R.string.developer_cse_iitb_4)));
    mDeveloperList.add(new GenericListViewItem(
        R.drawable.developer_ranveer,
        context.getString(R.string.developer_ranveer),
        context.getString(R.string.developer_cse_iitb_4)));
  }
}
