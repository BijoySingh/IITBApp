package com.iitblive.iitblive.util;

import com.iitblive.iitblive.items.CourseDataItem;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 7/22/15.
 */
public class IitbTimetableUtil {

    public Map<String, TimetableItem> mTimings = new HashMap<>();
    public Map<String, String[]> mSlotSets = new HashMap<>();

    private IitbTimetableUtil() {
        setupSlotTimings();
        setupSlotSets();
    }

    public static IitbTimetableUtil getInstance() {
        return new IitbTimetableUtil();
    }

    private void setupSlotTimings() {
        mTimings.put("1A", new TimetableItem(Calendar.MONDAY, 8, 30, 9, 25, "1", "1A"));
        mTimings.put("1B", new TimetableItem(Calendar.TUESDAY, 9, 30, 10, 25, "1", "1B"));
        mTimings.put("1C", new TimetableItem(Calendar.THURSDAY, 10, 35, 11, 30, "1", "1C"));

        mTimings.put("2A", new TimetableItem(Calendar.MONDAY, 9, 30, 10, 25, "2", "2A"));
        mTimings.put("2B", new TimetableItem(Calendar.TUESDAY, 10, 35, 11, 30, "2", "2B"));
        mTimings.put("2C", new TimetableItem(Calendar.THURSDAY, 11, 35, 12, 30, "2", "2C"));

        mTimings.put("3A", new TimetableItem(Calendar.MONDAY, 10, 35, 11, 30, "3", "3A"));
        mTimings.put("3B", new TimetableItem(Calendar.TUESDAY, 11, 35, 12, 30, "3", "3B"));
        mTimings.put("3C", new TimetableItem(Calendar.THURSDAY, 8, 30, 9, 25, "3", "3C"));

        mTimings.put("4A", new TimetableItem(Calendar.MONDAY, 11, 35, 12, 30, "4", "4A"));
        mTimings.put("4B", new TimetableItem(Calendar.TUESDAY, 8, 30, 9, 25, "4", "4B"));
        mTimings.put("4C", new TimetableItem(Calendar.THURSDAY, 9, 30, 10, 25, "4", "4C"));

        mTimings.put("5A", new TimetableItem(Calendar.WEDNESDAY, 9, 30, 10, 55, "5", "5A"));
        mTimings.put("5B", new TimetableItem(Calendar.FRIDAY, 9, 30, 10, 55, "5", "5B"));

        mTimings.put("6A", new TimetableItem(Calendar.WEDNESDAY, 11, 05, 12, 30, "6", "6A"));
        mTimings.put("6B", new TimetableItem(Calendar.FRIDAY, 11, 05, 12, 30, "6", "6B"));

        mTimings.put("7A", new TimetableItem(Calendar.WEDNESDAY, 8, 30, 9, 25, "7", "7A"));
        mTimings.put("7B", new TimetableItem(Calendar.FRIDAY, 8, 30, 9, 25, "7", "7B"));

        mTimings.put("8A", new TimetableItem(Calendar.MONDAY, 14, 00, 15, 25, "8", "8A"));
        mTimings.put("8B", new TimetableItem(Calendar.THURSDAY, 14, 00, 15, 25, "8", "8B"));

        mTimings.put("9A", new TimetableItem(Calendar.MONDAY, 15, 30, 16, 55, "9", "9A"));
        mTimings.put("9B", new TimetableItem(Calendar.THURSDAY, 15, 30, 16, 55, "9", "9B"));

        mTimings.put("10A", new TimetableItem(Calendar.TUESDAY, 14, 00, 15, 25, "10", "10A"));
        mTimings.put("10B", new TimetableItem(Calendar.FRIDAY, 14, 00, 15, 25, "10", "10B"));

        mTimings.put("11A", new TimetableItem(Calendar.TUESDAY, 15, 30, 16, 55, "11", "11A"));
        mTimings.put("11B", new TimetableItem(Calendar.FRIDAY, 15, 30, 16, 55, "11", "11B"));

        mTimings.put("12A", new TimetableItem(Calendar.MONDAY, 17, 05, 18, 30, "12", "12A"));
        mTimings.put("12B", new TimetableItem(Calendar.THURSDAY, 17, 05, 18, 30, "12", "12B"));

        mTimings.put("13A", new TimetableItem(Calendar.MONDAY, 18, 35, 20, 00, "13", "13A"));
        mTimings.put("13B", new TimetableItem(Calendar.THURSDAY, 18, 35, 20, 00, "13", "13B"));

        mTimings.put("14A", new TimetableItem(Calendar.TUESDAY, 17, 05, 18, 30, "14", "14A"));
        mTimings.put("14B", new TimetableItem(Calendar.FRIDAY, 17, 05, 18, 30, "14", "14B"));

        mTimings.put("15A", new TimetableItem(Calendar.TUESDAY, 18, 35, 20, 00, "15", "15A"));
        mTimings.put("15B", new TimetableItem(Calendar.FRIDAY, 18, 35, 20, 00, "15", "15B"));

        mTimings.put("X1", new TimetableItem(Calendar.WEDNESDAY, 14, 00, 14, 55, "X1", "X1"));
        mTimings.put("X2", new TimetableItem(Calendar.WEDNESDAY, 15, 00, 15, 55, "X2", "X2"));
        mTimings.put("X3", new TimetableItem(Calendar.WEDNESDAY, 16, 00, 16, 55, "X3", "X3"));
        mTimings.put("XC", new TimetableItem(Calendar.WEDNESDAY, 17, 05, 18, 30, "XC", "XC"));
        mTimings.put("XD", new TimetableItem(Calendar.WEDNESDAY, 18, 35, 20, 00, "XD", "XD"));

        mTimings.put("L1", new TimetableItem(Calendar.MONDAY, 14, 00, 16, 55, "L1", "L1"));
        mTimings.put("L2", new TimetableItem(Calendar.TUESDAY, 14, 00, 16, 55, "L2", "L2"));
        mTimings.put("LX", new TimetableItem(Calendar.WEDNESDAY, 14, 00, 16, 55, "LX", "LX"));
        mTimings.put("L3", new TimetableItem(Calendar.THURSDAY, 14, 00, 16, 55, "L3", "L3"));
        mTimings.put("L4", new TimetableItem(Calendar.FRIDAY, 14, 00, 16, 55, "L4", "L4"));
    }

