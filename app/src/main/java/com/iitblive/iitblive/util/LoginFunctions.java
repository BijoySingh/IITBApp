package com.iitblive.iitblive.util;

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
                                String first_name = response.getString(Constants.Ldap.RESPONSE_FIRST_NAME);
                                String last_name = response.getString(Constants.Ldap.RESPONSE_LAST_NAME);
                                String employee_number = response.getString(Constants.Ldap.RESPONSE_EMPLOYEE_NUMBER);

                                GcmUtility.registerInBackground(context, SharedPreferenceManager.load
                                        (context, email));
                                storeLoginInformation(context, ldap, name);
                                signupOnServer(context, employee_number, first_name, last_name, email);
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

    public static void signupOnServer(final Context context, String employeeNumber, final String
            first_name, String last_name, String email) {
        final Map<String, String> params = new HashMap<>();
        params.put(Constants.User.REQUEST_FIRST_NAME, first_name);
        params.put(Constants.User.REQUEST_LAST_NAME, last_name);
        params.put(Constants.User.REQUEST_EMAIL, email);
        params.put(Constants.User.REQUEST_EMPLOYEE_NUMBER, employeeNumber);
        JSONObject jsonParams = new JSONObject(params);

        Log.e("LOG", jsonParams.toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, ServerUrls.getInstance().USER, jsonParams, new
                        Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject result) {
                                LoginActivity.enableLogin();
                                try {
                                    Integer id = result.getInt(Constants.User.RESPONSE_ID);
                                    SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.USER_ID, id.toString());
                                    loginUtil(context);
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
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void storeLoginInformation(Context context,
                                             String ldap,
                                             String name) {
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.USERNAME, ldap);
        SharedPreferenceManager.save(context, SharedPreferenceManager.Tags.NAME, name);
    }

    public static void loginUtil(Context context) {
        LoginFunctions.userLoggedIn(context);
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity) context).finish();
    }
}
