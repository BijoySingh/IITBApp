package com.iitblive.iitblive.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitblive.iitblive.MainActivity;
import com.iitblive.iitblive.activity.LoginActivity;
import com.iitblive.iitblive.service.GcmUtility;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bijoy on 6/27/2015.
 */
public class LoginFunctions {
    public static void userLoggedIn(Context context) {
        Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_TRUE);
    }

    public static void userOffline(Context context) {
        Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_OFFLINE);
    }

    public static boolean isUserLoggedIn(Context context) {
        return Functions.loadSharedPreference(
                context,
                Constants.SP_LOGGED_IN).contentEquals(Constants.SP_TRUE);
    }

    public static boolean isUserOffline(Context context) {
        return Functions.loadSharedPreference(
                context,
                Constants.SP_LOGGED_IN).contentEquals(Constants.SP_OFFLINE);
    }

    public static void logoutUser(Context context) {
        Functions.saveSharedPreference(context, Constants.SP_NAME, Constants.SP_EMPTY);
        Functions.saveSharedPreference(context, Constants.SP_LOGGED_IN, Constants.SP_OFFLINE);
    }

    public static void sendLoginQuery(final Context context, String username, String password) {
        final Map<String, String> params = new HashMap<>();
        params.put(Constants.Ldap.REQUEST_USERNAME, username);
        params.put(Constants.Ldap.REQUEST_PASSWORD, password);
        JSONObject jsonParams = new JSONObject(params);

        StringRequest jsonRequest = new StringRequest
                (Request.Method.POST, Constants.Ldap.JSON_URL, new Response
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
                                String ldap = response.getString(Constants
                                        .Ldap.RESPONSE_LDAP);
                                String name = response.getString(Constants
                                        .Ldap.RESPONSE_NAME);
                                String email = response.getString(Constants
                                        .Ldap.RESPONSE_EMAIL);
                                loginUtil(context, ldap, name);
                                GcmUtility.registerInBackground(context, Functions.loadSharedPreference
                                        (context, email));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoginActivity.enableLogin();
                        error.printStackTrace();
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

    public static void loginUtil(Context context, String ldap, String name) {
        Functions.saveSharedPreference(context, Constants.SP_USERNAME, ldap);
        Functions.saveSharedPreference(context, Constants.SP_NAME, name);
        LoginFunctions.userLoggedIn(context);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
