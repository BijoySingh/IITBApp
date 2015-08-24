package com.gymkhana.iitbapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.activity.LoginActivity;
import com.gymkhana.iitbapp.service.GcmUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bijoy on 6/27/2015.
 */
public class AuthFunctions {
    public static void userLoggedIn(Context context) {
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.LOGGED_IN, SharedPreferenceManager.Tags.TRUE);
    }

    public static void userOffline(Context context) {
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.LOGGED_IN, SharedPreferenceManager.Tags.OFFLINE);
    }

    public static boolean isUserLoggedIn(Context context) {
        return SharedPreferenceManager.load(
                context,
                SharedPreferenceManager.Tags.LOGGED_IN).contentEquals(SharedPreferenceManager.Tags.TRUE);
    }

    public static boolean isUserOffline(Context context) {
        return SharedPreferenceManager.load(
                context,
                SharedPreferenceManager.Tags.LOGGED_IN).contentEquals(SharedPreferenceManager.Tags.OFFLINE);
    }

    public static void logoutUser(Context context) {
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.NAME, SharedPreferenceManager.Tags.EMPTY);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.LOGGED_IN, SharedPreferenceManager.Tags.OFFLINE);
    }


    public static void sendLoginQuery(final Context context, final String username, final String password) {
        final Map<String, String> params = new HashMap<>();
        params.put(Constants.Ldap.REQUEST_USERNAME, username);
        params.put(Constants.Ldap.REQUEST_PASSWORD, password);
        JSONObject jsonParams = new JSONObject(params);

        StringRequest jsonRequest = new StringRequest
                (Request.Method.POST, ServerUrls.getInstance().AUTHENTICATE, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        LoginActivity.enableLogin();
                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.getBoolean(Constants.Ldap.RESPONSE_ERROR)) {
                                Functions.makeToast(
                                        context,
                                        response.getString(Constants.Ldap.RESPONSE_ERROR_MESSAGE));
                            } else {
                                String ldap = response.getString(Constants.Ldap.RESPONSE_LDAP);
                                String name = response.getString(Constants.Ldap.RESPONSE_NAME);
                                String email = response.getString(Constants.Ldap.RESPONSE_EMAIL);
                                String user_token = response.getString(Constants.Ldap.RESPONSE_USER_TOKEN);
                                String id = ((Integer) response.getInt(Constants.Ldap.RESPONSE_USER_ID)).toString();

                                try {
                                    GcmUtility.registerInBackground(context);
                                } catch (Exception e) {
                                    Log.e("LOGIN_FUNCTIONS", "GCM FAILED", e);
                                }
                                storeLoginInformation(context, ldap, name, id, user_token, password);
                                loginUtil(context);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Functions.makeToast(context, "Unknown Error Occured");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoginActivity.enableLogin();
                        error.printStackTrace();
                        Functions.makeToast(context, "Server Error");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);

    }


    public static void sendLogoutQuery(final Context context) {
        final Map<String, String> params = new HashMap<>();
        params.put(Constants.Ldap.REQUEST_USER, SharedPreferenceManager.load(context, SharedPreferenceManager.Tags.USER_ID));
        JSONObject jsonParams = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUrls.getInstance().LOGOUT, jsonParams, new Response
                        .Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject result) {
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
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", SharedPreferenceManager.load(context, SharedPreferenceManager.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);

    }

    public static void storeLoginInformation(Context context,
                                             String ldap,
                                             String name,
                                             String id,
                                             String token,
                                             String password) {
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.USERNAME, ldap);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.NAME, name);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.USER_ID, id);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.USER_TOKEN, token);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.PASSWORD, password);
    }

    public static void loginUtil(Context context) {
        AuthFunctions.userLoggedIn(context);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }

    public static void logout(Context context) {
        sendLogoutQuery(context);
        AuthFunctions.logoutUser(context);
        GcmUtility.unregistrationIdOnServer(context);
        Intent loginIntent = new Intent(context, LoginActivity.class);
        context.startActivity(loginIntent);
        ((Activity) context).finish();
    }
}
