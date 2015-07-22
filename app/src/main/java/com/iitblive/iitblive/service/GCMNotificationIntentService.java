package com.iitblive.iitblive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.iitblive.iitblive.R;

/**
 * Created by bijoy on 7/14/15.
 */
public class GCMNotificationIntentService extends GcmListenerService {

    private static final String GCM_LOG_KEY = "GCM_IITBAPP";

    public GCMNotificationIntentService() {

    }

    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification("Received: " + data.toString());
    }

    @Override
    public void onDeletedMessages() {
        sendNotification("Deleted messages on server");
    }

    @Override
    public void onMessageSent(String msgId) {
        sendNotification("Upstream message sent. Id=" + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        sendNotification("Upstream message send error. Id=" + msgId + ", error" + error);
    }

    private void sendNotification(String msg) {
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.like_icon)
                .setCategory(Notification.CATEGORY_SOCIAL)
                .setContentTitle("New GCM Recieved")
                .setContentText(msg);

        Log.d(GCM_LOG_KEY, msg);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService
                (NOTIFICATION_SERVICE);
        mNotificationManager.notify(12, mNotificationBuilder.build());

    }
}