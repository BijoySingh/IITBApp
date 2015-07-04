package com.iitblive.iitblive.util;

import android.content.Context;

import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.items.InformationListViewItem;
import com.iitblive.iitblive.items.TimestampItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Bijoy on 5/25/2015.
 */
public class ListViewItemCreator {
    public static EventListViewItem createEventItem(Context context, JSONObject json) throws Exception {
        EventListViewItem e = new EventListViewItem();
        e.type = json.getString(Constants.JSON_KEY_TYPE);
        e.title = json.getString(Constants.JSON_KEY_TITLE);
        e.description = json.getString(Constants.JSON_KEY_DESCRIPTION);
        e.timestamp = json.getString(Constants.JSON_KEY_TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.event_timestamp = json.getString(Constants.JSON_KEY_EVENT_TIMESTAMP);
        e.event_time = new TimestampItem(context, e.event_timestamp);
        e.category = json.getString(Constants.JSON_KEY_CATEGORY);
        e.event_location = json.getString(Constants.JSON_KEY_LOCATION);
        e.id = json.getInt(Constants.JSON_KEY_ID);
        e.likes = json.getInt(Constants.JSON_KEY_LIKES);
        e.views = json.getInt(Constants.JSON_KEY_VIEWS);
        JSONArray image_links = json.getJSONArray(Constants.JSON_KEY_IMAGE_LIST);
        e.image_links = new ArrayList<>();
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }
        JSONObject source_json = json.getJSONObject(Constants.JSON_KEY_SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JSON_KEY_SOURCE_NAME);
        e.source_designation = source_json.getString(Constants.JSON_KEY_SOURCE_DESIGNATION);
        return e;
    }

    public static EventListViewItem createNewsItem(Context context, JSONObject json) throws Exception {
        EventListViewItem e = new EventListViewItem();
        e.type = json.getString(Constants.JSON_KEY_TYPE);
        e.title = json.getString(Constants.JSON_KEY_TITLE);
        e.description = json.getString(Constants.JSON_KEY_DESCRIPTION);
        e.timestamp = json.getString(Constants.JSON_KEY_TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.category = json.getString(Constants.JSON_KEY_CATEGORY);
        e.id = json.getInt(Constants.JSON_KEY_ID);
        e.likes = json.getInt(Constants.JSON_KEY_LIKES);
        e.views = json.getInt(Constants.JSON_KEY_VIEWS);
        JSONArray image_links = json.getJSONArray(Constants.JSON_KEY_IMAGE_LIST);
        e.image_links = new ArrayList<>();
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }
        JSONObject source_json = json.getJSONObject(Constants.JSON_KEY_SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JSON_KEY_SOURCE_NAME);
        e.source_designation = source_json.getString(Constants.JSON_KEY_SOURCE_DESIGNATION);
        return e;
    }

    public static EventListViewItem createNoticeItem(Context context, JSONObject json) {
        return null;
    }

    public static EventListViewItem createAnyItem(Context context, JSONObject json) throws Exception {
        String type = json.getString(Constants.JSON_KEY_TYPE);
        if (type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return createEventItem(context, json);
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return createNewsItem(context, json);
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            return createNoticeItem(context, json);
        } else {
            Exception exception = new Exception("Invalid Data Type");
            throw exception;
        }
    }

    public static InformationListViewItem createInformationItem(Context context, JSONObject json, int icon_resource) throws Exception {
        InformationListViewItem i = new InformationListViewItem();
        i.title = json.getString(Constants.JSON_KEY_TITLE);
        i.description = json.getString(Constants.JSON_KEY_DESCRIPTION);
        i.facebook = json.getString(Constants.JSON_KEY_FACEBOOK);
        i.phone = json.getString(Constants.JSON_KEY_PHONE);
        i.website = json.getString(Constants.JSON_KEY_LINK);
        i.email = json.getString(Constants.JSON_KEY_EMAIL);
        i.img_resource = icon_resource;
        return i;
    }

}
