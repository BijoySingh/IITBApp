package com.iitblive.iitblive.items;

import com.iitblive.iitblive.fragment.HomeFragment;

import java.util.List;

/**
 * Created by bijoy on 7/21/15.
 */
public class NowCardItem {
    public String mTitle, mDescription;
    public List<ApiItem> mData;
    public Integer mIconResource, mColor, mType, mFragmentId;

    public NowCardItem(String mTitle, String mDescription, List<ApiItem> mData) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mData = mData;
    }

    public NowCardItem(String mTitle, String mDescription) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
    }

    public NowCardItem(HomeFragment.NowCardMetaContent metaContent, List<ApiItem> mData) {
        this.mTitle = metaContent.title;
        this.mColor = metaContent.color;
        this.mType = metaContent.type;
        this.mFragmentId = metaContent.fragmentId;
        this.mIconResource = metaContent.iconResource;
        this.mData = mData;
    }

    public NowCardItem(String mTitle, List<ApiItem> mData) {
        this.mTitle = mTitle;
        this.mData = mData;
    }

    public static final class CardTypes {
        public static final int EVENTS = 0;
        public static final int NEWS = 1;
        public static final int NOTICES = 2;
        public static final int TIMETABLE = 3;
    }
}
