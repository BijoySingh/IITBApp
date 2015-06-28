package com.iitblive.iitblive.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.internet.DownloadJson;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.DownloadJsonUtil;
import com.iitblive.iitblive.util.Functions;

@SuppressLint("NewApi")
public class GenericInformationListActivity extends ActionBarActivity {

  private Context mContext;
  public static String mLink;
  public static String mFileName = null;
  public static Integer mIconResource;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listview_layout);
    mContext = this;

    Functions.setActionBar(this);
    getSupportActionBar().setHomeButtonEnabled(true);

    ListView listView = (ListView) findViewById(R.id.list);

    if (mFileName != null) {
      String json = Functions.offlineDataReader(mContext, mFileName);
      if (json == null || json.isEmpty()) {
        DownloadJsonUtil.onGetInformationResult(json, mContext, mIconResource, listView);
      }
    }

    DownloadJson downloadJson = new DownloadJson(
        mLink,
        mContext,
        Constants.DATA_TYPE_INFORMATION,
        Constants.DATA_COUNT_MULTIPLE,
        listView
    );
    downloadJson.mIconResource = mIconResource;
    downloadJson.setStorageFile(mFileName);
    downloadJson.execute();
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
