package com.gymkhana.iitbapp.lvadapter;

/**
 * Created by Bijoy on 5/27/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.items.FeedCategoryItem;
import com.gymkhana.iitbapp.items.FeedSubscriptionItem;
import com.gymkhana.iitbapp.util.LocalData;
import com.gymkhana.iitbapp.views.FeedSubListItem;
import com.rey.material.widget.Switch;

import java.util.ArrayList;
import java.util.List;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterFeeds extends ArrayAdapter<FeedSubscriptionItem> {
    private final Context mContext;
    private final Integer mLayoutId;
    public List<FeedSubscriptionItem> mValues;

    public LVAdapterFeeds(Context context, List<FeedSubscriptionItem> values) {
        super(context, R.layout.feed_list_item_layout, values);
        this.mLayoutId = R.layout.feed_list_item_layout;
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final FeedSubscriptionItem data = mValues.get(position);

        int layout = mLayoutId;

        GenericViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            holder = new GenericViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
            holder.logoImage = (ImageView) convertView.findViewById(R.id.lv_logo);
            holder.status = (Switch) convertView.findViewById(R.id.lv_right_icon);
            holder.categories = (LinearLayout) convertView.findViewById(R.id.categories);
            convertView.setTag(holder);
        } else {
            holder = (GenericViewHolder) convertView.getTag();
        }

        if (data != null) {
            holder.title.setText(data.title);
            holder.subtitle.setText(data.description);

            holder.logoImage.setImageResource(R.drawable.feed_options_icon);
            holder.status.setVisibility(View.VISIBLE);
            if (LocalData.load(mContext, data.prefKey()).contentEquals(LocalData.Tags.FALSE)) {
                holder.status.setChecked(false);
            } else {
                holder.status.setChecked(true);
            }
        }

        View.OnClickListener openFeed = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) mContext).displayFragment(MainActivity.SHOW_FEED);
            }
        };
        holder.title.setOnClickListener(openFeed);
        holder.subtitle.setOnClickListener(openFeed);
        holder.logoImage.setOnClickListener(openFeed);

        holder.status.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                if (b) {
                    LocalData.save(mContext, data.prefKey(), LocalData.Tags.TRUE);
                } else {
                    LocalData.save(mContext, data.prefKey(), LocalData.Tags.FALSE);
                }
            }
        });

        holder.categories.removeAllViewsInLayout();
        for (final FeedCategoryItem object : data.categories) {
            FeedSubListItem item = new FeedSubListItem(mContext);
            item.setup(object.term, object.subscribed);
            item.mSubscribed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    setSubscription(position, object.id, b);
                }
            });
            holder.categories.addView(item);
        }

        return convertView;
    }

    public List<Integer> getSubscriptions() {
        List<Integer> lst = new ArrayList<>();
        for (FeedSubscriptionItem feed : mValues) {
            for (FeedCategoryItem item : feed.categories) {
                if (item.subscribed) {
                    lst.add(item.id);
                }
            }
        }
        return lst;
    }

    private void setSubscription(int position, int category_id, boolean subscribed) {
        FeedSubscriptionItem item = mValues.get(position);
        for (int i = 0; i < item.categories.size(); i++) {
            if (item.categories.get(i).id == category_id) {
                FeedCategoryItem categoryItem = item.categories.get(i);
                categoryItem.subscribed = subscribed;
                mValues.get(position).categories.set(i, categoryItem);
                return;
            }
        }
    }

    public class GenericViewHolder {
        ImageView logoImage;
        TextView title, subtitle;
        Switch status;
        LinearLayout categories;
    }
}