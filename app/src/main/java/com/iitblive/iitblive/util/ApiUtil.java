package com.iitblive.iitblive.util;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.fragment.HomeFragment;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.items.GenericItem;
import com.iitblive.iitblive.items.InformationItem;
import com.iitblive.iitblive.lvadapter.LVAdapterGeneric;
import com.iitblive.iitblive.lvadapter.LVAdapterInformation;
import com.iitblive.iitblive.lvadapter.LVAdapterMain;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijoy on 6/21/2015.
 */
public class ApiUtil {

    public static void makeApiCall(String link,
                                   final Context context,
                                   final int dataType,
                                   final View adapterView,
                                   final Integer iconResource,
                                   final String storeFile,
                                   final boolean fileExists) {
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
                            writeFile = onGetDataResult(response, dataType, context,
                                    adapterView);
                        } else if (dataType == Constants.DATA_TYPE_INFORMATION) {
                            writeFile = onGetInformationResult(response,
                                    context, iconResource, adapterView, fileExists);
                        }

                        if (writeFile && storeFile != null) {
                            Functions.offlineDataWriter(context, storeFile, response);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        if (!fileExists) {
                            onCreateEmptyList(context, adapterView);
                        }
                    }
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static void makeApiCallForHome(String link,
                                          final Context context,
                                          final int dataType,
                                          final String storeFile,
                                          final HomeFragment homeFragment,
                                          final HomeFragment.NowCardMetaContent metaContent) {
        StringRequest jsonRequest = new StringRequest
                (Request.Method.GET, link, new Response
                        .Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<ApiItem> eventItems = new ArrayList<>();

                        if (dataType == Constants.DATA_TYPE_NEWS ||
                                dataType == Constants.DATA_TYPE_EVENT ||
                                dataType == Constants.DATA_TYPE_NOTICE) {
                            eventItems = getEventListFromJson(context, response, dataType);
                        }

                        if (storeFile != null) {
                            Functions.offlineDataWriter(context, storeFile, response);
                        }

                        homeFragment.addCard(metaContent, eventItems);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    public static List<ApiItem> getEventListFromJson(Context context, String data, int
            dataType) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
            List<ApiItem> lst =
                    Functions.getEventItemList(context, jsonArray, dataType);
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
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);
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

    public static boolean onGetDataResult(String data, Integer dataType, Context context, View
            adapterView) {
        try {
            List<ApiItem> lst = getEventListFromJson(context, data, dataType);
            if (lst.isEmpty() || adapterView == null) {
                return onCreateEmptyGrid(context, adapterView);
            }
            LVAdapterMain lvAdapterMain = new LVAdapterMain(context, lst);
            ((GridView) adapterView).setAdapter(lvAdapterMain);
            return true;
        } catch (Exception e) {
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
