package com.gymkhana.iitbapp.items;

import java.io.Serializable;

/**
 * Created by bijoy on 8/5/15.
 */
public class FeedSubscriptionItem implements Serializable {
    public String title, description;
    public String url;
    public String updated;
    public Integer feed_id;

    public String prefKey() {
        return "FEED_PREF" + feed_id;
    }

    public String filename() {
        return "cache_feed_" + feed_id + ".txt";
    }
}