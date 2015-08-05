package com.gymkhana.iitbapp.feed;

import com.gymkhana.iitbapp.util.Constants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bijoy on 8/4/15.
 */
public class RSSFeedItem implements Serializable {
    public String title;
    public String description;
    public String guid;
    public String name = "";
    public String publishDate;
    public List<String> category = new ArrayList<>();

    // Meta Data not fetched
    public boolean liked = false;
    public boolean viewed = false;
    public Integer likes;
    public Integer views;

    public int getAccentColor() {
        return getPrimaryColor();
    }

    public int getNavigationColor() {
        return Constants.Colors.PRIMARY_DARK_FEED;
    }

    public int getPrimaryColor() {
        return Constants.Colors.PRIMARY_FEED;
    }

}
