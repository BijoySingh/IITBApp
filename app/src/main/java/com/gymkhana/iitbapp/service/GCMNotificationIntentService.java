package com.gymkhana.iitbapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.activity.ArticleActivity;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;

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
            Log.d(GCM_LOG_KEY, item.toString());
            String action = json.getString(Constants.Gcm.ACTION);

            ApiItem apiItem = Functions.getEventItem(getApplicationContext(), item, type);
            if (!Functions.isNotifiableItem(this, apiItem)) {
                Log.d(GCM_LOG_KEY, "Not a notifiable item");
                return;
            }

            if (apiItem.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
                apiItem.description = Html.fromHtml(apiItem.description).toString();
            }

            Intent resultIntent = new Intent(this, ArticleActivity.class);
            resultIntent.putExtra(ArticleActivity.INTENT_ARTICLE, apiItem);
            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );

            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
                    .setColor(apiItem.getAccentColor())
                    .setCategory(Notification.CATEGORY_SOCIAL)
                    .setContentTitle(apiItem.title)
                    .setContentText(apiItem.description)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(apiItem.description))
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true);

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