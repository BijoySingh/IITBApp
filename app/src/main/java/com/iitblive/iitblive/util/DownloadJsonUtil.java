package com.iitblive.iitblive.util;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.items.GenericListViewItem;
import com.iitblive.iitblive.items.InformationListViewItem;
import com.iitblive.iitblive.lvadapter.LVAdapterGeneric;
import com.iitblive.iitblive.lvadapter.LVAdapterInformation;
import com.iitblive.iitblive.lvadapter.LVAdapterMain;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bijoy on 6/21/2015.
 */
public class DownloadJsonUtil {
  public static void onGetInformationResult(String data,
                                            Context context,
                                            Integer iconResource,
                                            View adapterView) {
    try {
      JSONArray jsonArray = new JSONArray(data);
      List<InformationListViewItem> lst =
          Functions.getInformationItemList(context, jsonArray, iconResource);
      if (lst.isEmpty()) {
        onCreateEmptyList(context, adapterView);
        return;
      }
      LVAdapterInformation lvAdapterInformation = new LVAdapterInformation(context, lst);
      ((ListView) adapterView).setAdapter(lvAdapterInformation);
    } catch (Exception e) {
      onCreateEmptyList(context, adapterView);
    }
  }

  public static void onGetDataResult(String data, Integer dataType, Context context, View adapterView) {
    try {
      JSONArray jsonArray = new JSONArray(data);
      List<EventListViewItem> lst =
          Functions.getEventItemList(context, jsonArray, dataType);

      if (lst.isEmpty()) {
        onCreateEmptyList(context, adapterView);
        return;
      }
      LVAdapterMain lvAdapterMain = new LVAdapterMain(context, lst);
      ((GridView) adapterView).setAdapter(lvAdapterMain);
    } catch (Exception e) {
      onCreateEmptyList(context, adapterView);
    }
  }

  public static void onCreateEmptyList(Context context, View adapterView) {
    try {
      List<GenericListViewItem> emptyList = new ArrayList<>();

      GenericListViewItem emptyButton = new GenericListViewItem(
          R.drawable.urgent_no_data_icon,
          context.getString(R.string.urgent_data_title),
          context.getString(R.string.urgent_data_description));
      emptyButton.tag = GenericListViewItem.IS_URGENT;
      emptyList.add(emptyButton);

      LVAdapterGeneric lvAdapterGeneric = new LVAdapterGeneric(context, emptyList);
      ((GridView) adapterView).setAdapter(lvAdapterGeneric);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
