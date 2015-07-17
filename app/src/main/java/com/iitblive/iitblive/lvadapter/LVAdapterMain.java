package com.iitblive.iitblive.lvadapter;

/**
 * Created by Bijoy on 1/15/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.EventListViewItem;
import com.iitblive.iitblive.util.CategoryImageMapping;
import com.iitblive.iitblive.util.Constants;
import com.iitblive.iitblive.util.Functions;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterMain extends ArrayAdapter<EventListViewItem> {
    private final Context mContext;
    private final List<EventListViewItem> mValues;
    private final Integer mLayoutId;
    private Map<Integer, Drawable> mImages;

    public LVAdapterMain(Context context, List<EventListViewItem> values) {
        super(context, R.layout.grid_item_layout, values);
        this.mLayoutId = R.layout.grid_item_layout;
        this.mContext = context;
        this.mValues = values;
        mImages = new HashMap<>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EventViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(mLayoutId, parent, false);
            viewHolder = new EventViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.sourceName = (TextView) convertView.findViewById(R.id.source);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.likes);
            viewHolder.views = (TextView) convertView.findViewById(R.id.views);
            viewHolder.eventImage = (ImageView) convertView.findViewById(R.id.card_image);
            viewHolder.categoryImage = (FloatingActionButton) convertView.findViewById(R.id.category_logo);
            viewHolder.eventTime = (TextView) convertView.findViewById(R.id.event_time);
            viewHolder.eventDate = (TextView) convertView.findViewById(R.id.event_date);
            viewHolder.eventLayout = (RelativeLayout) convertView.findViewById(R.id.event_layout);
            viewHolder.emphasisView = convertView.findViewById(R.id.emphasis_panel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventViewHolder) convertView.getTag();
        }

        EventListViewItem data = mValues.get(position);

        if (data != null) {
            viewHolder.title.setText(data.title);
            viewHolder.description.setText(
                    Functions.cropString(data.description,
                            Constants.DESCRIPTION_CROP_SIZE)
            );
            viewHolder.categoryImage.setIcon(
                    mContext.getResources().getDrawable(CategoryImageMapping.getDrawable(data.category)),
                    false
            );
            viewHolder.likes.setText("" + data.likes);
            viewHolder.views.setText("" + data.views);
            viewHolder.sourceName.setText(data.source_name);
            viewHolder.emphasisView.setVisibility(View.VISIBLE);

            if (data.image_links != null && !data.image_links.isEmpty()) {
                viewHolder.eventImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(data.image_links.get(0))
                        .into(viewHolder.eventImage);
            } else {
                viewHolder.eventImage.setVisibility(View.GONE);
            }

            if (data.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
                viewHolder.eventLayout.setVisibility(View.VISIBLE);
                viewHolder.eventTime.setText(data.event_time.time);
                viewHolder.eventDate.setText(data.event_time.date);
                viewHolder.emphasisView.setVisibility(View.VISIBLE);
                viewHolder.emphasisView.setBackgroundColor(Constants.ACCENT_COLOR);
            } else if (data.type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {
                viewHolder.emphasisView.setBackgroundColor(Constants.ACCENT_COLOR_TEAL);
            } else if (data.type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
                viewHolder.emphasisView.setBackgroundColor(getPriorityColor(data.notice_priority));
            }
        }
        return convertView;
    }

    public int getPriorityColor(String priority) {
        if (priority.contentEquals(Constants.NOTICE_PRIORITY_LOW)) {
            return Constants.NOTICE_EMPHASIS_COLOR_LOW;
        } else if (priority.contentEquals(Constants.NOTICE_PRIORITY_MEDIUM)) {
            return Constants.NOTICE_EMPHASIS_COLOR_MEDIUM;
        } else if (priority.contentEquals(Constants.NOTICE_PRIORITY_HIGH)) {
            return Constants.NOTICE_EMPHASIS_COLOR_HIGH;
        } else if (priority.contentEquals(Constants.NOTICE_PRIORITY_URGENT)) {
            return Constants.NOTICE_EMPHASIS_COLOR_URGENT;
        }
        return Constants.ACCENT_COLOR;
    }

    public class EventViewHolder {
        ImageView eventImage;
        TextView title, description, sourceName, likes, views;
        TextView eventTime, eventDate;
        RelativeLayout eventLayout;
        View emphasisView;
        FloatingActionButton categoryImage;
    }
}


