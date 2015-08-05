package com.gymkhana.iitbapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.feed.RSSFeedItem;
import com.gymkhana.iitbapp.items.TimestampItem;
import com.gymkhana.iitbapp.util.CategoryImages;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.ServerUrls;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bijoy on 8/4/15.
 */
public class RSSViewerActivity extends ActionBarActivity {

    public static String INTENT_RSS = "RSS";
    private RSSFeedItem mRSSItem;
    private Context mContext;
    private RSSViewHolder mViewHolder = new RSSViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);

        mRSSItem = (RSSFeedItem) getIntent().getSerializableExtra(INTENT_RSS);

        mContext = this;

        mViewHolder.title = (TextView) findViewById(R.id.title);
        mViewHolder.description = (TextView) findViewById(R.id.description);
        mViewHolder.sourceName = (TextView) findViewById(R.id.source);
        mViewHolder.sourceDesignation = (TextView) findViewById(R.id.source_designation);
        mViewHolder.likes = (TextView) findViewById(R.id.likes);
        mViewHolder.views = (TextView) findViewById(R.id.views);
        mViewHolder.scrollLayout = (LinearLayout) findViewById(R.id.image_scroll_view);
        mViewHolder.categoryImage = (FloatingActionButton) findViewById(R.id.category_logo);
        mViewHolder.addToCalendar = (ImageView) findViewById(R.id.add_event_logo);
        mViewHolder.likeIcon = (ImageView) findViewById(R.id.like_logo);
        mViewHolder.viewIcon = (ImageView) findViewById(R.id.view_logo);
        mViewHolder.eventTime = (TextView) findViewById(R.id.event_time);
        mViewHolder.eventDate = (TextView) findViewById(R.id.event_date);
        mViewHolder.eventLocation = (TextView) findViewById(R.id.event_location);
        mViewHolder.articleTime = (TextView) findViewById(R.id.article_time);
        mViewHolder.articleDate = (TextView) findViewById(R.id.article_date);
        mViewHolder.eventLayout = (RelativeLayout) findViewById(R.id.event_layout);
        mViewHolder.likePanel = (RelativeLayout) findViewById(R.id.panel);
        mViewHolder.horizontalScrollView =
                (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

        mViewHolder.title.setText(Html.fromHtml(mRSSItem.title).toString());
        mViewHolder.description.setText(Html.fromHtml(mRSSItem.description));
        mViewHolder.categoryImage.setIcon(
                mContext.getResources().getDrawable(CategoryImages.getDrawable("feed")),
                false
        );
        mViewHolder.categoryImage.setBackgroundColor(mRSSItem.getAccentColor());

        mViewHolder.scrollLayout.setVisibility(View.GONE);
        mViewHolder.horizontalScrollView.setVisibility(View.GONE);
        mViewHolder.title.setBackgroundColor(mRSSItem.getNavigationColor());

        mViewHolder.addToCalendar.setVisibility(View.GONE);
        mViewHolder.likePanel.setVisibility(View.GONE);

        TimestampItem timestampItem = new TimestampItem(mContext, mRSSItem.publishDate);

        mViewHolder.sourceName.setText(mRSSItem.name);
        mViewHolder.articleDate.setText(timestampItem.date);
        mViewHolder.articleTime.setText(timestampItem.time);
        mViewHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRSSItem.liked) {
                    unlikeArticle();
                } else {
                    likeArticle();
                }
            }
        });

        setupLike();
        if (mRSSItem.viewed)
            mViewHolder.viewIcon.setColorFilter(mRSSItem.getAccentColor());
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
        viewArticle();
    }

    public void hideNavigation() {
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
        );
    }

    private void setupLike() {
        if (mRSSItem.liked) {
            mViewHolder.likeIcon.setColorFilter(mRSSItem.getAccentColor());
        } else {
            mViewHolder.likeIcon.clearColorFilter();
        }
    }

    private void interpretJSONResponse(JSONObject jsonObject) {
        try {
            mViewHolder.likePanel.setVisibility(View.VISIBLE);
            mRSSItem.liked = jsonObject.getBoolean(Constants.Article.RESPONSE_LIKED);
            mRSSItem.viewed = jsonObject.getBoolean(Constants.Article.RESPONSE_VIEWED);
            mRSSItem.likes = jsonObject.getInt(Constants.Article.RESPONSE_LIKES);
            mRSSItem.views = jsonObject.getInt(Constants.Article.RESPONSE_VIEWS);

            setupLike();
            mViewHolder.likes.setText(mRSSItem.likes.toString());
            mViewHolder.views.setText(mRSSItem.views.toString());
            if (mRSSItem.viewed)
                mViewHolder.viewIcon.setColorFilter(mRSSItem.getAccentColor());

        } catch (Exception e) {
        }
    }

    private void apiAction(final Context context, String url, JSONObject jsonParams, final int actionType) {
        if (url == null || jsonParams == null) {
            return;
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonParams, new
                        Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject result) {
                                try {
                                    if (result.has("views")) {
                                        interpretJSONResponse(result);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LoginActivity.enableLogin();
                        error.printStackTrace();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("token-auth", SharedPreferenceManager.load(context, SharedPreferenceManager.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private JSONObject getActionParams() {
        try {
            JSONObject params = new JSONObject();
            params.put(Constants.Article.REQUEST_USER,
                    SharedPreferenceManager.load(mContext, SharedPreferenceManager.Tags.USER_ID));
            params.put(Constants.Article.REQUEST_GUID, mRSSItem.guid);
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void likeArticle() {
        apiAction(mContext, ServerUrls.getInstance().FEED + Constants.Article.ACTION_URL_LIKE,
                getActionParams(), Constants.Article.ACTION_LIKE);
    }

    private void unlikeArticle() {
        apiAction(mContext, ServerUrls.getInstance().FEED + Constants.Article.ACTION_URL_UNLIKE,
                getActionParams(), Constants.Article.ACTION_UNLIKE);
    }

    private void viewArticle() {
        apiAction(mContext, ServerUrls.getInstance().FEED + Constants.Article.ACTION_URL_VIEW,
                getActionParams(), Constants.Article.ACTION_VIEW);
    }

    public class RSSViewHolder {
        FloatingActionButton categoryImage;
        ImageView addToCalendar, likeIcon, viewIcon;
        TextView title, description, sourceName, sourceDesignation, likes, views;
        TextView eventTime, eventDate, eventLocation;
        TextView articleTime, articleDate;
        RelativeLayout eventLayout, likePanel;
        LinearLayout scrollLayout;
        HorizontalScrollView horizontalScrollView;
    }

}
