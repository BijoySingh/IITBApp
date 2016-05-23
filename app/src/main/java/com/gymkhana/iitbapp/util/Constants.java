package com.gymkhana.iitbapp.util;

import android.graphics.Color;

/**
 * Created by Bijoy on 3/6/2015.
 * This class contains all the core static final variables in the app
 */
public class Constants {
    public static final Boolean PRODUCTION_MODE = true;
    public static final String GCM_SENDER_ID = "388067495650";

    public static final String JSON_DATA_TYPE_EVENT = "event";
    public static final String JSON_DATA_TYPE_NEWS = "news";
    public static final String JSON_DATA_TYPE_NOTICE = "notice";
    public static final String JSON_DATA_TYPE_FEED = "feed";

    public static final Integer DATA_TYPE_EVENT = 0;
    public static final Integer DATA_TYPE_NEWS = 1;
    public static final Integer DATA_TYPE_NOTICE = 2;
    public static final Integer DATA_TYPE_ANY = 3;
    public static final Integer DATA_TYPE_INFORMATION = 4;
    public static final Integer DATA_TYPE_RSS = 5;
    public static final Integer DATA_TYPE_FEED = 6;
    public static final Integer DATA_TYPE_FEED_INFO = 6;

    public static final Integer LOGIN_REQUEST_CODE = 0;
    public static final Integer LOGIN_LOG_INTO = 1;
    public static final Integer LOGIN_LDAP_LOGIN = 2;
    public static final String PACKAGE_NAME_INSTIMAP = "in.designlabs.instimap";

    public static final class JsonKeys {
        public static final String RESULTS = "results";
        public static final String COUNT = "count";
        public static final String NEXT = "next";
        public static final String PREVIOUS = "previous";

        // News Json
        public static final String ID = "id";
        public static final String LIKES = "likes";
        public static final String VIEWS = "views";
        public static final String LIKED = "liked";
        public static final String VIEWED = "viewed";
        public static final String IMAGE_LIST = "images";
        public static final String TIMESTAMP = "time";
        public static final String CATEGORY = "category";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";

        // Source Json
        public static final String SOURCE_JSON = "posted_by";
        public static final String SOURCE_NAME = "name";
        public static final String SOURCE_DESIGNATION = "post";
        public static final String SOURCE_EMAIL = "email";

        // Events Json
        public static final String EVENT_TIMESTAMP = "event_time";
        public static final String LOCATION = "event_place";

        // Notice Json
        public static final String PRIORITY = "priority";
        public static final String EXPIRATION = "expiration_date";
        public static final String PHONE = "phone";
        public static final String FACEBOOK = "facebook";
        public static final String LINK = "link";
        public static final String EMAIL = "email";

        // Feed Json
        public static final String CONTENT = "content";
        public static final String AUTHOR = "author";
        public static final String UPDATED = "updated";
        public static final String FEED_CONFIG = "feed_config";
        public static final String CATEGORIES = "categories";
        public static final String TERM = "term";
        public static final String SCHEME = "scheme";
        public static final String LABEL = "label";
        public static final String SUBSCRIBED = "subscribed";
    }

    public static final class Article {
        public static final String REQUEST_USER = "user";
        public static final String REQUEST_CONTENT_ID = "content_id";
        public static final String REQUEST_NEWS = "news";
        public static final String REQUEST_EVENT = "event";
        public static final String REQUEST_ENTRY = "entry";
        public static final String REQUEST_GUID = "guid";

        public static final String RESPONSE_VIEWED = "viewed";
        public static final String RESPONSE_LIKED = "liked";
        public static final String RESPONSE_LIKES = "likes";
        public static final String RESPONSE_VIEWS = "views";

        public static final String ACTION_URL_LIKE = "like/";
        public static final String ACTION_URL_UNLIKE = "unlike/";
        public static final String ACTION_URL_VIEW = "view/";

        public static final int ACTION_LIKE = 0;
        public static final int ACTION_UNLIKE = 1;
        public static final int ACTION_VIEW = 2;
    }

    public static final class Filenames {
        public static final String NEWS = "cache_news.txt";
        public static final String EVENT = "cache_event.txt";
        public static final String NOTICE = "cache_notice.txt";
        public static final String FEED = "cache_feed.txt";
        public static final String INFO_FEED = "cache_info_feed.txt";
        public static final String INFO_GROUPS = "cache_info_groups.txt";
        public static final String INFO_DEPARTMENT = "cache_info_departments.txt";
        public static final String INFO_CONTACTS = "cache_info_contacts.txt";
        public static final String INFO_EMERGENCY = "cache_info_emergency.txt";
    }

    public static final class ButtonTypes {
        public static final int LDAP_LOGIN = 0;
        public static final int IITB_HOME = 1;
        public static final int REQUEST_CODE = 2;
        public static final int OFFLINE = 3;
        public static final int RESEND_CODE = 4;
        public static final int LOGIN = 5;
        public static final int CHANGE_LDAP = 6;
    }

    public static final class Colors {
        public static final int ACCENT_DEFAULT = Color.parseColor("#03A9F4");

        public static final int PRIMARY_DARK_DISABLED = Color.parseColor("#455A64");
        public static final int PRIMARY_DISABLED = Color.parseColor("#607D8E");

        public static final int PRIMARY_DARK_EVENT = Color.parseColor("#00796B");
        public static final int PRIMARY_EVENT = Color.parseColor("#009688");

        public static final int PRIMARY_DARK_NEWS = Color.parseColor("#1976D2");
        public static final int PRIMARY_NEWS = Color.parseColor("#2196F3");

        public static final int PRIMARY_DARK_NOTICES = Color.parseColor("#303F9F");
        public static final int PRIMARY_NOTICES = Color.parseColor("#3F51B5");

        public static final int PRIMARY_DARK_FEED = Color.parseColor("#00838F");
        public static final int PRIMARY_FEED = Color.parseColor("#0097A7");
    }

    public static final class User {
        public static final String REQUEST_FIRST_NAME = "first_name";
        public static final String REQUEST_LAST_NAME = "last_name";
        public static final String REQUEST_EMAIL = "email";
        public static final String REQUEST_EMPLOYEE_NUMBER = "employeeNumber";
        public static final String RESPONSE_ID = "id";
    }

    public static final class Ldap {
        public static final String REQUEST_USERNAME = "username";
        public static final String REQUEST_PASSWORD = "password";
        public static final String REQUEST_USER = "user";

        public static final String RESPONSE_LDAP = "ldap";
        public static final String RESPONSE_USER_ID = "id";
        public static final String RESPONSE_USER_TOKEN = "token";
        public static final String RESPONSE_FIRST_NAME = "first_name";
        public static final String RESPONSE_LAST_NAME = "last_name";
        public static final String RESPONSE_NAME = "name";
        public static final String RESPONSE_EMPLOYEE_NUMBER = "employeeNumber";
        public static final String RESPONSE_ERROR = "error";
        public static final String RESPONSE_ERROR_MESSAGE = "error_message";
        public static final String RESPONSE_EMAIL = "email";
    }

    public static final class Gcm {
        public static final String MESSAGE = "msg";
        public static final String ACTION = "action";
        public static final String TYPE = "type";
        public static final String ITEM = "item";
    }
}
