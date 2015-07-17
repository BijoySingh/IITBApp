package com.iitblive.iitblive.service;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by bijoy on 7/14/15.
 */
public class GCMNotificationIntentService extends GcmListenerService {

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

    }
}