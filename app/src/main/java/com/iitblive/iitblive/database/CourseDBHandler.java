package com.iitblive.iitblive.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iitblive.iitblive.items.CourseDataItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bijoy on 2/7/2015.
 * database for questions download
 */
public class CourseDBHandler extends SQLiteOpenHelper {

  // All Static variables
  // Database Version
  private static final int DATABASE_VERSION = 1;
  // Database Name
  private static final String DATABASE_NAME = "courseDatabase";
  // Contacts table name
  private static final String TABLE_NAME = "course_table";

  // Contacts Table Columns names
  private static final String KEY_ID = "id";
  private static final String KEY_COURSE = "course";
  private static final String KEY_LOCATION = "location";
  private static final String KEY_SLOT_TAG = "slot_tag";

  private Context context;

  public String[] myList = new String[]{KEY_ID,
      KEY_COURSE, KEY_LOCATION, KEY_SLOT_TAG};

  public CourseDBHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }


  @Override
  public void onCreate(SQLiteDatabase db) {
    String CREATE_TABLE =
        "CREATE TABLE " + TABLE_NAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_SLOT_TAG + " TEXT,"
            + KEY_LOCATION + " TEXT,"
            + KEY_COURSE + " TEXT"
            + ");";
    db.execSQL(CREATE_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    onCreate(db);
  }

  ;

  public ContentValues getContentValues(String name, String location, String slot) {
    ContentValues values = new ContentValues();
    values.put(KEY_COURSE, name);
    values.put(KEY_LOCATION, location);
    values.put(KEY_SLOT_TAG, slot);

    return values;
  }

  //updates course if already exists
  public CourseDataItem updateCourse(String name, String location, String slot) {
    try {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = getContentValues(name, location, slot);
      db.update(TABLE_NAME, values, KEY_SLOT_TAG + " = ?", new String[]{slot});
      db.close();
      return new CourseDataItem(slot, name, location);
    } catch (Exception e) {
    }
    return null;
  }

  //insert course
  public CourseDataItem insertCourse(String name, String location, String slot) {
    try {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues values = getContentValues(name, location, slot);
      db.insert(TABLE_NAME, null, values);
      db.close();
      return new CourseDataItem(slot, name, location);
    } catch (Exception e) {
    }
    return null;
  }

  public CourseDataItem getCourseData(String tag) {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.rawQuery(
        "SELECT " + KEY_SLOT_TAG + ", " + KEY_COURSE + ", " + KEY_LOCATION +
            " FROM " + TABLE_NAME +
            " WHERE " + KEY_SLOT_TAG + " = '" + tag + "'", null);

    CourseDataItem courseDataItem = null;
    if (cursor != null) {
      if (cursor.moveToNext()) {
        courseDataItem = new CourseDataItem(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2)
        );
      }
    }
    return courseDataItem;
  }

  public Map<String, CourseDataItem> getCourseData() {
    SQLiteDatabase db = this.getReadableDatabase();

    Cursor cursor = db.rawQuery(
        "SELECT " + KEY_SLOT_TAG + ", " + KEY_COURSE + ", " + KEY_LOCATION +
            " FROM " + TABLE_NAME, null);

    Map<String, CourseDataItem> mCourseData = new HashMap<>();
    if (cursor != null) {
      while (cursor.moveToNext()) {
        CourseDataItem courseDataItem = new CourseDataItem(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2)
        );
        mCourseData.put(cursor.getString(0), courseDataItem);
      }
    }
    return mCourseData;
  }

  //delete
  public void deleteItem(String slot) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, KEY_SLOT_TAG + " = ?", new String[]{slot});
    db.close();
  }

  public void deleteDatabase() {
    SQLiteDatabase db = this.getWritableDatabase();
    db.delete(TABLE_NAME, null, null);
    db.close();
  }
}
