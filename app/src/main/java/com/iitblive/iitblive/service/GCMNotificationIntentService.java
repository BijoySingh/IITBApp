package com.iitblive.iitblive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.activity.ArticleActivity;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;

import org.json.JSONObject;

/**
 * Created by bijoy on 7/14/15.
 */
public class GCMNotificationIntentService extends GcmListenerService {

    private static final String GCM_LOG_KEY = "GCM_IITBAPP";

    public GCMNotificationIntentService() {

    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(data);
    }

    @Override
    public void onDeletedMessages() {
    }

    @Override
    public void onMessageSent(String msgId) {
    }

    @Override
    public void onSendError(String msgId, String error) {
    }

    private void sendNotification(Bundle data) {
        try {
            JSONObject json = new JSONObject(data.getString(Constants.Gcm.MESSAGE));

            String type = json.getString(Constants.Gcm.TYPE);

            if (type.contentEquals("")) {

            }

            JSONObject item = json.getJSONObject(Constants.Gcm.ITEM);
            String action = json.getString(Constants.Gcm.ACTION);

            ApiItem apiItem = Functions.getEventItem(getApplicationContext(), item, type);

            Intent resultIntent = new Intent(this, ArticleActivity.class);
            ArticleActivity.mArticle = apiItem;
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setColor(apiItem.getAccentColor())
                    .setCategory(Notification.CATEGORY_SOCIAL)
                    .setContentTitle(apiItem.title)
                    .setContentText(apiItem.description)
                    .setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager) getSystemService
                    (NOTIFICATION_SERVICE);
            mNotificationManager.notify(apiItem.id, mNotificationBuilder.build());

        } catch (Exception e) {
            Log.e(GCM_LOG_KEY, "JSON or Bundle error", e);
            e.printStackTrace();
        }

        Log.d(GCM_LOG_KEY, data.toString());

    }
}