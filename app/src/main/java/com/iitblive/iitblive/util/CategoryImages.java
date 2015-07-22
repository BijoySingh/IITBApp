package com.iitblive.iitblive.util;

import com.iitblive.iitblive.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 7/10/15.
 */
public class CategoryImages {

    public static int getDrawable(String type) {
        Map<String, Integer> mResourceMapping = new HashMap<>();
        mResourceMapping.put(Category.MUSIC, Resource.MUSIC);
        mResourceMapping.put(Category.PHOTO, Resource.PHOTO);
        mResourceMapping.put(Category.ART, Resource.ART);
        mResourceMapping.put(Category.SPORTS, Resource.SPORTS);
        mResourceMapping.put(Category.ACTIVITY, Resource.ACTIVITY);
        mResourceMapping.put(Category.TECH, Resource.TECH);
        mResourceMapping.put(Category.FOOD, Resource.FOOD);
        mResourceMapping.put(Category.LITERATURE, Resource.LITERATURE);
        mResourceMapping.put(Category.NOTICE, Resource.NOTICE);

        if (mResourceMapping.containsKey(type)) {
            return mResourceMapping.get(type);
        }
        return Resource.DEFAULT;
    }

    public static class Category {
        public static String MUSIC = "music";
        public static String PHOTO = "photo";
        public static String ART = "art";
        public static String SPORTS = "sports";
        public static String ACTIVITY = "activity";
        public static String TECH = "tech";
        public static String FOOD = "food";
        public static String LITERATURE = "literature";
        public static String NOTICE = "notice";
    }

    private static class Resource {
        public static int MUSIC = R.drawable.ic_audiotrack_white_48dp;
        public static int PHOTO = R.drawable.ic_camera_alt_white_48dp;
        public static int ART = R.drawable.ic_color_lens_white_48dp;
        public static int SPORTS = R.drawable.ic_directions_bike_white_48dp;
        public static int ACTIVITY = R.drawable.ic_directions_walk_white_48dp;
        public static int TECH = R.drawable.ic_laptop_white_48dp;
        public static int FOOD = R.drawable.ic_local_dining_white_48dp;
        public static int LITERATURE = R.drawable.ic_local_library_white_48dp;
        public static int DEFAULT = R.drawable.ic_people_white_48dp;
        public static int NOTICE = R.drawable.ic_chrome_reader_mode_white_48dp;
    }
}
