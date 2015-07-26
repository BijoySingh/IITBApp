package com.gymkhana.iitbapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.activity.IitbTimetableActivity;
import com.gymkhana.iitbapp.activity.InformationActivity;
import com.gymkhana.iitbapp.activity.LoginActivity;
import com.gymkhana.iitbapp.items.GenericItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijoy on 5/29/2015.
 */
public class ListContent {

    public static List<GenericItem> mSettingsList = null;
    public static List<GenericItem> mTimetableList = null;
    public static List<GenericItem> mInformationList = null;
    public static List<GenericItem> mDeveloperList = null;

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
        mSettingsList.add(new GenericItem(
                R.drawable.ic_launcher,
                context.getString(R.string.settings_about_iitbapp_title),
                context.getString(R.string.settings_about_iitbapp_description)));
        mSettingsList.add(new GenericItem(
                R.drawable.settings_about_us,
                context.getString(R.string.settings_about_us_title),
                context.getString(R.string.settings_about_us_description)));
        mSettingsList.add(new GenericItem(
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
        mTimetableList.add(new GenericItem(
                R.drawable.timetable_weekly,
                context.getString(R.string.timetable_weekly_title),
                context.getString(R.string.timetable_weekly_department)));
        mTimetableList.add(new GenericItem(
                R.drawable.timetable_academic_calendar,
                context.getString(R.string.timetable_acad_cal_title),
                context.getString(R.string.timetable_acad_cal_department)));
        mTimetableList.add(new GenericItem(
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
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ServerUrls.getInstance().ACADEMIC_CAL));
                        break;

                    case 2:
                        Functions.deleteDatabaseDialog(context);
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
        if (!AuthFunctions.isUserLoggedIn(context)) {
            GenericItem loginButton = new GenericItem(
                    R.drawable.urgent_icon,
                    context.getString(R.string.urgent_login_title),
                    context.getString(R.string.urgent_login_description));
            loginButton.tag = GenericItem.IS_URGENT;
            mInformationList.add(loginButton);
        }

        mInformationList.add(new GenericItem(
                R.drawable.information_department,
                context.getString(R.string.information_department_title),
                context.getString(R.string.information_department_description)));
        mInformationList.add(new GenericItem(
                R.drawable.information_groups,
                context.getString(R.string.information_groups_title),
                context.getString(R.string.information_groups_description)));
        mInformationList.add(new GenericItem(
                R.drawable.information_contacts,
                context.getString(R.string.information_contacts_title),
                context.getString(R.string.information_contacts_description)));
        mInformationList.add(new GenericItem(
                R.drawable.information_emergency,
                context.getString(R.string.information_emergency_title),
                context.getString(R.string.information_emergency_description)));
        mInformationList.add(new GenericItem(
                R.drawable.information_maps,
                context.getString(R.string.information_maps_title),
                context.getString(R.string.information_maps_description)));

        mInformationOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                if (!AuthFunctions.isUserLoggedIn(context)) {
                    position = position - 1;
                }
                switch (position) {
                    case -1:
                        intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();
                        return;
                    case 0:
                        intent = new Intent(context, InformationActivity.class);
                        InformationActivity.mTitle = context.getString(R.string.information_department_title);
                        InformationActivity.mLink = ServerUrls.getInstance().DEPARTMENTS;
                        InformationActivity.mIconResource = R.drawable.information_department;
                        InformationActivity.mFileName = Constants.Filenames.INFO_DEPARTMENT;
                        break;
                    case 1:
                        intent = new Intent(context, InformationActivity.class);
                        InformationActivity.mTitle = context.getString(R.string.information_groups_title);
                        InformationActivity.mLink = ServerUrls.getInstance().CLUBS;
                        InformationActivity.mIconResource = R.drawable.information_groups;
                        InformationActivity.mFileName = Constants.Filenames.INFO_GROUPS;
                        break;
                    case 2:
                        intent = new Intent(context, InformationActivity.class);
                        InformationActivity.mTitle = context.getString(R.string.information_contacts_title);
                        InformationActivity.mLink = ServerUrls.getInstance().CONTACTS;
                        InformationActivity.mIconResource = R.drawable.information_contacts;
                        InformationActivity.mFileName = Constants.Filenames.INFO_CONTACTS;
                        break;
                    case 3:
                        intent = new Intent(context, InformationActivity.class);
                        InformationActivity.mTitle = context.getString(R.string.information_emergency_title);
                        InformationActivity.mLink = ServerUrls.getInstance().EMERGENCY;
                        InformationActivity.mIconResource = R.drawable.information_emergency;
                        InformationActivity.mFileName = Constants.Filenames.INFO_EMERGENCY;
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

        mDeveloperList.add(new GenericItem(
                R.drawable.developer_bijoy,
                context.getString(R.string.developer_bijoy),
                context.getString(R.string.developer_cse_iitb_4)));
        mDeveloperList.add(new GenericItem(
                R.drawable.developer_dheerendra,
                context.getString(R.string.developer_dheerendra),
                context.getString(R.string.developer_cse_iitb_4)));
        mDeveloperList.add(new GenericItem(
                R.drawable.developer_aman,
                context.getString(R.string.developer_aman),
                context.getString(R.string.developer_cse_iitb_4)));
        mDeveloperList.add(new GenericItem(
                R.drawable.developer_ranveer,
                context.getString(R.string.developer_ranveer),
                context.getString(R.string.developer_cse_iitb_4)));
    }
}
