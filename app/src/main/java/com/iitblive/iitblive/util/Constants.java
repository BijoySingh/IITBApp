package com.iitblive.iitblive.util;

import android.graphics.Color;

/**
 * Created by Bijoy on 3/6/2015.
 * This class contains all the core static variables in the app
 */
public class Constants {
  public static String BASE_URL = "http://bijoysingh.com/iitbapp_test/";
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

  public static String URL_NEWS = "news.php";
  public static String URL_EVENTS = "events.php";
  public static String URL_ANY = "both.php";

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

  public static String JSON_KEY_TITLE = "title";
  public static String JSON_KEY_DESCRIPTION = "description";
  public static String JSON_KEY_IMAGE_LIST = "images";
  public static String JSON_KEY_TIMESTAMP = "timestamp";
  public static String JSON_KEY_EVENT_TIMESTAMP = "event_timestamp";
  public static String JSON_KEY_SOURCE_JSON = "source";
  public static String JSON_KEY_ID = "id";
  public static String JSON_KEY_LIKES = "likes";
  public static String JSON_KEY_VIEWS = "views";
  public static String JSON_KEY_TYPE = "type";
  public static String JSON_KEY_CATEGORY = "category";
  public static String JSON_KEY_SOURCE_NAME = "name";
  public static String JSON_KEY_SOURCE_DESIGNATION = "designation";
  public static String JSON_KEY_LOCATION = "location";
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

  public static int EVENT_EMPHASIS_COLOR = Color.rgb(159, 209, 167);
  public static int NEWS_EMPHASIS_COLOR = Color.rgb(252, 210, 135);
  public static int NOTICE_EMPHASIS_COLOR = Color.rgb(250, 127, 138);
  public static int NO_IMAGE_EMPHASIS_COLOR = Color.rgb(91, 163, 222);

}
