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
import com.gymkhana.iitbapp.util.SharedPreferenceManager;

public class GCMRegisterIntentService extends IntentService {

    private static final String TAG = "GCM_UTILITY";

    public GCMRegisterIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "GCM Registration Service Started");
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(Constants.GCM_SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

            Log.i(TAG, "GCM Registration Token: " + token);
            SharedPreferenceManager.save(
                    getBaseContext(),
                    SharedPreferenceManager.Tags.REGISTRATION_ID,
                    token);

            sendRegistrationToServer(token);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            SharedPreferenceManager.save(getBaseContext(),
                    SharedPreferenceManager.Tags.REGISTRATION_ID, SharedPreferenceManager.Tags.EMPTY);
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

