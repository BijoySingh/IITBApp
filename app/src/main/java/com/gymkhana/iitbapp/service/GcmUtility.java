package com.gymkhana.iitbapp.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.LocalData;
import com.gymkhana.iitbapp.util.ServerUrls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 7/14/15.
 */
public class GcmUtility {

    private static final String GCM_LOG_KEY = "GCM_UTILITY";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_REG_ID = "reg_id";
    private static final String PARAM_DEV_ID = "dev_id";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static void registerInBackground(final Context context) {
        Log.d(GCM_LOG_KEY, "REGISTER IN BACKGROUND");
        if (checkPlayServices(context)) {
            Intent intent = new Intent(context, GCMRegisterIntentService.class);
            context.startService(intent);
        }
    }

    private static boolean checkPlayServices(Context context) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(GCM_LOG_KEY, "This device is not supported.");
            }
            return false;
        }
        Log.i(GCM_LOG_KEY, "This device is supported.");
        return true;
    }

    private static Map<String, String> getParameters(Context context, final String registrationId) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_NAME, LocalData.load(context, LocalData.Tags.NAME));
        params.put(PARAM_REG_ID, registrationId);
        params.put(PARAM_DEV_ID, Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Log.d(GCM_LOG_KEY, params.toString());
        return params;
    }

    public static void storeRegistrationIdOnServer(final Context context, final String registrationId) {
        JSONObject params = new JSONObject(getParameters(context, registrationId));
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUrls.getInstance().GCM_REGISTER, params, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(GCM_LOG_KEY, response.toString());
                        try {
                            if (!response.has("error_message")) {
                                LocalData.save(
                                        context,
                                        LocalData.Tags.GCM_REGISTERED,
                                        LocalData.Tags.TRUE);
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Functions.makeToast(context, context.getString(R.string.toast_register));
                        Log.d(GCM_LOG_KEY, Functions.showVolleyError(error));
                        error.printStackTrace();
                    }

                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }


    public static void unregistrationIdOnServer(final Context context) {
        // This is to ensure that the user is attempted to register
        // even if he couldnt de-register this time
        LocalData.save(
                context,
                LocalData.Tags.GCM_REGISTERED,
                LocalData.Tags.FALSE);

        Map<String, String> params = new HashMap<>();
        params.put(PARAM_DEV_ID, Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        JSONObject jsonParams = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUrls.getInstance().GCM_UNREGISTER, jsonParams, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Log.d(GCM_LOG_KEY, Functions.showVolleyError(error));
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
