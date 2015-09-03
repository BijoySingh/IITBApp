package com.gymkhana.iitbapp.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.MainActivity;
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
public class FeedSubscriptionFragment extends Fragment {

    public String mTitle;
    public AdapterView.OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private MainActivity mActivity;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.listview_layout, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list);
        mListView.setOnItemClickListener(mOnItemClickListener);

        String link = ServerUrls.getInstance().FEEDS;
        String fileName = Constants.Filenames.INFO_FEED;
        Integer dataType = Constants.DATA_TYPE_FEED_INFO;
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

        Functions.setActionBarTitle(mActivity, mContext.getString(R.string.title_subscription));
        return rootView;
    }

    public void uploadSubscriptions(final Context context) {

        Map<String, Object> map = new HashMap<>();
        List<Integer> subscribed = ((LVAdapterFeeds) mListView.getAdapter()).getSubscriptions();
        map.put(Constants.JsonKeys.CATEGORIES, subscribed);

        JSONObject params = new JSONObject(map);
        String url = ServerUrls.getInstance().FEED_SUBSCRIBE;

        Log.d(FeedSubscriptionFragment.class.getSimpleName(), "Updating Subscriptions => " + url + " => " + params.toString());

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
                        Log.d(FeedSubscriptionFragment.class.getSimpleName(), Functions.showVolleyError(error));
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        mActivity = (MainActivity) activity;
    }

    @Override
    public void onStop() {
        uploadSubscriptions(mContext);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
