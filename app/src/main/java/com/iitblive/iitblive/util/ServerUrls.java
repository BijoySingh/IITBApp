package com.iitblive.iitblive.util;

/**
 * Created by bijoy on 7/22/15.
 */
public class ServerUrls {
    public static final String DEFAULT_URL = "http://gymkhana.iitb.ac.in/iitbapp/";
    public String SERVER, GCM_REGISTER, GCM_UNREGISTER, IITB_HOME, ACADEMIC_CAL, API, USER,
            NEWS, EVENTS, NOTICES, PUBLIC_API, INFORMATION_API, DEPARTMENTS, CLUBS, CONTACTS, EMERGENCY,
            AUTHENTICATE;

    private ServerUrls(String serverUrl) {
        SERVER = serverUrl;
        GCM_REGISTER = SERVER + "gcm/v1/device/register/";
        GCM_UNREGISTER = SERVER + "gcm/v1/device/unregister/";
        IITB_HOME = "http://www.iitb.ac.in/";
        ACADEMIC_CAL = IITB_HOME + "newacadhome/toacadcalender.jsp";
        API = SERVER + "api/";
        USER = API + "users/";
        NEWS = API + "news/";
        AUTHENTICATE = API + "users/authenticate/";
        EVENTS = API + "event/";
        NOTICES = API + "notice/";
        PUBLIC_API = SERVER + "public/api/";
        INFORMATION_API = PUBLIC_API + "information/";
        DEPARTMENTS = INFORMATION_API + "department/";
        CLUBS = INFORMATION_API + "club/";
        CONTACTS = INFORMATION_API + "contact/";
        EMERGENCY = INFORMATION_API + "emergency_contact/";
    }

    public static final ServerUrls getInstance() {
        return new ServerUrls(DEFAULT_URL);
    }
}
