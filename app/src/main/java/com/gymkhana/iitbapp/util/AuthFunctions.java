package com.gymkhana.iitbapp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
        LocalData.save(context, LocalData.Tags.LOGGED_IN, LocalData.Tags.TRUE);
    }

    public static void userOffline(Context context) {
        LocalData.save(context, LocalData.Tags.LOGGED_IN, LocalData.Tags.OFFLINE);
    }

    public static boolean isUserLoggedIn(Context context) {
        return LocalData.load(
            context,
            LocalData.Tags.LOGGED_IN).contentEquals(LocalData.Tags.TRUE);
    }

    public static boolean isUserOffline(Context context) {
        return LocalData.load(
            context,
            LocalData.Tags.LOGGED_IN).contentEquals(LocalData.Tags.OFFLINE);
    }

    public static void logoutUser(Context context) {
        LocalData.save(context, LocalData.Tags.NAME, LocalData.Tags.EMPTY);
        LocalData.save(context, LocalData.Tags.LOGGED_IN, LocalData.Tags.OFFLINE);
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
        params.put(Constants.Ldap.REQUEST_USER, LocalData.load(context, LocalData.Tags.USER_ID));
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
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
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
        LocalData.save(context, LocalData.Tags.USERNAME, ldap);
        LocalData.save(context, LocalData.Tags.NAME, name);
        LocalData.save(context, LocalData.Tags.USER_ID, id);
        LocalData.save(context, LocalData.Tags.USER_TOKEN, token);
        LocalData.save(context, LocalData.Tags.PASSWORD, password);
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
