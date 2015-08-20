package com.gymkhana.iitbapp.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.database.TimetableDBHandler;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.FeedSubscriptionItem;
import com.gymkhana.iitbapp.items.GenericItem;
import com.gymkhana.iitbapp.items.InformationItem;
import com.gymkhana.iitbapp.lvadapter.LVAdapterGeneric;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Bijoy on 3/6/2015.
 * This file is the core function file of the entire application
 * All the functions here are static functions and form the basis of the app
 */
public class Functions {

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

    public static String correctUTFEncoding(String source) {
        try {
            return new String(source.getBytes("ISO-8859-1"), "UTF-8");
        } catch (Exception e) {
            return source;
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
    public static void setActionBarWithColor(ActionBarActivity activity,
                                             int actionbarColor,
                                             int navigationColor) {
        ActionBar ab = activity.getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(actionbarColor));
        ab.setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(navigationColor);
            //window.setNavigationBarColor(navigationColor);
        }
    }

    //Setup the actionbar of the activity
    public static void setActionBar(ActionBarActivity activity,
                                    int action_bar_color_resource,
                                    int navigation_bar_color_resource) {
        setActionBarWithColor(activity,
                activity.getResources().getColor(action_bar_color_resource),
                activity.getResources().getColor(navigation_bar_color_resource));
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

    public static ApiItem getEventItem(
            Context context,
            JSONObject json,
            String data_type
    ) throws Exception {
        if (data_type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return ListItemCreator.createEventItem(context, json);
        } else if (data_type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return ListItemCreator.createNewsItem(context, json);
        } else if (data_type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            return ListItemCreator.createNoticeItem(context, json);
        } else if (data_type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
            return ListItemCreator.createFeedItem(context, json);
        }
        return null;
    }

    public static ApiItem getEventItem(
            Context context,
            JSONObject json,
            Integer data_type
    ) throws Exception {
        if (data_type == Constants.DATA_TYPE_EVENT) {
            return ListItemCreator.createEventItem(context, json);
        } else if (data_type == Constants.DATA_TYPE_NEWS) {
            return ListItemCreator.createNewsItem(context, json);
        } else if (data_type == Constants.DATA_TYPE_NOTICE) {
            return ListItemCreator.createNoticeItem(context, json);
        } else if (data_type == Constants.DATA_TYPE_FEED) {
            return ListItemCreator.createFeedItem(context, json);
        }
        return null;
    }

    public static List<ApiItem> getEventItemList(
            Context context,
            JSONArray json,
            Integer data_type
    ) {
        List<ApiItem> listViewItems = new ArrayList<>();
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

    public static List<InformationItem> getInformationItemList(
            Context context,
            JSONArray json,
            int icon_resource) {
        List<InformationItem> listViewItems = new ArrayList<>();
        for (int i = 0; i < json.length(); i++) {
            try {
                listViewItems.add(
                        ListItemCreator.createInformationItem(context,
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
        fabLogo.setIcon(context.getResources().getDrawable(fabIcon), false);

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

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static void setupList(List<GenericItem> lst, ListView listView, Context context) {
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

    public static void deleteDatabaseDialog(final Context context) {
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(R.string.remove_timetable)
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setMessage(R.string.delete_database_message)
                .setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TimetableDBHandler db = new TimetableDBHandler(context);
                        db.deleteDatabase();
                        Functions.makeToast(context, R.string.toast_timetable_deleted);
                    }
                }).create().show();
    }

    public static List<FeedSubscriptionItem> getSubscriptions(final Context context) {
        String fileName = Constants.Filenames.INFO_FEED;
        if (fileName != null) {
            String json = Functions.offlineDataReader(context, fileName);
            if (json != null && !json.isEmpty()) {
                return ApiUtil.getSubscriptionListFromJson(context, json);
            }
        }
        return null;
    }

    public static Map<Integer, String> getSubscriptionMapping(final Context context) {
        Map<Integer, String> mapping = new HashMap<>();

        List<FeedSubscriptionItem> feeds = getSubscriptions(context);
        for (FeedSubscriptionItem feed : feeds) {
            mapping.put(feed.feed_id, feed.title);
        }

        return mapping;
    }


    public static boolean isNotifiableItem(Context context, ApiItem item) {
        if (item.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
            List<FeedSubscriptionItem> subscriptions = getSubscriptions(context);
            for (FeedSubscriptionItem feed : subscriptions) {
                if (feed.feed_id == item.feed_id) {
                    if (SharedPreferenceManager.load(context, feed.prefKey()).contentEquals(SharedPreferenceManager.Tags.FALSE)) {
                        return false;
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
