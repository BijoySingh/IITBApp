package com.iitblive.iitblive.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.database.CourseDBHandler;
import com.iitblive.iitblive.items.CourseDataItem;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.util.Functions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@SuppressLint("NewApi")
public class IitbTimetableActivity extends ActionBarActivity {

  public static EventListViewItem mArticle;
  private Context mContext;

  private Map<String, CourseDataItem> mData = new HashMap<>();
  private Map<String, LinearLayout> mLayouts = new HashMap<>();
  private Map<String, String[]> mMappings = new HashMap<>();
  private Map<String, String[]> mTimings = new HashMap<>();
  private CourseDBHandler mCourseDBHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.iitb_timetable_layout);
    mContext = this;
    mCourseDBHandler = new CourseDBHandler(mContext);

    setupData();
    setupMappings();
    setupView();
    setupTimings();
    setupItems();

    Functions.setActionBar(this);
    Functions.setActionBarTitle(this, getString(R.string.title_timetable));
  }

  private void setupMappings() {
    String[] slot1 = new String[]{"1", "1A", "1B", "1C"};
    mMappings.put("1A", slot1);
    mMappings.put("1B", slot1);
    mMappings.put("1C", slot1);

    String[] slot2 = new String[]{"2", "2A", "2B", "2C"};
    mMappings.put("2A", slot2);
    mMappings.put("2B", slot2);
    mMappings.put("2C", slot2);

    String[] slot3 = new String[]{"3", "3A", "3B", "3C"};
    mMappings.put("3A", slot3);
    mMappings.put("3B", slot3);
    mMappings.put("3C", slot3);

    String[] slot4 = new String[]{"4", "4A", "4B", "4C"};
    mMappings.put("4A", slot4);
    mMappings.put("4B", slot4);
    mMappings.put("4C", slot4);

    String[] slot5 = new String[]{"5", "5A", "5B"};
    mMappings.put("5A", slot5);
    mMappings.put("5B", slot5);

    String[] slot6 = new String[]{"6", "6A", "6B"};
    mMappings.put("6A", slot6);
    mMappings.put("6B", slot6);

    String[] slot7 = new String[]{"7", "7A", "7B"};
    mMappings.put("7A", slot7);
    mMappings.put("7B", slot7);

    String[] slot8 = new String[]{"8", "8A", "8B"};
    mMappings.put("8A", slot8);
    mMappings.put("8B", slot8);

    String[] slot9 = new String[]{"9", "9A", "9B"};
    mMappings.put("9A", slot9);
    mMappings.put("9B", slot9);

    String[] slot10 = new String[]{"10", "10A", "10B"};
    mMappings.put("10A", slot10);
    mMappings.put("10B", slot10);

    String[] slot11 = new String[]{"11", "11A", "11B"};
    mMappings.put("11A", slot11);
    mMappings.put("11B", slot11);

    String[] slot12 = new String[]{"12", "12A", "12B"};
    mMappings.put("12A", slot12);
    mMappings.put("12B", slot12);

    String[] slot13 = new String[]{"13", "13A", "13B"};
    mMappings.put("13A", slot13);
    mMappings.put("13B", slot13);

    String[] slot14 = new String[]{"14", "14A", "14B"};
    mMappings.put("14A", slot14);
    mMappings.put("14B", slot14);

    String[] slot15 = new String[]{"15", "15A", "15B"};
    mMappings.put("15A", slot15);
    mMappings.put("15B", slot15);

    mMappings.put("X1", new String[]{"X1", "X1"});
    mMappings.put("X2", new String[]{"X2", "X2"});
    mMappings.put("X3", new String[]{"X3", "X3"});
    mMappings.put("XC", new String[]{"XC", "XC"});
    mMappings.put("XD", new String[]{"XD", "XD"});

    mMappings.put("L1", new String[]{"L1", "L1"});
    mMappings.put("L2", new String[]{"L2", "L2"});
    mMappings.put("LX", new String[]{"LX", "LX"});
    mMappings.put("L3", new String[]{"L3", "L3"});
    mMappings.put("L4", new String[]{"L4", "L4"});

  }

  private void setupTimings() {
    mTimings.put("1A", new String[]{"Monday", "8:30am - 9:25am"});
    mTimings.put("1B", new String[]{"Tuesday", "9:30am - 10:25am"});
    mTimings.put("1C", new String[]{"Thursday", "10:35am - 11:30am"});

    mTimings.put("2A", new String[]{"Monday", "9:30am - 10:25am"});
    mTimings.put("2B", new String[]{"Tuesday", "10:35am - 11:30am"});
    mTimings.put("2C", new String[]{"Thursday", "11:35am - 12:30pm"});

    mTimings.put("3A", new String[]{"Monday", "10:35am - 11:30am"});
    mTimings.put("3B", new String[]{"Tuesday", "11:35am - 12:30pm"});
    mTimings.put("3C", new String[]{"Thursday", "8:30am - 9:25am"});

    mTimings.put("4A", new String[]{"Monday", "11:35am - 12:30pm"});
    mTimings.put("4B", new String[]{"Tuesday", "8:30am - 9:25am"});
    mTimings.put("4C", new String[]{"Thursday", "9:30am - 10:25am"});

    mTimings.put("5A", new String[]{"Wednesday", "9:30am - 10:55am"});
    mTimings.put("5B", new String[]{"Friday", "9:30am - 10:55am"});

    mTimings.put("6A", new String[]{"Wednesday", "11:05am - 12:30pm"});
    mTimings.put("6B", new String[]{"Friday", "11:05am - 12:30pm"});

    mTimings.put("7A", new String[]{"Wednesday", "8:30am - 9:25am"});
    mTimings.put("7B", new String[]{"Friday", "8:30am - 9:25am"});

    mTimings.put("8A", new String[]{"Monday", "2:00pm - 3:25pm"});
    mTimings.put("8B", new String[]{"Thursday", "2:00pm - 3:25pm"});

    mTimings.put("9A", new String[]{"Monday", "3:30pm - 4:55pm"});
    mTimings.put("9B", new String[]{"Thursday", "3:30pm - 4:55pm"});

    mTimings.put("10A", new String[]{"Tuesday", "2:00pm - 3:25pm"});
    mTimings.put("10B", new String[]{"Friday", "2:00pm - 3:25pm"});

    mTimings.put("11A", new String[]{"Tuesday", "3:30pm - 4:55pm"});
    mTimings.put("11B", new String[]{"Friday", "3:30pm - 4:55pm"});

    mTimings.put("12A", new String[]{"Monday", "5:05pm - 6:30pm"});
    mTimings.put("12B", new String[]{"Thursday", "5:05pm - 6:30pm"});

    mTimings.put("13A", new String[]{"Monday", "6:35pm - 8:00pm"});
    mTimings.put("13B", new String[]{"Thursday", "6:35pm - 8:00pm"});

    mTimings.put("14A", new String[]{"Tuesday", "5:05pm - 6:30pm"});
    mTimings.put("14B", new String[]{"Friday", "5:05pm - 6:30pm"});

    mTimings.put("15A", new String[]{"Tuesday", "6:35pm - 8:00pm"});
    mTimings.put("15B", new String[]{"Friday", "6:35pm - 8:00pm"});

    mTimings.put("X1", new String[]{"Wednesday", "2:00pm - 2:5pm"});
    mTimings.put("X2", new String[]{"Wednesday", "3:00pm - 3:55pm"});
    mTimings.put("X3", new String[]{"Wednesday", "4:00pm - 4:55pm"});
    mTimings.put("XC", new String[]{"Wednesday", "5:05pm - 6:30pm"});
    mTimings.put("XD", new String[]{"Wednesday", "6:35pm - 8:00pm"});

    mTimings.put("L1", new String[]{"Monday", "2:00pm - 4:55pm"});
    mTimings.put("L2", new String[]{"Tuesday", "2:00pm - 4:55pm"});
    mTimings.put("LX", new String[]{"Wednesday", "2:00pm - 4:55pm"});
    mTimings.put("L3", new String[]{"Thursday", "2:00pm - 4:55pm"});
    mTimings.put("L4", new String[]{"Friday", "2:00pm - 4:55pm"});
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

  }

  private void setupItem(final String tag) {
    LinearLayout linearLayout = mLayouts.get(tag);

    final String mDataKey = mMappings.get(tag)[0];
    LinearLayout unitLayout = (LinearLayout) linearLayout.findViewById(R.id.unit_layout);
    TextView titleView = (TextView) linearLayout.findViewById(R.id.unit_title);
    TextView descriptionView = (TextView) linearLayout.findViewById(R.id.unit_description);
    final boolean isCourse = mData.containsKey(mDataKey);

    if (isCourse) {
      titleView.setText(mData.get(mDataKey).mName);
      descriptionView.setText(mData.get(mDataKey).mLocation);
      unitLayout.setBackgroundColor(getResources().getColor(R.color.timetable_selected));
      titleView.setTextColor(getResources().getColor(R.color.secondary_text));
    } else {
      titleView.setText(tag);
    }

    linearLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isCourse) {
          Functions.createMaterialDialog(
              mContext,
              mData.get(mDataKey).mName,
              mData.get(mDataKey).mLocation,
              mData.get(mDataKey).mSlotTag,
              getString(R.string.remove),
              new Callable<Void>() {
                public Void call() {
                  mCourseDBHandler.deleteItem(mData.get(mDataKey).mSlotTag);
                  Functions.makeToast(mContext, R.string.toast_item_removed);
                  setupItems();
                  return null;
                }
              }
          ).show();
        } else {
          Functions.createMaterialDialog(
              mContext,
              mTimings.get(tag)[0],
              mTimings.get(tag)[1],
              getString(R.string.slot) + " " + mDataKey,
              getString(R.string.set),
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

  private void setupData() {
    mData = mCourseDBHandler.getCourseData();
  }

  private void setupItems() {
    for (Map.Entry entry : mLayouts.entrySet()) {
      String key = (String) entry.getKey();
      LinearLayout linearLayout = (LinearLayout) entry.getValue();
      setupItem(key);
    }
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
          mCourseDBHandler.deleteItem(tag);
          mData.remove(tag);
          setupItems();
          dialog.dismiss();
        }
      });
      done.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          CourseDataItem courseDataItem =
              mCourseDBHandler.updateCourse(name.getText().toString(), location.getText().toString(), tag);

          if (courseDataItem != null) {
            mData.put(courseDataItem.mSlotTag, courseDataItem);
            setupItems();
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
              mCourseDBHandler.insertCourse(name.getText().toString(), location.getText().toString(), tag);

          if (courseDataItem != null) {
            mData.put(courseDataItem.mSlotTag, courseDataItem);
            setupItems();
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
