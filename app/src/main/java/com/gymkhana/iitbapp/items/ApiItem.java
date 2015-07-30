package com.gymkhana.iitbapp.items;

import com.gymkhana.iitbapp.util.Constants;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Bijoy on 5/25/2015.
 */
public class ApiItem implements Serializable {
    public Integer id;
    public String type, title, description, category;
    public String source_name, source_designation, source_email, timestamp;
    public String event_timestamp, event_location, notice_priority, notice_timestamp;
    public Integer likes, views;
    public List<String> image_links;
    public TimestampItem article_time, event_time, expiration_time;
    public boolean liked, viewed;

    public boolean hasExpired() {
        TimestampItem timestampItem;
        if (type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            timestampItem = event_time;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return false;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)
                && expiration_time.isNull) {
            return false;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            timestampItem = expiration_time;
        } else {
            timestampItem = article_time;
        }

        Calendar current = Calendar.getInstance();
        return current.compareTo(timestampItem.calender) > 0;
    }

    public int getAccentColor() {
        return getPrimaryColor();
    }

    public int getNavigationColor() {
        if (hasExpired()) {
            return Constants.Colors.PRIMARY_DARK_DISABLED;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return Constants.Colors.PRIMARY_DARK_EVENT;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return Constants.Colors.PRIMARY_DARK_NEWS;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            return Constants.Colors.PRIMARY_DARK_NOTICES;
        }

        return Constants.Colors.ACCENT_DEFAULT;
    }

    public int getPrimaryColor() {
        if (hasExpired()) {
            return Constants.Colors.PRIMARY_DISABLED;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return Constants.Colors.PRIMARY_EVENT;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return Constants.Colors.PRIMARY_NEWS;
        } else if (type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            return Constants.Colors.PRIMARY_NOTICES;
        }

        return Constants.Colors.ACCENT_DEFAULT;
    }

}
