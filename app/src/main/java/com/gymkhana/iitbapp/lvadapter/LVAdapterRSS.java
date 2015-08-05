package com.gymkhana.iitbapp.lvadapter;

/**
 * Created by Bijoy on 1/15/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.feed.RSSFeedItem;
import com.gymkhana.iitbapp.util.CategoryImages;
import com.rey.material.widget.FloatingActionButton;

import java.util.List;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterRSS extends ArrayAdapter<RSSFeedItem> {
    private final Context mContext;
    private final List<RSSFeedItem> mValues;
    private final Integer mLayoutId;

    public LVAdapterRSS(Context context, List<RSSFeedItem> values) {
        super(context, R.layout.grid_item_layout, values);
        this.mLayoutId = R.layout.grid_item_layout;
        this.mContext = context;
        this.mValues = values;
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
            viewHolder.likeIcon = (ImageView) convertView.findViewById(R.id.like_logo);
            viewHolder.viewIcon = (ImageView) convertView.findViewById(R.id.view_logo);
            viewHolder.categoryImage = (FloatingActionButton) convertView.findViewById(R.id.category_logo);
            viewHolder.eventTime = (TextView) convertView.findViewById(R.id.event_time);
            viewHolder.eventDate = (TextView) convertView.findViewById(R.id.event_date);
            viewHolder.eventLayout = (RelativeLayout) convertView.findViewById(R.id.event_layout);
            viewHolder.emphasisView = convertView.findViewById(R.id.emphasis_panel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (EventViewHolder) convertView.getTag();
        }

        RSSFeedItem data = mValues.get(position);

        if (data != null) {
            viewHolder.title.setText(data.title);
            viewHolder.description.setText(Html.fromHtml(data.description.trim()).toString());
            viewHolder.categoryImage.setIcon(
                    mContext.getResources().getDrawable(CategoryImages.getDrawable("feed")),
                    false
            );
            viewHolder.sourceName.setText(data.name);

            int categoryColor = data.getAccentColor();
            viewHolder.categoryImage.setBackgroundColor(categoryColor);
            viewHolder.eventDate.setVisibility(View.GONE);

            viewHolder.eventImage.setVisibility(View.GONE);
            viewHolder.likes.setVisibility(View.GONE);
            viewHolder.likeIcon.setVisibility(View.GONE);
            viewHolder.viewIcon.setVisibility(View.GONE);
            viewHolder.views.setVisibility(View.GONE);
        }

        return convertView;
    }

    public class EventViewHolder {
        ImageView eventImage, likeIcon, viewIcon;
        TextView title, description, sourceName, likes, views;
        TextView eventTime, eventDate;
        RelativeLayout eventLayout;
        View emphasisView;
        FloatingActionButton categoryImage;
    }
}


