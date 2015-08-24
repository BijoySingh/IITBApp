package com.gymkhana.iitbapp.service;

/**
 * Created by bijoy on 8/24/15.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.LocalData;

public class GCMRegisterIntentService extends IntentService {

    private static final String TAG = "GCM_UTILITY";

    public GCMRegisterIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "GCM Registration Service Started");
        String token = null;

        int backoff = 1, factor = 2, rounds = 10;
        for (int round = 0; round < rounds; round++) {
            try {
                InstanceID instanceID = InstanceID.getInstance(this);
                token = instanceID.getToken(Constants.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            } catch (Exception e) {
                Log.e(TAG, "Failed to complete token refresh", e);
            }

            if (!(token == null || token.isEmpty())) {
                break;
            }

            try {
                wait(backoff);
                backoff *= factor;
            } catch (Exception e) {
                ;
            }
        }

        if (token != null && !token.isEmpty()) {
            Log.i(TAG, "GCM Registration Token: " + token);
            LocalData.save(
                    getBaseContext(),
                    LocalData.Tags.REGISTRATION_ID,
                    token);
            sendRegistrationToServer(token);
        } else {
            LocalData.save(getBaseContext(),
                    LocalData.Tags.REGISTRATION_ID, LocalData.Tags.EMPTY);
        }
    }

    /**
     * Persist registration to third-party servers.
     * <p/>
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        GcmUtility.storeRegistrationIdOnServer(getBaseContext(), token);
    }

}

