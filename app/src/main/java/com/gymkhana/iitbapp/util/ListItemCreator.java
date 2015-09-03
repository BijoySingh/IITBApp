package com.gymkhana.iitbapp.util;

import android.content.Context;
import android.util.Log;

import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.FeedCategoryItem;
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
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.DESCRIPTION));
        e.timestamp = json.getString(Constants.JsonKeys.TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.event_timestamp = json.getString(Constants.JsonKeys.EVENT_TIMESTAMP);
        e.event_time = new TimestampItem(context, e.event_timestamp);
        e.category = json.getString(Constants.JsonKeys.CATEGORY);
        e.event_location = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.LOCATION));
        e.id = json.getInt(Constants.JsonKeys.ID);
        e.likes = json.getInt(Constants.JsonKeys.LIKES);
        e.views = json.getInt(Constants.JsonKeys.VIEWS);
        e.image_links = new ArrayList<>();
        JSONArray image_links = json.getJSONArray(Constants.JsonKeys.IMAGE_LIST);
        e.image_links = new ArrayList<>();
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }
        JSONObject source_json = json.getJSONObject(Constants.JsonKeys.SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JsonKeys.SOURCE_NAME);
        e.source_email = source_json.getString(Constants.JsonKeys.SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JsonKeys.SOURCE_DESIGNATION);

        e.liked = json.getBoolean(Constants.JsonKeys.LIKED);
        e.viewed = json.getBoolean(Constants.JsonKeys.VIEWED);
        return e;
    }

    public static ApiItem createNewsItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_NEWS;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.DESCRIPTION));
        e.timestamp = json.getString(Constants.JsonKeys.TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.category = json.getString(Constants.JsonKeys.CATEGORY);
        e.id = json.getInt(Constants.JsonKeys.ID);
        e.likes = json.getInt(Constants.JsonKeys.LIKES);
        e.views = json.getInt(Constants.JsonKeys.VIEWS);
        JSONArray image_links = json.getJSONArray(Constants.JsonKeys.IMAGE_LIST);
        e.image_links = new ArrayList<>();
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }
        JSONObject source_json = json.getJSONObject(Constants.JsonKeys.SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JsonKeys.SOURCE_NAME);
        e.source_email = source_json.getString(Constants.JsonKeys.SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JsonKeys.SOURCE_DESIGNATION);

        e.liked = json.getBoolean(Constants.JsonKeys.LIKED);
        e.viewed = json.getBoolean(Constants.JsonKeys.VIEWED);
        return e;
    }

    public static ApiItem createNoticeItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_NOTICE;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.DESCRIPTION));
        e.timestamp = json.getString(Constants.JsonKeys.TIMESTAMP);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.id = json.getInt(Constants.JsonKeys.ID);
        e.notice_timestamp = json.getString(Constants.JsonKeys.EXPIRATION);
        e.expiration_time = new TimestampItem(context, e.notice_timestamp);
        e.notice_priority = json.getString(Constants.JsonKeys.PRIORITY);
        e.category = CategoryImages.Category.NOTICE;
        e.image_links = new ArrayList<>();

        JSONObject source_json = json.getJSONObject(Constants.JsonKeys.SOURCE_JSON);
        e.source_name = source_json.getString(Constants.JsonKeys.SOURCE_NAME);
        e.source_email = source_json.getString(Constants.JsonKeys.SOURCE_EMAIL);
        e.source_designation = source_json.getString(Constants.JsonKeys.SOURCE_DESIGNATION);
        return e;
    }

    public static ApiItem createFeedItem(Context context, JSONObject json) throws Exception {
        ApiItem e = new ApiItem();
        e.type = Constants.JSON_DATA_TYPE_FEED;
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.CONTENT));
        e.timestamp = json.getString(Constants.JsonKeys.UPDATED);
        e.article_time = new TimestampItem(context, e.timestamp);
        e.id = json.getInt(Constants.JsonKeys.ID);
        e.feed_id = json.getInt(Constants.JsonKeys.FEED_CONFIG);
        e.category = CategoryImages.Category.FEED;

        e.image_links = new ArrayList<>();
        JSONArray image_links = json.getJSONArray(Constants.JsonKeys.IMAGE_LIST);
        for (int i = 0; i < image_links.length(); i++) {
            e.image_links.add(image_links.getString(i));
        }

        e.source_name = json.getString(Constants.JsonKeys.AUTHOR);
        e.source_email = json.getString(Constants.JsonKeys.LINK);
        e.source_designation = json.getString(Constants.JsonKeys.LINK);

        e.likes = json.getInt(Constants.JsonKeys.LIKES);
        e.views = json.getInt(Constants.JsonKeys.VIEWS);
        e.liked = json.getBoolean(Constants.JsonKeys.LIKED);
        e.viewed = json.getBoolean(Constants.JsonKeys.VIEWED);
        return e;
    }

    public static FeedSubscriptionItem createFeedInformation(Context context, JSONObject json) throws Exception {
        Log.d("FEED", json.toString());

        FeedSubscriptionItem e = new FeedSubscriptionItem();
        e.title = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.TITLE));
        e.description = Functions.correctUTFEncoding(json.getString(Constants.JsonKeys.LINK));
        e.feed_config = json.getInt(Constants.JsonKeys.ID);
        e.url = json.getString(Constants.JsonKeys.LINK);
        e.updated = json.getString(Constants.JsonKeys.UPDATED);


        JSONArray categories = json.getJSONArray(Constants.JsonKeys.CATEGORIES);
        for (int i = 0; i < categories.length(); i++) {
            JSONObject category = categories.getJSONObject(i);
            e.categories.add(new FeedCategoryItem(
                    category.getInt(Constants.JsonKeys.ID),
                    category.getInt(Constants.JsonKeys.FEED_CONFIG),
                    category.getString(Constants.JsonKeys.TERM),
                    category.getString(Constants.JsonKeys.SCHEME),
                    category.getString(Constants.JsonKeys.LABEL),
                    category.getBoolean(Constants.JsonKeys.SUBSCRIBED)
            ));
        }

        return e;
    }

    public static InformationItem createInformationItem(Context context, JSONObject json, int icon_resource) throws Exception {
        InformationItem i = new InformationItem();
        i.title = json.getString(Constants.JsonKeys.TITLE);
        i.description = json.getString(Constants.JsonKeys.DESCRIPTION);
        i.facebook = json.getString(Constants.JsonKeys.FACEBOOK);
        i.phone = json.getString(Constants.JsonKeys.PHONE);
        i.website = json.getString(Constants.JsonKeys.LINK);
        i.email = json.getString(Constants.JsonKeys.EMAIL);
        i.img_resource = icon_resource;
        return i;
    }

}
