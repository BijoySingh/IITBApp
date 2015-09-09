package com.gymkhana.iitbapp.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.fragment.HomeFragment;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.FeedSubscriptionItem;
import com.gymkhana.iitbapp.items.GenericItem;
import com.gymkhana.iitbapp.items.InformationItem;
import com.gymkhana.iitbapp.lvadapter.LVAdapterFeeds;
import com.gymkhana.iitbapp.lvadapter.LVAdapterGeneric;
import com.gymkhana.iitbapp.lvadapter.LVAdapterInformation;
import com.gymkhana.iitbapp.lvadapter.LVAdapterMain;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bijoy on 6/21/2015.
 */
public class ApiUtil {

    private static final Integer TIMEOUT = 5000;

    public static void makeApiCall(String link,
                                   final Context context,
                                   final int dataType,
                                   final View adapterView,
                                   final Integer iconResource,
                                   final String storeFile,
                                   final boolean fileExists) {
        Log.d("API_UTIL", storeFile + " => " + link);
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        boolean writeFile = false;
                        if (dataType == Constants.DATA_TYPE_NEWS ||
                                dataType == Constants.DATA_TYPE_EVENT ||
                                dataType == Constants.DATA_TYPE_NOTICE ||
                                dataType == Constants.DATA_TYPE_ANY) {
                            writeFile = onGetDataResult(response, dataType, context, adapterView);
                        } else if (dataType == Constants.DATA_TYPE_INFORMATION) {
                            writeFile = onGetInformationResult(response,
                                    context, iconResource, adapterView, fileExists);
                        } else if (dataType == Constants.DATA_TYPE_FEED_INFO) {
                            writeFile = onSubscriptionDataResult(response, context, adapterView);
                        }

                        if (writeFile && storeFile != null) {
                            Functions.offlineDataWriter(context, storeFile,
                                    Functions.correctUTFEncoding(response));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                            AuthFunctions.logout(context);
                        }
                        if (!fileExists) {
                            onCreateEmptyList(context, adapterView);
                        }
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

    public static void makeApiCallForHome(String link,
                                          final Context context,
                                          final int dataType,
                                          final String storeFile,
                                          final HomeFragment homeFragment,
                                          final HomeFragment.NowCardMetaContent metaContent) {
        Log.d("API_UTIL", storeFile + " => " + link);
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<ApiItem> eventItems = new ArrayList<>();

                        Log.d("API_UTIL", storeFile + " => " + response);

                        if (dataType == Constants.DATA_TYPE_NEWS ||
                                dataType == Constants.DATA_TYPE_EVENT ||
                                dataType == Constants.DATA_TYPE_NOTICE ||
                                dataType == Constants.DATA_TYPE_FEED) {
                            eventItems = getEventListFromJson(context, response, dataType);
                        }

                        if (storeFile != null) {
                            Functions.offlineDataWriter(context, storeFile,
                                    Functions.correctUTFEncoding(response));
                        }

                        homeFragment.addCard(metaContent, eventItems);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        NetworkResponse networkResponse = error.networkResponse;
                        if (networkResponse != null && networkResponse.statusCode == HttpStatus.SC_FORBIDDEN) {
                            AuthFunctions.logout(context);
                        }
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                ApiUtil.TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static List<ApiItem> getEventListFromJson(Context context, String data, int
            dataType) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.JsonKeys.RESULTS);
            List<ApiItem> lst = Functions.getEventItemList(context, jsonArray, dataType);
            return lst;
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }

    public static List<FeedSubscriptionItem> getSubscriptionListFromJson(Context context, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray json = jsonObject.getJSONArray(Constants.JsonKeys.RESULTS);
            List<FeedSubscriptionItem> lst = new ArrayList<>();

            for (int i = 0; i < json.length(); i++) {
                try {
                    lst.add(ListItemCreator.createFeedInformation(context, json.getJSONObject(i)));
                } catch (Exception e) {
                    Log.e("PARSE_JSON_IITBAPP", "Error", e);
                }
            }
            return lst;
        } catch (Exception e) {
        }
        return new ArrayList<>();
    }

    public static boolean onGetInformationResult(String data,
                                                 Context context,
                                                 Integer iconResource,
                                                 View adapterView,
                                                 boolean fileExists) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.JsonKeys.RESULTS);
            List<InformationItem> lst =
                    Functions.getInformationItemList(context, jsonArray, iconResource);
            if (lst.isEmpty() || adapterView == null) {
                return onCreateEmptyList(context, adapterView);
            }
            LVAdapterInformation lvAdapterInformation = new LVAdapterInformation(context, lst);
            ((ListView) adapterView).setAdapter(lvAdapterInformation);
            return true;
        } catch (Exception e) {
            if (!fileExists)
                onCreateEmptyList(context, adapterView);
        }
        return false;
    }

    public static boolean onSubscriptionDataResult(String data, Context context, View
            adapterView) {
        try {
            List<FeedSubscriptionItem> lst = getSubscriptionListFromJson(context, data);
            if (lst.isEmpty() || adapterView == null) {
                return onCreateEmptyList(context, adapterView);
            }
            LVAdapterFeeds lvAdapterMain = new LVAdapterFeeds(context, lst);
            ((ListView) adapterView).setAdapter(lvAdapterMain);
            return true;
        } catch (Exception e) {
            return onCreateEmptyGrid(context, adapterView);
        }
    }

    public static boolean onGetDataResult(String data, Integer dataType, Context context, View adapterView) {
        try {
            List<ApiItem> lst = getEventListFromJson(context, data, dataType);
            if (lst.isEmpty() || adapterView == null) {
                return onCreateEmptyGrid(context, adapterView);
            }
            LVAdapterMain lvAdapterMain = new LVAdapterMain(context, lst);
            ((GridView) adapterView).setAdapter(lvAdapterMain);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return onCreateEmptyGrid(context, adapterView);
        }
    }

    public static boolean onCreateEmptyView(Context context, View adapterView, boolean isGrid) {
        if (adapterView == null) {
            return true;
        }

        try {
            List<GenericItem> emptyList = new ArrayList<>();
            GenericItem emptyButton = new GenericItem(
                    R.drawable.urgent_no_data_icon,
                    context.getString(R.string.urgent_data_title),
                    context.getString(R.string.urgent_data_description));
            emptyButton.tag = GenericItem.IS_URGENT;
            emptyList.add(emptyButton);

            LVAdapterGeneric lvAdapterGeneric = new LVAdapterGeneric(context, emptyList);
            if (isGrid)
                ((GridView) adapterView).setAdapter(lvAdapterGeneric);
            else
                ((ListView) adapterView).setAdapter(lvAdapterGeneric);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean onCreateEmptyGrid(Context context, View adapterView) {
        return onCreateEmptyView(context, adapterView, true);
    }

    public static boolean onCreateEmptyList(Context context, View adapterView) {
        return onCreateEmptyView(context, adapterView, false);
    }
}
