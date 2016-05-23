package com.gymkhana.iitbapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.util.ApiUtil;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;

@SuppressLint("NewApi")
public class InformationActivity extends AppCompatActivity {

    public static String mTitle = "";
    public static String mLink;
    public static String mFileName = null;
    public static Integer mIconResource;
    private Context mContext;
    private boolean mFileExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        mContext = this;

        Functions.setActionBar(this);
        Functions.setActionBarTitle(this, mTitle);
        getSupportActionBar().setHomeButtonEnabled(true);

        ListView listView = (ListView) findViewById(R.id.list);
        mFileExists = false;

        if (mFileName != null) {
            String json = Functions.offlineDataReader(mContext, mFileName);
            if (!json.isEmpty()) {
                ApiUtil.onGetInformationResult(json, mContext, mIconResource, listView,
                        false);
                mFileExists = true;
            }
        }

        ApiUtil.makeApiCall(
                mLink,
                mContext,
                Constants.DATA_TYPE_INFORMATION,
                listView,
                mIconResource,
                mFileName,
                mFileExists
        );

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
