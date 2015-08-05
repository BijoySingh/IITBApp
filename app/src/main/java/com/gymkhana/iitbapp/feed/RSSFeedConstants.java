package com.gymkhana.iitbapp.feed;

import java.io.Serializable;

/**
 * Created by bijoy on 8/5/15.
 */
public class RSSFeedConstants {

    public static final Feed[] feeds = new Feed[]{
            new Feed("Insight", "http://www.insightiitb.org/feed/atom/", false,
                    "feed_insight", "Latest news updates from InsIghT's feed"),
            new Feed("Placement Cell", "http://placements.iitb.ac.in/blog/?feed=atom", true,
                    "feed_placement_cell", "Updates from the placement cell @ IITBombay"),
            new Feed("Internship Portal", "http://placements.iitb.ac.in/trainingblog/?feed=atom", true,
                    "feed_internship_portal", "Feed from the internship portal")
    };

    public static final class Feed implements Serializable {
        public String title, description;
        public String url;
        public String feed_id;
        public boolean authenticated;

        public Feed(String title, String url, boolean authenticated, String feed_id, String description) {
            this.title = title;
            this.url = url;
            this.authenticated = authenticated;
            this.feed_id = feed_id;
            this.description = description;
        }

        public String filename() {
            return feed_id + ".txt";
        }

        public String lastUpdated() {
            return feed_id + "_updated";
        }
    }
}
