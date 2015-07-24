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
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.util.CategoryImages;
import com.iitblive.iitblive.util.Constants;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterMain extends ArrayAdapter<ApiItem> {
    private final Context mContext;
    private final List<ApiItem> mValues;
    private final Integer mLayoutId;
    private Map<Integer, Drawable> mImages;

    public LVAdapterMain(Context context, List<ApiItem> values) {
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

        ApiItem data = mValues.get(position);

        if (data != null) {
            viewHolder.title.setText(data.title);
            viewHolder.description.setText(data.description.trim());
            viewHolder.categoryImage.setIcon(
                    mContext.getResources().getDrawable(CategoryImages.getDrawable(data.category)),
                    false
            );
            viewHolder.likes.setText("" + data.likes);
            viewHolder.views.setText("" + data.views);
            viewHolder.sourceName.setText(data.source_name + ", " + data.source_designation);

            if (data.image_links != null && !data.image_links.isEmpty()) {
                viewHolder.eventImage.setVisibility(View.VISIBLE);
                Picasso.with(mContext)
                        .load(data.image_links.get(0))
                        .into(viewHolder.eventImage);
            } else {
                viewHolder.eventImage.setVisibility(View.GONE);
            }

            int categoryColor = data.getAccentColor();
            viewHolder.categoryImage.setBackgroundColor(categoryColor);

            if (data.type.contentEquals(Constants.JSON_DATA_TYPE_EVENT)) {
                viewHolder.eventLayout.setVisibility(View.VISIBLE);
                viewHolder.eventTime.setText(data.event_time.time);
                viewHolder.eventDate.setText(data.event_time.date);
            } else if (data.type.contentEquals(Constants.JSON_DATA_TYPE_NEWS)) {

            } else if (data.type.contentEquals(Constants.JSON_DATA_TYPE_NOTICE)) {
                viewHolder.eventLayout.setVisibility(View.VISIBLE);
                viewHolder.eventTime.setText(data.expiration_time.time);
                viewHolder.eventDate.setText(data.expiration_time.date);
            }
        }
        return convertView;
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


