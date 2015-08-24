package com.gymkhana.iitbapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
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
import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.TimestampItem;
import com.gymkhana.iitbapp.util.CategoryImages;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.util.Functions;
import com.gymkhana.iitbapp.util.LocalData;
import com.gymkhana.iitbapp.util.ServerUrls;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ArticleActivity extends ActionBarActivity {

    public static String INTENT_ARTICLE = "ARTICLE";
    private static String EVENT_TYPE = "vnd.android.cursor.item/event";
    private static String EVENT_BEGIN_TIME = "beginTime";
    private static String EVENT_END_TIME = "endTime";
    private ApiItem mArticle;
    private Context mContext;
    private EventViewHolder mViewHolder = new EventViewHolder();
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);

        mArticle = (ApiItem) getIntent().getSerializableExtra(INTENT_ARTICLE);

        mContext = this;
        mImageLoader = Functions.loadImageLoader(mContext);

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
        mViewHolder.eventLogo = (ImageView) findViewById(R.id.event_logo);

        mViewHolder.title.setText(mArticle.title);
        if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
            mViewHolder.description.setText(Html.fromHtml(mArticle.description));
        } else {
            mViewHolder.description.setText(mArticle.description);
        }
        mViewHolder.categoryImage.setIcon(
                mContext.getResources().getDrawable(CategoryImages.getDrawable(mArticle.category)),
                false
        );
        mViewHolder.categoryImage.setBackgroundColor(mArticle.getAccentColor());

        mViewHolder.likes.setText("" + mArticle.likes);
        mViewHolder.views.setText("" + mArticle.views);
        mViewHolder.sourceName.setText(mArticle.source_name);
        mViewHolder.sourceDesignation.setText(mArticle.source_designation);

        for (String link : mArticle.image_links) {
            ImageView imageView = new ImageView(mContext);
            imageView.setAdjustViewBounds(true);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    (int) Functions.convertDpToPixel(320, mContext)
            ));
            mImageLoader.displayImage(link, imageView);
            mViewHolder.scrollLayout.addView(imageView);
        }

        if (mArticle.image_links == null || mArticle.image_links.isEmpty()) {
            mViewHolder.scrollLayout.setVisibility(View.GONE);
            mViewHolder.horizontalScrollView.setVisibility(View.GONE);
            mViewHolder.title.setBackgroundColor(mArticle.getNavigationColor());
        }

        if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            mViewHolder.eventLayout.setVisibility(View.VISIBLE);
            mViewHolder.eventTime.setText(mArticle.event_time.time);
            mViewHolder.eventDate.setText(mArticle.event_time.date);
            mViewHolder.eventLocation.setText(mArticle.event_location);
            mViewHolder.addToCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCalendarEvent();
                }
            });
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            if (!mArticle.expiration_time.isNull) {
                mViewHolder.eventLayout.setVisibility(View.VISIBLE);
                mViewHolder.eventTime.setText(mArticle.expiration_time.time);
                mViewHolder.eventDate.setText(mArticle.expiration_time.date);
            }
            mViewHolder.addToCalendar.setVisibility(View.GONE);
            mViewHolder.likePanel.setVisibility(View.GONE);
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
            mViewHolder.eventLayout.setVisibility(View.VISIBLE);
            mViewHolder.eventLogo.setImageResource(R.drawable.feed_options_icon);
            mViewHolder.eventDate.setText(getString(R.string.feed_subscription));
            mViewHolder.eventTime.setText(getString(R.string.feed_subscription_details));
            mViewHolder.eventLocation.setText("");
            mViewHolder.addToCalendar.setImageResource(R.drawable.drawer_icon_feed);
            mViewHolder.addToCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainIntent = new Intent(mContext, MainActivity.class);
                    mainIntent.putExtra(MainActivity.FRAGMENT_KEY, MainActivity.SHOW_FEED_PREFERENCES);
                    startActivity(mainIntent);
                    finish();
                }
            });
        }

        mViewHolder.articleTime.setText(mArticle.article_time.time);
        mViewHolder.articleDate.setText(mArticle.article_time.date);
        mViewHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArticle.liked) {
                    unlikeArticle();
                } else {
                    likeArticle();
                }
            }
        });

        setupLike();
        if (mArticle.viewed)
            mViewHolder.viewIcon.setColorFilter(mArticle.getAccentColor());
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

    public void addCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType(EVENT_TYPE);
        intent.putExtra(CalendarContract.Events.TITLE, mArticle.title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, mArticle.description);
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, mArticle.event_location);
        intent.putExtra(EVENT_BEGIN_TIME, mArticle.event_time.calender.getTimeInMillis());
        intent.putExtra(
                EVENT_END_TIME,
                mArticle.event_time.calender.getTimeInMillis() + TimestampItem.ONE_HOUR);
        startActivity(intent);
    }

    private void setupLike() {
        if (mArticle.liked) {
            mViewHolder.likeIcon.setColorFilter(mArticle.getAccentColor());
        } else {
            mViewHolder.likeIcon.clearColorFilter();
        }
    }

    private void interpretJSONResponse(JSONObject jsonObject) {
        try {
            mArticle.liked = jsonObject.getBoolean(Constants.Article.RESPONSE_LIKED);
            mArticle.viewed = jsonObject.getBoolean(Constants.Article.RESPONSE_VIEWED);
            mArticle.likes = jsonObject.getInt(Constants.Article.RESPONSE_LIKES);
            mArticle.views = jsonObject.getInt(Constants.Article.RESPONSE_VIEWS);

            setupLike();
            mViewHolder.likes.setText(mArticle.likes.toString());
            mViewHolder.views.setText(mArticle.views.toString());
            if (mArticle.viewed)
                mViewHolder.viewIcon.setColorFilter(mArticle.getAccentColor());

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
                                    if (result.has("id")) {
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
                params.put("token-auth", LocalData.load(context, LocalData.Tags.USER_TOKEN));
                return params;
            }
        };

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private String getActionBaseUrl() {
        if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return ServerUrls.getInstance().EVENTS;
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return ServerUrls.getInstance().NEWS;
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
            return ServerUrls.getInstance().FEEDS;
        } else {
            return null;
        }
    }

    private JSONObject getActionParams() {
        try {
            JSONObject params = new JSONObject();
            if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
                params.put(Constants.Article.REQUEST_EVENT, mArticle.id);
            } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
                params.put(Constants.Article.REQUEST_NEWS, mArticle.id);
            } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_FEED)) {
                params.put(Constants.Article.REQUEST_ENTRY, mArticle.id);
            } else {
                return null;
            }

            params.put(Constants.Article.REQUEST_USER,
                    LocalData.load(mContext, LocalData.Tags.USER_ID));
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void likeArticle() {
        apiAction(mContext, getActionBaseUrl() + Constants.Article.ACTION_URL_LIKE,
                getActionParams(), Constants.Article.ACTION_LIKE);
    }

    private void unlikeArticle() {
        apiAction(mContext, getActionBaseUrl() + Constants.Article.ACTION_URL_UNLIKE,
                getActionParams(), Constants.Article.ACTION_UNLIKE);
    }

    private void viewArticle() {
        apiAction(mContext, getActionBaseUrl() + Constants.Article.ACTION_URL_VIEW,
                getActionParams(), Constants.Article.ACTION_VIEW);
    }

    public class EventViewHolder {
        FloatingActionButton categoryImage;
        ImageView addToCalendar, likeIcon, viewIcon, eventLogo;
        TextView title, description, sourceName, sourceDesignation, likes, views;
        TextView eventTime, eventDate, eventLocation;
        TextView articleTime, articleDate;
        RelativeLayout eventLayout, likePanel;
        LinearLayout scrollLayout;
        HorizontalScrollView horizontalScrollView;
    }

}
