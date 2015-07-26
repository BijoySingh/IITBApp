package com.gymkhana.iitbapp.items;

/**
 * Created by Bijoy on 6/21/2015.
 */
public class CourseDataItem {
    public String mName, mSlotTag;
    public String mLocation;

    public CourseDataItem(String mSlotTag, String mName, String mLocation) {
        this.mName = mName;
        this.mSlotTag = mSlotTag;
        this.mLocation = mLocation;
    }

    public CourseDataItem() {
    }
}
