package com.iitblive.iitblive.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.items.GenericListViewItem;
import com.iitblive.iitblive.items.InformationListViewItem;
import com.iitblive.iitblive.lvadapter.LVAdapterGeneric;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Bijoy on 3/6/2015.
 * This file is the core function file of the entire application
 * All the functions here are static functions and form the basis of the app
 */
public class Functions {

    //Load the data from the shared preferences
    public static String loadSharedPreference(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        String strValue = sp.getString(key, "");
        return strValue;
    }

    //Saves the data into the shared preferences
    public static void saveSharedPreference(Context context,
                                            String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(Constants.SHARED_PREFERENCES, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    //It store the filedata into the file given by the filename
    public static void offlineDataWriter(Context context, String filename, String filedata) {
        try {
            FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(filedata.getBytes());
            fos.close();
        } catch (Exception e) {
            ;
        }

    }

    //Reads the data stored in the file given by the filename
    public static String offlineDataReader(Context context, String filename) {

        try {
            FileInputStream fis = context.openFileInput(filename);
            StringBuilder builder = new StringBuilder();
            int ch;
            while ((ch = fis.read()) != -1) {
                builder.append((char) ch);
            }

            return builder.toString();
        } catch (Exception e) {
            ;
        }

        return "";
    }

    //Makes a toast(this is much cleaner than the code for toast)
    public static void makeToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    //Makes a toast(this is much cleaner than the code for toast) from resources
    public static void makeToast(Context context, Integer textId) {
        Toast.makeText(context, context.getString(textId), Toast.LENGTH_SHORT).show();
    }

    //Sets the actionbar title
    public static void setActionBarTitle(ActionBarActivity activity, String title) {
        ActionBar ab = activity.getSupportActionBar();
        ab.setTitle(title);
    }

    //Setup the actionbar of the activity
    public static void setActionBar(ActionBarActivity activity) {
        setActionBar(activity, R.color.actionbar_500, R.color.actionbar_700);
    }

    //Setup the actionbar of the activity
    public static void setActionBar(ActionBarActivity activity,
                                    int action_bar_color_resource,
                                    int navigation_bar_color_resource) {
        ActionBar ab = activity.getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(action_bar_color_resource)));
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(activity.getResources().getColor(navigation_bar_color_resource));
        }
    }

    //Hide the actionbar of the activity
    public static void hideActionBar(ActionBarActivity activity) {
        ActionBar ab = activity.getSupportActionBar();
        ab.hide();
    }

    //Gets a dialog which is transparent
    public static Dialog getTransparentDialog(Context context, Integer layout, Integer color) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);

        final Window d_window = dialog.getWindow();
        d_window.setBackgroundDrawable(new ColorDrawable(color));
        return dialog;
    }

    public static String cropString(String str, Integer length) {
        if (str.length() <= length) {
            return str;
        } else {
            str = str.substring(0, length - 3);
            str += "...";
            return str;
        }
    }

    public static EventListViewItem getEventItem(
            Context context,
            JSONObject json,
            Integer data_type
    ) throws Exception {
        if (data_type == Constants.DATA_TYPE_EVENT) {
            return ListViewItemCreator.createEventItem(context, json);
        } else if (data_type == Constants.DATA_TYPE_NEWS) {
            return ListViewItemCreator.createNewsItem(context, json);
        } else if (data_type == Constants.DATA_TYPE_NOTICE) {
            return ListViewItemCreator.createNoticeItem(context, json);
        }
        return null;
    }

    public static List<EventListViewItem> getEventItemList(
            Context context,
            JSONArray json,
            Integer data_type
    ) {
        List<EventListViewItem> listViewItems = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            try {
                listViewItems.add(
                        Functions.getEventItem(
                                context,
                                json.getJSONObject(i),
                                data_type));
            } catch (Exception e) {
                Log.e("PARSE_JSON_IITBAPP", "Error", e);
            }
        }
        return listViewItems;
    }

    public static List<InformationListViewItem> getInformationItemList(
            Context context,
            JSONArray json,
            int icon_resource) {
        List<InformationListViewItem> listViewItems = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            try {
                listViewItems.add(
                        ListViewItemCreator.createInformationItem(context,
                                json.getJSONObject(i), icon_resource));
            } catch (Exception e) {
                ;
            }
        }
        return listViewItems;
    }

    public static Dialog createMaterialDialog(
            Context context,
            String titleStr,
            String subtitleStr,
            String footerStr,
            String buttonText,
            int fabIcon,
            final Callable<Void> callable) {
        final Dialog dialog = getTransparentDialog(context, R.layout.material_dialog, Color.TRANSPARENT);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(titleStr);

        TextView subTitle = (TextView) dialog.findViewById(R.id.subtitle);
        subTitle.setText(subtitleStr);

        TextView footer = (TextView) dialog.findViewById(R.id.footer);
        footer.setText(footerStr);

        FloatingActionButton fabLogo = (FloatingActionButton) dialog.findViewById(R.id.fab_logo);
        fabLogo.setIcon(context.getDrawable(fabIcon), false);

        Button button = (Button) dialog.findViewById(R.id.button);
        button.setText(buttonText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    callable.call();
                } catch (Exception e) {
                }

                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static void setupList(List<GenericListViewItem> lst, ListView listView, Context context) {
        LVAdapterGeneric lvAdapterGeneric = new LVAdapterGeneric(context, lst);
        listView.setAdapter(lvAdapterGeneric);
    }

    public static void openWebsite(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }


    public static Intent openAppStore(String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=" + packageName));

        return intent;
    }

    public static Intent openApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            return intent;
        } else {
            return openAppStore(packageName);
        }
    }
}
