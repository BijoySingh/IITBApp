package com.gymkhana.iitbapp.feed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bijoy on 8/5/15.
 */
public class RSSFeedChannel {
    public String title;
    public String updated;

    public List<RSSFeedItem> entries = new ArrayList<>();

    public List<RSSFeedItem> getFeed(int count) {
        List<RSSFeedItem> sublist = new ArrayList<>();
        for (RSSFeedItem feed : entries) {
            if (count == 0)
                return sublist;
            sublist.add(feed);
            count--;
        }
        return sublist;
    }
}
