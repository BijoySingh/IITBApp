package com.gymkhana.iitbapp.items;

import com.gymkhana.iitbapp.util.Constants;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by bijoy on 9/2/15.
 */
public class FeedCategoryItem implements Serializable {
    public Integer id, feed_config;
    public String term, scheme, label;
    public Boolean subscribed;

    public FeedCategoryItem(JSONObject category) throws Exception {
        id = category.getInt(Constants.JsonKeys.ID);
        feed_config = category.getInt(Constants.JsonKeys.FEED_CONFIG);
        term = category.getString(Constants.JsonKeys.TERM);
        scheme = category.getString(Constants.JsonKeys.SCHEME);
        label = category.getString(Constants.JsonKeys.LABEL);
        subscribed = category.getBoolean(Constants.JsonKeys.SUBSCRIBED);
    }

    public FeedCategoryItem(Integer id, Integer feed_config, String term, String scheme, String label, Boolean subscribed) {
        this.id = id;
        this.feed_config = feed_config;
        this.term = term;
        this.scheme = scheme;
        this.label = label;
        this.subscribed = subscribed;
    }
}
