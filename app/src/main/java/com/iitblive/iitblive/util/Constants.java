package com.iitblive.iitblive.util;

import android.graphics.Color;

/**
 * Created by Bijoy on 3/6/2015.
 * This class contains all the core static variables in the app
 */
public class Constants {
    public static String BASE_URL = "http://iitbapp.herokuapp.com/api/";
    public static String BASE_URL_OPEN = "http://iitbapp.herokuapp.com/api";
    public static String SERVER_REFERER = "Referer";
    public static String LDAP_AUTH_URL = "http://www.cse.iitb.ac.in/~bijoy/authenticate.php";
    public static String IITB_HOME_URL = "http://www.iitb.ac.in/";

    public static String SHARED_PREFERENCES = "IITBAPP";

    public static String SP_NAME = "NAME";
    public static String SP_USERNAME = "USERNAME";
    public static String SP_LOGGED_IN = "LOGGED_IN";
    public static String SP_FIRST_TIME = "FIRST_TIME";

    public static String SP_TRUE = "TRUE";
    public static String SP_FALSE = "FALSE";
    public static String SP_OFFLINE = "OFFLINE";
    public static String SP_EMPTY = "";

    public static String URL_NEWS = "news";
    public static String URL_EVENTS = "event";
    public static String URL_NOTICES = "notice";

    public static String JSON_DATA_TYPE_EVENT = "E";
    public static String JSON_DATA_TYPE_NEWS = "N";
    public static String JSON_DATA_TYPE_NOTICE = "C";

    public static Integer DATA_TYPE_EVENT = 0;
    public static Integer DATA_TYPE_NEWS = 1;
    public static Integer DATA_TYPE_NOTICE = 2;
    public static Integer DATA_TYPE_ANY = 3;
    public static Integer DATA_TYPE_INFORMATION = 4;

    public static Integer DATA_COUNT_SINGLE = 0;
    public static Integer DATA_COUNT_MULTIPLE = 1;

    public static Integer DESCRIPTION_CROP_SIZE = 60;

    // Json Total Data
    public static String JSON_KEY_RESULTS = "results";
    public static String JSON_KEY_COUNT = "count";
    public static String JSON_KEY_NEXT = "next";
    public static String JSON_KEY_PREVIOUS = "previous";

    // News Json
    public static String JSON_KEY_ID = "id";
    public static String JSON_KEY_LIKES = "likes";
    public static String JSON_KEY_VIEWS = "views";
    public static String JSON_KEY_IMAGE_LIST = "images";
    public static String JSON_KEY_TIMESTAMP = "time";
    public static String JSON_KEY_CATEGORY = "category";
    public static String JSON_KEY_PUBLISHED = "published";
    public static String JSON_KEY_TITLE = "title";
    public static String JSON_KEY_DESCRIPTION = "description";

    // Source Json
    public static String JSON_KEY_SOURCE_JSON = "posted_by";
    public static String JSON_KEY_SOURCE_NAME = "name";
    public static String JSON_KEY_SOURCE_DESIGNATION = "post";
    public static String JSON_KEY_SOURCE_EMAIL = "email";

    // Events Json
    public static String JSON_KEY_EVENT_TIMESTAMP = "event_time";
    public static String JSON_KEY_LOCATION = "event_place";

    // Notice Json
    public static String JSON_KEY_ISSUE_DATE = "issue_date";
    public static String JSON_KEY_PRIORITY = "priority";
    public static String JSON_KEY_EXPIRATION = "expiration_date";

    public static String JSON_KEY_PHONE = "phone";
    public static String JSON_KEY_FACEBOOK = "facebook";
    public static String JSON_KEY_LINK = "link";
    public static String JSON_KEY_EMAIL = "email";

    public static Integer LOGIN_REQUEST_CODE = 0;
    public static Integer LOGIN_LOG_INTO = 1;
    public static Integer LOGIN_LDAP_LOGIN = 2;

    public static String FILENAME_HOME = "cache_home.txt";
    public static String FILENAME_NEWS = "cache_news.txt";
    public static String FILENAME_EVENT = "cache_event.txt";
    public static String FILENAME_NOTICE = "cache_notice.txt";
    public static String FILENAME_INFO_GROUPS = "cache_info_groups.txt";
    public static String FILENAME_INFO_DEPARTMENT = "cache_info_departments.txt";
    public static String FILENAME_INFO_CONTACTS = "cache_info_contacts.txt";
    public static String FILENAME_INFO_EMERGENCY = "cache_info_emergency.txt";

    public static String PACKAGE_NAME_INSTIMAP = "in.designlabs.instimap";

    public static String ACADEMIC_CAL_URL = "http://www.iitb.ac.in/newacadhome/toacadcalender.jsp";

    public static int BUTTON_TYPE_LDAP_LOGIN = 0;
    public static int BUTTON_TYPE_IITB_HOME = 1;
    public static int BUTTON_TYPE_REQUEST_CODE = 2;
    public static int BUTTON_TYPE_OFFLINE = 3;
    public static int BUTTON_TYPE_RESEND_CODE = 4;
    public static int BUTTON_TYPE_LOGIN = 5;
    public static int BUTTON_TYPE_CHANGE_LDAP = 6;

    public static String LDAP_AUTH_USERNAME_PARAM = "ldap_username";
    public static String LDAP_AUTH_PASSWORD_PARAM = "ldap_password";

    public static int ACCENT_COLOR = Color.rgb(30, 136, 229);
    public static int ACCENT_COLOR_TEAL = Color.rgb(0, 150, 136);
    public static int NOTICE_EMPHASIS_COLOR_LOW = Color.rgb(124, 179, 66);
    public static int NOTICE_EMPHASIS_COLOR_MEDIUM = Color.rgb(255, 193, 7);
    public static int NOTICE_EMPHASIS_COLOR_HIGH = Color.rgb(244, 81, 30);
    public static int NOTICE_EMPHASIS_COLOR_URGENT = Color.rgb(211, 47, 47);

    public static String NOTICE_PRIORITY_LOW = "0";
    public static String NOTICE_PRIORITY_MEDIUM = "1";
    public static String NOTICE_PRIORITY_HIGH = "2";
    public static String NOTICE_PRIORITY_URGENT = "3";

}