    public void setupSlotSets() {
        mSlotSets.put("1", new String[]{"1A", "1B", "1C"});
        mSlotSets.put("2", new String[]{"2A", "2B", "2C"});
        mSlotSets.put("3", new String[]{"3A", "3B", "3C"});
        mSlotSets.put("4", new String[]{"4A", "4B", "4C"});
        mSlotSets.put("5", new String[]{"5A", "5B"});
        mSlotSets.put("6", new String[]{"6A", "6B"});
        mSlotSets.put("7", new String[]{"7A", "7B"});
        mSlotSets.put("8", new String[]{"8A", "8B"});
        mSlotSets.put("9", new String[]{"9A", "9B"});
        mSlotSets.put("10", new String[]{"10A", "10B"});
        mSlotSets.put("11", new String[]{"11A", "11B"});
        mSlotSets.put("12", new String[]{"12A", "12B"});
        mSlotSets.put("13", new String[]{"13A", "13B"});
        mSlotSets.put("14", new String[]{"14A", "14B"});
        mSlotSets.put("15", new String[]{"15A", "15B"});
        mSlotSets.put("X1", new String[]{"X1"});
        mSlotSets.put("X2", new String[]{"X2"});
        mSlotSets.put("X3", new String[]{"X3"});
        mSlotSets.put("XC", new String[]{"XC"});
        mSlotSets.put("XD", new String[]{"XD"});
        mSlotSets.put("L1", new String[]{"L1"});
        mSlotSets.put("L2", new String[]{"L2"});
        mSlotSets.put("LX", new String[]{"LX"});
        mSlotSets.put("L3", new String[]{"L3"});
        mSlotSets.put("L4", new String[]{"L4"});
    }

    public TimetableItem findNextClosestCourse(int day, int hr, int min) {

        TimetableItem bestItem = null;
        for (TimetableItem item : mTimings.values()) {
            if (item.mDay == day && item.relativeTimePosition(hr, min) != 1) {
                if (bestItem == null || bestItem.mStartHr > item.mStartHr || (bestItem.mStartHr
                        == item.mStartHr && bestItem.mStartMin >= item.mStartMin)) {
                    bestItem = item;
                }
            }
        }

        return bestItem;
    }

    public TimetableItem findNextSlot() {
        Calendar now = Calendar.getInstance();
        return findNextClosestCourse(now.get(Calendar.DAY_OF_WEEK), now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE));
    }

    public TimetableItem findNextCourse(Map<String, CourseDataItem> data) {
        Calendar now = Calendar.getInstance();

        int day = now.get(Calendar.DAY_OF_WEEK);
        int hr = now.get(Calendar.HOUR_OF_DAY);
        int min = now.get(Calendar.MINUTE);

        TimetableItem bestItem = null;
        for (TimetableItem item : mTimings.values()) {
            if (item.mDay == day && item.relativeTimePosition(hr, min) != 1
                    && data.containsKey(item.mParentSlot)) {
                if (bestItem == null || bestItem.mStartHr > item.mStartHr || (bestItem.mStartHr
                        == item.mStartHr && bestItem.mStartMin >= item.mStartMin)) {
                    bestItem = item;
                }
            }
        }

        return bestItem;
    }

    public class TimetableItem {
        public String mParentSlot, mSlot;
        public int mDay;
        public int mStartHr, mStartMin;
        public int mEndHr, mEndMin;

        public TimetableItem(int mDay, int mStartHr, int mStartMin, int mEndHr, int mEndMin,
                             String mParentSlot, String mSlot) {
            this.mDay = mDay;
            this.mStartHr = mStartHr;
            this.mStartMin = mStartMin;
            this.mEndHr = mEndHr;
            this.mEndMin = mEndMin;
            this.mParentSlot = mParentSlot;
            this.mSlot = mSlot;
        }

        public String getTime(int hr, int min) {
            String suffix = "am";
            if (hr >= 12) {
                suffix = "pm";
            }
            if (hr > 12) {
                hr -= 12;
            }

            return hr + ":" + min + suffix;
        }

        public String getRange() {
            return getTime(mStartHr, mStartMin) + " - " + getTime(mEndHr, mEndMin);
        }

        public String getDay() {
            switch (mDay) {
                case Calendar.MONDAY:
                    return "Monday";
                case Calendar.TUESDAY:
                    return "Tuesday";
                case Calendar.WEDNESDAY:
                    return "Wednesday";
                case Calendar.THURSDAY:
                    return "Thursday";
                case Calendar.FRIDAY:
                    return "Friday";
                case Calendar.SATURDAY:
                    return "Saturday";
                case Calendar.SUNDAY:
                    return "Sunday";
                default:
                    return "";
            }
        }

        public int relativeTimePosition(int hr, int min) {
            if (hr < mStartHr || (hr == mStartHr && min <= mStartMin)) {
                return -1;
            } else if (hr < mEndHr || (hr == mEndHr && min <= mEndMin)) {
                return 0;
            }
            return 1;
        }
    }
}
