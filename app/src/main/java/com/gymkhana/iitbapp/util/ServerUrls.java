package com.gymkhana.iitbapp.util;

import android.content.Context;

import com.gymkhana.iitbapp.items.FeedSubscriptionItem;

import java.util.List;

/**
 * Created by bijoy on 7/22/15.
 */
public class ServerUrls {
    public static final String DEFAULT_URL = "http://gymkhana.iitb.ac.in/iitbapp/";
    public static final String DEBUG_URL = "http://gymkhana.iitb.ac.in/iitbapp_dev/";
    public String SERVER, GCM_REGISTER, GCM_UNREGISTER, IITB_HOME, ACADEMIC_CAL, API, USER,
            NEWS, EVENTS, NOTICES, PUBLIC_API, INFORMATION_API, DEPARTMENTS, CLUBS, CONTACTS, EMERGENCY,
            AUTHENTICATE, LOGOUT, BUG, FEEDS, FEED_ENTRIES;

    private ServerUrls(String serverUrl) {
        SERVER = serverUrl;
        GCM_REGISTER = SERVER + "api/pns/register/";
        GCM_UNREGISTER = SERVER + "api/pns/deregister/";
        IITB_HOME = "http://www.iitb.ac.in/";
        ACADEMIC_CAL = IITB_HOME + "newacadhome/toacadcalender.jsp";
        API = SERVER + "api/";
        USER = API + "users/";
        BUG = API + "bug/add/";
        NEWS = API + "news/";
        AUTHENTICATE = USER + "authenticate/";
        LOGOUT = USER + "logout/";
        EVENTS = API + "event/";
        NOTICES = API + "notice/";
        FEEDS = API + "feeds/";
        FEED_ENTRIES = API + "feeds/entries/";
        PUBLIC_API = SERVER + "public/api/";
        INFORMATION_API = PUBLIC_API + "information/";
        DEPARTMENTS = INFORMATION_API + "department/";
        CLUBS = INFORMATION_API + "club/";
        CONTACTS = INFORMATION_API + "contact/";
        EMERGENCY = INFORMATION_API + "emergency_contact/";
    }

    public static final ServerUrls getInstance() {
        if (Constants.PRODUCTION_MODE) {
            return new ServerUrls(DEFAULT_URL);
        } else {
            return new ServerUrls(DEBUG_URL);
        }
    }

    public String getFeedUrl(Context context) {
        String link = FEED_ENTRIES;
        String append = "?id=";
        List<FeedSubscriptionItem> feedList = Functions.getSubscriptions(context);
        if (feedList != null && !feedList.isEmpty()) {
            for (FeedSubscriptionItem feed : feedList) {
                if (!LocalData.load(context, feed.prefKey()).contentEquals(LocalData.Tags.FALSE)) {
                    append += feed.feed_id + ",";
                }
            }
            if (append.contentEquals("?id="))
                return null;
            link += append;
        }
        return link;
    }
}
