package com.iitblive.iitblive.service;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;

import org.json.JSONObject;

import java.io.IOException;
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

    public static GoogleCloudMessaging googleCloudMessaging;
    public static String registrationId;

    public static void registerInBackground(final Context context, final String emailID) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    if (googleCloudMessaging == null) {
                        googleCloudMessaging = GoogleCloudMessaging
                                .getInstance(context);
                    }
                    registrationId = googleCloudMessaging
                            .register(Constants.GCM_SENDER_ID);
                    return registrationId;
                } catch (IOException ex) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String msg) {
                if (registrationId != null && !TextUtils.isEmpty(registrationId)) {
                    storeRegistrationIdOnServer(context);
                    Functions.saveSharedPreference(context, Constants.SP_REGISTRATION_ID, registrationId);
                }
            }
        }.execute();
    }

    private static Map<String, String> getParameters(Context context) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_NAME, Functions.loadSharedPreference(context, Constants.SP_NAME));
        params.put(PARAM_REG_ID, registrationId);
        params.put(PARAM_DEV_ID, Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));

        Log.e(GCM_LOG_KEY, params.toString());
        return params;
    }

    private static void storeRegistrationIdOnServer(final Context context) {
        JSONObject params = new JSONObject(getParameters(context));
        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, Constants.Urls.GCM_REGISTER, params, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(GCM_LOG_KEY, Constants.Urls.GCM_REGISTER);
                        Log.e(GCM_LOG_KEY, response.toString());
                        try {
                            if (!response.has("error_message")) {
                                Functions.saveSharedPreference(
                                        context,
                                        Constants.SP_GCM_REGISTERED,
                                        Constants.SP_TRUE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }


    public static void unregistrationIdOnServer(final Context context) {
        Map<String, String> params = new HashMap<>();
        params.put(PARAM_DEV_ID, Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID));
        JSONObject jsonParams = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, Constants.Urls.GCM_UNREGISTER, jsonParams, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(GCM_LOG_KEY, Constants.Urls.GCM_UNREGISTER);
                        Log.e(GCM_LOG_KEY, response.toString());
                        try {
                            Functions.saveSharedPreference(
                                    context,
                                    Constants.SP_GCM_REGISTERED,
                                    Constants.SP_FALSE);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }
}
