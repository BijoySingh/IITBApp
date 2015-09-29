package com.gymkhana.iitbapp.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.lvadapter.LVAdapterFeeds;
import com.gymkhana.iitbapp.util.ApiUtil;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.LocalData;
import com.gymkhana.iitbapp.util.ServerUrls;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class FeedSubscriptionActivity extends ActionBarActivity {

    public ListView mListView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);
        mContext = this;

        String link = ServerUrls.getInstance().FEEDS;
        String fileName = Constants.Filenames.INFO_FEED;
        Integer dataType = Constants.DATA_TYPE_FEED_INFO;
        mListView = (ListView) findViewById(R.id.list);

        Functions.setActionBar(this);
        Functions.setActionBarTitle(this, mContext.getString(R.string.title_subscription));
        getSupportActionBar().setHomeButtonEnabled(true);

        boolean mFileExists = false;

        if (fileName != null) {
            String json = Functions.offlineDataReader(mContext, fileName);
            if (json != null && !json.isEmpty()) {
                mFileExists = true;
                ApiUtil.onSubscriptionDataResult(json, mContext, mListView);
            }
        }

        ApiUtil.makeApiCall(
                link,
                mContext,
                dataType,
                mListView,
                null,
                fileName,
                mFileExists
        );
    }

    public void uploadSubscriptions(final Context context) {

        Map<String, Object> map = new HashMap<>();
        List<Integer> subscribed = ((LVAdapterFeeds) mListView.getAdapter()).getSubscriptions();
        map.put(Constants.JsonKeys.CATEGORIES, subscribed);

        JSONObject params = new JSONObject(map);
        String url = ServerUrls.getInstance().FEED_SUBSCRIBE;

        Log.d(FeedSubscriptionActivity.class.getSimpleName(), "Updating Subscriptions => " + url + " => " + params.toString());

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, url, params, new
                        Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject result) {
                                try {
                                    if (result.has("count")) {
                                        return;
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Functions.makeToast(context, "Could Not Update Subscriptions");
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Functions.makeToast(context, "Could Not Update Subscriptions");
                        Log.d(FeedSubscriptionActivity.class.getName(), Functions.showVolleyError(error));
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    @Override
    protected void onStop() {
        uploadSubscriptions(mContext);
        super.onStop();
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
