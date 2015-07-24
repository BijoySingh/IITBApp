package com.iitblive.iitblive.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.items.TimestampItem;
import com.iitblive.iitblive.util.CategoryImages;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.ServerUrls;
import com.iitblive.iitblive.util.SharedPreferenceManager;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class ArticleActivity extends ActionBarActivity {

    public static ApiItem mArticle;
    private static String EVENT_TYPE = "vnd.android.cursor.item/event";
    private static String EVENT_BEGIN_TIME = "beginTime";
    private static String EVENT_END_TIME = "endTime";

    private Context mContext;
    private EventViewHolder mViewHolder = new EventViewHolder();
    private boolean mIsLiked = false;

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_layout);

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
        mViewHolder.articleTime = (TextView) findViewById(R.id.article_time);
        mViewHolder.articleDate = (TextView) findViewById(R.id.article_date);
        mViewHolder.eventLayout = (RelativeLayout) findViewById(R.id.event_layout);
        mViewHolder.horizontalScrollView =
                (HorizontalScrollView) findViewById(R.id.horizontal_scroll_view);

        mViewHolder.title.setText(mArticle.title);
        mViewHolder.description.setText(mArticle.description);
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
                    (int) convertDpToPixel(320, mContext)
            ));
            Picasso.with(mContext).load(link).into(imageView);
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
            mViewHolder.addToCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCalendarEvent();
                }
            });
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
            mViewHolder.eventLayout.setVisibility(View.VISIBLE);
            mViewHolder.eventTime.setText(mArticle.expiration_time.time);
            mViewHolder.eventDate.setText(mArticle.expiration_time.date);
            mViewHolder.addToCalendar.setVisibility(View.GONE);
        }

        mViewHolder.articleTime.setText(mArticle.article_time.time);
        mViewHolder.articleDate.setText(mArticle.article_time.date);
        mViewHolder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsLiked) {
                    unlikeArticle();
                } else {
                    likeArticle();
                }
            }
        });
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
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    public void addCalendarEvent() {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType(EVENT_TYPE);
        intent.putExtra(CalendarContract.Events.TITLE, mArticle.title);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, mArticle.description);
        intent.putExtra(EVENT_BEGIN_TIME, mArticle.event_time.calender.getTimeInMillis());
        intent.putExtra(
                EVENT_END_TIME,
                mArticle.event_time.calender.getTimeInMillis() + TimestampItem.ONE_HOUR);
        startActivity(intent);
    }

    private void actionResponse(int actionType, JSONObject jsonObject) {
        switch (actionType) {
            case Constants.Article.ACTION_LIKE:
                mViewHolder.likeIcon.setColorFilter(mArticle.getAccentColor());
                mIsLiked = true;
                break;
            case Constants.Article.ACTION_UNLIKE:
                mViewHolder.likeIcon.clearColorFilter();
                mIsLiked = false;
                break;
            case Constants.Article.ACTION_VIEW:
                mViewHolder.viewIcon.setColorFilter(mArticle.getAccentColor());
                break;
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
                                    Log.d("LOG", result.toString());
                                    if (result.has("id")) {
                                        actionResponse(actionType, result);
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
                });

        Volley.newRequestQueue(context).add(jsonRequest);
    }

    private String getActionBaseUrl() {
        if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
            return ServerUrls.getInstance().EVENTS;
        } else if (mArticle.type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
            return ServerUrls.getInstance().NEWS;
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
            } else {
                return null;
            }

            params.put(Constants.Article.REQUEST_USER,
                    SharedPreferenceManager.load(mContext, SharedPreferenceManager.Tags.USER_ID));
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
        ImageView addToCalendar, likeIcon, viewIcon;
        TextView title, description, sourceName, sourceDesignation, likes, views;
        TextView eventTime, eventDate;
        TextView articleTime, articleDate;
        RelativeLayout eventLayout;
        LinearLayout scrollLayout;
        HorizontalScrollView horizontalScrollView;
    }

}
