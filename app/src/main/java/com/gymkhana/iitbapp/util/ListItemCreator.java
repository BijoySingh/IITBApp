package com.gymkhana.iitbapp.util;

import android.content.Context;

import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.FeedSubscriptionItem;
import com.gymkhana.iitbapp.items.InformationItem;
import com.gymkhana.iitbapp.items.TimestampItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Bijoy on 5/25/2015.
 */
public class ListItemCreator {
    public static ApiItem createEventItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_EVENT;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_DESCRIPTION));
        e.timestamp = json.getString(Constants.JSON_KEY_TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.event_timestamp = json.getString(Constants.JSON_KEY_EVENT_TIMESTAMP);
        e.event_time = new TimestampItem(context, e.event_timestamp);
        e.category = json.getString(Constants.JSON_KEY_CATEGORY);
        e.event_location = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_LOCATION));
        e.id = json.getInt(Constants.JSON_KEY_ID);
        e.likes = json.getInt(Constants.JSON_KEY_LIKES);
        e.views = json.getInt(Constants.JSON_KEY_VIEWS);
        e.image_links = new ArrayList<>();
        JSONArray image_links = json.getJSONArray(Constants.JSON_KEY_IMAGE_LIST);
        e.image_links = new ArrayList<>();
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }
        JSONObject source_json = json.getJSONObject(Constants.JSON_KEY_SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JSON_KEY_SOURCE_NAME);
        e.source_email = source_json.getString(Constants.JSON_KEY_SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JSON_KEY_SOURCE_DESIGNATION);

        e.liked = json.getBoolean(Constants.JSON_KEY_LIKED);
        e.viewed = json.getBoolean(Constants.JSON_KEY_VIEWED);
        return e;
    }

    public static ApiItem createNewsItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_NEWS;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_DESCRIPTION));
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
        e.source_email = source_json.getString(Constants.JSON_KEY_SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JSON_KEY_SOURCE_DESIGNATION);

        e.liked = json.getBoolean(Constants.JSON_KEY_LIKED);
        e.viewed = json.getBoolean(Constants.JSON_KEY_VIEWED);
        return e;
    }

    public static ApiItem createNoticeItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_NOTICE;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_DESCRIPTION));
        e.timestamp = json.getString(Constants.JSON_KEY_TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.id = json.getInt(Constants.JSON_KEY_ID);
        e.notice_timestamp = json.getString(Constants.JSON_KEY_EXPIRATION);
        e.expiration_time = new TimestampItem(context, e.notice_timestamp);
        e.notice_priority = json.getString(Constants.JSON_KEY_PRIORITY);
        e.category = CategoryImages.Category.NOTICE;
        e.image_links = new ArrayList<>();

        JSONObject source_json = json.getJSONObject(Constants.JSON_KEY_SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JSON_KEY_SOURCE_NAME);
        e.source_email = source_json.getString(Constants.JSON_KEY_SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JSON_KEY_SOURCE_DESIGNATION);
        return e;
    }

    public static ApiItem createFeedItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_FEED;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_CONTENT));
        e.timestamp = json.getString(Constants.JSON_KEY_UPDATED);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.id = json.getInt(Constants.JSON_KEY_ID);
        e.feed_id = json.getInt(Constants.JSON_KEY_FEED_ID);
        e.category = CategoryImages.Category.FEED;

        e.image_links = new ArrayList<>();
        JSONArray image_links = json.getJSONArray(Constants.JSON_KEY_IMAGE_LIST);
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }

        e.source_name = json.getString(Constants.JSON_KEY_AUTHOR);
        e.source_email = json.getString(Constants.JSON_KEY_LINK);
        e.source_designation = json.getString(Constants.JSON_KEY_LINK);

        e.likes = json.getInt(Constants.JSON_KEY_LIKES);
        e.views = json.getInt(Constants.JSON_KEY_VIEWS);
        e.liked = json.getBoolean(Constants.JSON_KEY_LIKED);
        e.viewed = json.getBoolean(Constants.JSON_KEY_VIEWED);
        return e;
    }

    public static FeedSubscriptionItem createFeedInformation(Context context, JSONObject json) throws Exception {
        FeedSubscriptionItem e = new FeedSubscriptionItem();
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JSON_KEY_LINK));
        e.feed_id = json.getInt(Constants.JSON_KEY_ID);
        e.url = json.getString(Constants.JSON_KEY_LINK);
        e.updated = json.getString(Constants.JSON_KEY_UPDATED);
        return e;
    }

    public static InformationItem createInformationItem(Context context, JSONObject json, int icon_resource) throws Exception {
        InformationItem i = new InformationItem();
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
