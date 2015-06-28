package com.iitblive.iitblive.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.items.TimestampItem;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.squareup.picasso.Picasso;

@SuppressLint("NewApi")
public class ArticleFragment extends Fragment {

  public static EventListViewItem mArticle;
  private Context mContext;
  private static String EVENT_TYPE = "vnd.android.cursor.item/event";
  private static String EVENT_BEGIN_TIME = "beginTime";
  private static String EVENT_END_TIME = "endTime";

  public class EventViewHolder {
    ImageView categoryImage;
    ImageView addToCalendar;
    TextView title, description, sourceName, sourceDesignation, likes, views;
    TextView eventTime, eventDate;
    TextView articleTime, articleDate;
    RelativeLayout eventLayout;
    LinearLayout scrollLayout;
    HorizontalScrollView horizontalScrollView;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.article_layout, container, false);

    EventViewHolder viewHolder = new EventViewHolder();
    viewHolder.title = (TextView) rootView.findViewById(R.id.title);
    viewHolder.description = (TextView) rootView.findViewById(R.id.description);
    viewHolder.sourceName = (TextView) rootView.findViewById(R.id.source);
    viewHolder.sourceDesignation = (TextView) rootView.findViewById(R.id.source_designation);
    viewHolder.likes = (TextView) rootView.findViewById(R.id.likes);
    viewHolder.views = (TextView) rootView.findViewById(R.id.views);
    viewHolder.scrollLayout = (LinearLayout) rootView.findViewById(R.id.image_scroll_view);
    viewHolder.categoryImage = (ImageView) rootView.findViewById(R.id.category_logo);
    viewHolder.addToCalendar = (ImageView) rootView.findViewById(R.id.add_event_logo);
    viewHolder.eventTime = (TextView) rootView.findViewById(R.id.event_time);
    viewHolder.eventDate = (TextView) rootView.findViewById(R.id.event_date);
    viewHolder.articleTime = (TextView) rootView.findViewById(R.id.article_time);
    viewHolder.articleDate = (TextView) rootView.findViewById(R.id.article_date);
    viewHolder.eventLayout = (RelativeLayout) rootView.findViewById(R.id.event_layout);
    viewHolder.horizontalScrollView =
        (HorizontalScrollView) rootView.findViewById(R.id.horizontal_scroll_view);

    viewHolder.title.setText(mArticle.title);
    viewHolder.description.setText(mArticle.description);
    viewHolder.categoryImage.setImageResource(
        Functions.getCategoryResource(mArticle.category)
    );
    viewHolder.likes.setText("" + mArticle.likes);
    viewHolder.views.setText("" + mArticle.views);
    viewHolder.sourceName.setText(mArticle.source_name);
    viewHolder.sourceDesignation.setText(mArticle.source_designation);

    for (String link : mArticle.image_links) {
      ImageView imageView = new ImageView(mContext);
      imageView.setAdjustViewBounds(true);
      imageView.setLayoutParams(new LinearLayout.LayoutParams(
          ViewGroup.LayoutParams.WRAP_CONTENT,
          (int) convertDpToPixel(320, mContext)
      ));
      Picasso.with(mContext).load(Constants.BASE_URL + link).into(imageView);
      viewHolder.scrollLayout.addView(imageView);
    }

    if (mArticle.image_links.size() == 0) {
      viewHolder.scrollLayout.setVisibility(View.GONE);
      viewHolder.horizontalScrollView.setVisibility(View.GONE);
    }

    if (mArticle.type.contentEquals("E")) {
      viewHolder.eventLayout.setVisibility(View.VISIBLE);
      viewHolder.eventTime.setText(mArticle.event_time.time);
      viewHolder.eventDate.setText(mArticle.event_time.date);
      viewHolder.addToCalendar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          addCalendarEvent();
        }
      });
    }

    viewHolder.articleTime.setText(mArticle.article_time.time);
    viewHolder.articleDate.setText(mArticle.article_time.date);

    return rootView;
  }

  public static float convertDpToPixel(float dp, Context context) {
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float px = dp * (metrics.densityDpi / 160f);
    return px;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    mContext = activity;
  }

  public void addCalendarEvent() {
    Intent intent = new Intent(Intent.ACTION_EDIT);
    intent.setType(EVENT_TYPE);
    intent.putExtra(Events.TITLE, mArticle.title);
    intent.putExtra(Events.DESCRIPTION, mArticle.description);
    intent.putExtra(EVENT_BEGIN_TIME, mArticle.event_time.calender.getTimeInMillis());
    intent.putExtra(
        EVENT_END_TIME,
        mArticle.event_time.calender.getTimeInMillis() + TimestampItem.ONE_HOUR);
    startActivity(intent);
  }
}
