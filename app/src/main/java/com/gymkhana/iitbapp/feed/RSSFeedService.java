package com.gymkhana.iitbapp.feed;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * Created by bijoy on 8/5/15.
 */
public class RSSFeedService extends IntentService {

    public RSSFeedService() {
        super("RSS_FEED_INTENT");
    }

    public RSSFeedService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        Context context = getBaseContext();
        /*
        for (RSSFeedConstants.Feed feed : RSSFeedConstants.feeds) {
            if (SharedPreferenceManager.load(context, feed.feed_id).contentEquals(SharedPreferenceManager.Tags.FALSE)) {
                continue;
            }
            String username = null;
            String password = null;

            if (feed.authenticated) {
                username = SharedPreferenceManager.getUsername(context);
                password = SharedPreferenceManager.getPassword(context);
            }
            RSSFeedFetcher.getFeedForNotification(context, feed.url, username, password, feed);
        }
        */
    }
}