package com.gymkhana.iitbapp.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.database.TimetableDBHandler;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.CourseDataItem;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.IitbTimetableUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@SuppressLint("NewApi")
public class IitbTimetableActivity extends AppCompatActivity {

    private Context mContext;

    private Map<String, CourseDataItem> mData = new HashMap<>();
    private Map<String, LinearLayout> mLayouts = new HashMap<>();
    private TimetableDBHandler mTimetableDBHandler;
    private IitbTimetableUtil mIitbTimetableUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iitb_timetable_layout);
        mContext = this;
        mTimetableDBHandler = new TimetableDBHandler(mContext);
        mIitbTimetableUtil = IitbTimetableUtil.getInstance();

        setupView();
        resetUi();

        Functions.setActionBar(this);
        Functions.setActionBarTitle(this, getString(R.string.title_timetable));
    }

    private void setupView() {
        mLayouts.put("1A", (LinearLayout) findViewById(R.id.t1A));
        mLayouts.put("1B", (LinearLayout) findViewById(R.id.t1B));
        mLayouts.put("1C", (LinearLayout) findViewById(R.id.t1C));

        mLayouts.put("2A", (LinearLayout) findViewById(R.id.t2A));
        mLayouts.put("2B", (LinearLayout) findViewById(R.id.t2B));
        mLayouts.put("2C", (LinearLayout) findViewById(R.id.t2C));

        mLayouts.put("3A", (LinearLayout) findViewById(R.id.t3A));
        mLayouts.put("3B", (LinearLayout) findViewById(R.id.t3B));
        mLayouts.put("3C", (LinearLayout) findViewById(R.id.t3C));

        mLayouts.put("4A", (LinearLayout) findViewById(R.id.t4A));
        mLayouts.put("4B", (LinearLayout) findViewById(R.id.t4B));
        mLayouts.put("4C", (LinearLayout) findViewById(R.id.t4C));

        mLayouts.put("5A", (LinearLayout) findViewById(R.id.t5A));
        mLayouts.put("5B", (LinearLayout) findViewById(R.id.t5B));

        mLayouts.put("6A", (LinearLayout) findViewById(R.id.t6A));
        mLayouts.put("6B", (LinearLayout) findViewById(R.id.t6B));

        mLayouts.put("7A", (LinearLayout) findViewById(R.id.t7A));
        mLayouts.put("7B", (LinearLayout) findViewById(R.id.t7B));

        mLayouts.put("8A", (LinearLayout) findViewById(R.id.t8A));
        mLayouts.put("8B", (LinearLayout) findViewById(R.id.t8B));

        mLayouts.put("9A", (LinearLayout) findViewById(R.id.t9A));
        mLayouts.put("9B", (LinearLayout) findViewById(R.id.t9B));

        mLayouts.put("10A", (LinearLayout) findViewById(R.id.t10A));
        mLayouts.put("10B", (LinearLayout) findViewById(R.id.t10B));

        mLayouts.put("11A", (LinearLayout) findViewById(R.id.t11A));
        mLayouts.put("11B", (LinearLayout) findViewById(R.id.t11B));

        mLayouts.put("12A", (LinearLayout) findViewById(R.id.t12A));
        mLayouts.put("12B", (LinearLayout) findViewById(R.id.t12B));

        mLayouts.put("13A", (LinearLayout) findViewById(R.id.t13A));
        mLayouts.put("13B", (LinearLayout) findViewById(R.id.t13B));

        mLayouts.put("14A", (LinearLayout) findViewById(R.id.t14A));
        mLayouts.put("14B", (LinearLayout) findViewById(R.id.t14B));

        mLayouts.put("15A", (LinearLayout) findViewById(R.id.t15A));
        mLayouts.put("15B", (LinearLayout) findViewById(R.id.t15B));

        mLayouts.put("X1", (LinearLayout) findViewById(R.id.tX1));
        mLayouts.put("X2", (LinearLayout) findViewById(R.id.tX2));
        mLayouts.put("X3", (LinearLayout) findViewById(R.id.tX3));
        mLayouts.put("XC", (LinearLayout) findViewById(R.id.tXC));
        mLayouts.put("XD", (LinearLayout) findViewById(R.id.tXD));

        mLayouts.put("L1", (LinearLayout) findViewById(R.id.tL1));
        mLayouts.put("L2", (LinearLayout) findViewById(R.id.tL2));
        mLayouts.put("LX", (LinearLayout) findViewById(R.id.tLX));
        mLayouts.put("L3", (LinearLayout) findViewById(R.id.tL3));
        mLayouts.put("L4", (LinearLayout) findViewById(R.id.tL4));

        mLayouts.put("MondaySet", (LinearLayout) findViewById(R.id.set_monday));
        mLayouts.put("TuesdaySet", (LinearLayout) findViewById(R.id.set_tuesday));
        mLayouts.put("WednesdaySet", (LinearLayout) findViewById(R.id.set_wednesday));
        mLayouts.put("ThursdaySet", (LinearLayout) findViewById(R.id.set_thursday));
        mLayouts.put("FridaySet", (LinearLayout) findViewById(R.id.set_friday));
    }

    private void highlightItem(final String tag) {
        LinearLayout linearLayout = mLayouts.get(tag);
        LinearLayout unitLayout = (LinearLayout) linearLayout.findViewById(R.id.unit_layout);
        unitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.timetable_highlighted));
    }

    private void setupItem(final String tag) {
        if (!mIitbTimetableUtil.mTimings.containsKey(tag))
            return;

        LinearLayout linearLayout = mLayouts.get(tag);

        final String mDataKey = mIitbTimetableUtil.mTimings.get(tag).mParentSlot;
        LinearLayout unitLayout = (LinearLayout) linearLayout.findViewById(R.id.unit_layout);
        TextView titleView = (TextView) linearLayout.findViewById(R.id.unit_title);
        TextView descriptionView = (TextView) linearLayout.findViewById(R.id.unit_description);
        final boolean isCourse = mData.containsKey(mDataKey);

        if (isCourse) {
            linearLayout.setVisibility(View.VISIBLE);
            titleView.setText(mData.get(mDataKey).mName);
            descriptionView.setText(mData.get(mDataKey).mLocation);
            unitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.timetable_selected));
            titleView.setTextColor(ContextCompat.getColor(this, R.color.secondary_text));
            increaseVisibility(mDataKey);
        } else {
            unitLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            titleView.setText(tag);
            titleView.setTextColor(ContextCompat.getColor(this, R.color.hint_text));
            descriptionView.setText("");
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCourse) {
                    Functions.createMaterialDialog(
                        mContext,
                        mData.get(mDataKey).mName,
                        mData.get(mDataKey).mLocation,
                        getString(R.string.slot) + " " + mData.get(mDataKey).mSlotTag,
                        getString(R.string.remove),
                        R.drawable.ic_today_white_48dp,
                        new Callable<Void>() {
                            public Void call() {
                                mTimetableDBHandler.deleteItem(mData.get(mDataKey).mSlotTag);
                                Functions.makeToast(mContext, R.string.toast_item_removed);
                                resetUi();
                                return null;
                            }
                        }
                    ).show();
                } else {
                    Functions.createMaterialDialog(
                        mContext,
                        mIitbTimetableUtil.mTimings.get(tag).getDay(),
                        mIitbTimetableUtil.mTimings.get(tag).getRange(),
                        getString(R.string.slot) + " " + mDataKey,
                        getString(R.string.set),
                        R.drawable.ic_today_white_48dp,
                        new Callable<Void>() {
                            public Void call() {
                                createCourseDialog(mDataKey);
                                return null;
                            }
                        }
                    ).show();
                }
            }
        });

        linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createCourseDialog(mDataKey);
                return false;
            }
        });
    }

    private void increaseVisibility(String parentSlot) {
        if (parentSlot.contentEquals("8") || parentSlot.contentEquals("9")) {
            mLayouts.get("L1").setVisibility(View.GONE);
            mLayouts.get("L3").setVisibility(View.GONE);
        } else if (parentSlot.contentEquals("L1") || parentSlot.contentEquals("L3")) {
            mLayouts.get("MondaySet").setVisibility(View.GONE);
            mLayouts.get("ThursdaySet").setVisibility(View.GONE);
        } else if (parentSlot.contentEquals("10") || parentSlot.contentEquals("11")) {
            mLayouts.get("L2").setVisibility(View.GONE);
            mLayouts.get("L4").setVisibility(View.GONE);
        } else if (parentSlot.contentEquals("L2") || parentSlot.contentEquals("L4")) {
            mLayouts.get("TuesdaySet").setVisibility(View.GONE);
            mLayouts.get("FridaySet").setVisibility(View.GONE);
        } else if (parentSlot.contentEquals("X1") ||
            parentSlot.contentEquals("X2") ||
            parentSlot.contentEquals("X3")) {
            mLayouts.get("LX").setVisibility(View.GONE);
        } else if (parentSlot.contentEquals("LX")) {
            mLayouts.get("WednesdaySet").setVisibility(View.GONE);
        }
    }

    private void setupData() {
        mData = mTimetableDBHandler.getCourseData();
    }

    private void setupItems() {
        for (Map.Entry entry : mLayouts.entrySet()) {
            String key = (String) entry.getKey();
            setupItem(key);
        }
    }

    private void resetUi() {

        for (Map.Entry entry : mLayouts.entrySet()) {
            ((LinearLayout) entry.getValue()).setVisibility(View.VISIBLE);
        }

        setupData();
        setupItems();

        IitbTimetableUtil.TimetableItem item = mIitbTimetableUtil.findNextSlot();
        if (item != null)
            highlightItem(item.mSlot);
    }

    private void createCourseDialog(final String tag) {
        final Dialog dialog = Functions.getTransparentDialog(
            mContext,
            R.layout.course_dialog_layout,
            Color.WHITE);
        final EditText name = (EditText) dialog.findViewById(R.id.course_name);
        final EditText location = (EditText) dialog.findViewById(R.id.course_location);

        TextView done = (TextView) dialog.findViewById(R.id.done);
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);

        if (mData.containsKey(tag)) {
            name.setText(mData.get(tag).mName);
            location.setText(mData.get(tag).mLocation);

            cancel.setText(getString(R.string.remove));
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTimetableDBHandler.deleteItem(tag);
                    mData.remove(tag);
                    resetUi();
                    dialog.dismiss();
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDataItem courseDataItem =
                        mTimetableDBHandler.updateCourse(name.getText().toString(), location.getText().toString(), tag);

                    if (courseDataItem != null) {
                        mData.put(courseDataItem.mSlotTag, courseDataItem);
                        resetUi();
                    }
                    dialog.dismiss();
                }
            });
        } else {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CourseDataItem courseDataItem =
                        mTimetableDBHandler.insertCourse(name.getText().toString(), location.getText().toString(), tag);

                    if (courseDataItem != null) {
                        mData.put(courseDataItem.mSlotTag, courseDataItem);
                        resetUi();
                    }
                    dialog.dismiss();
                }
            });
        }

        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
