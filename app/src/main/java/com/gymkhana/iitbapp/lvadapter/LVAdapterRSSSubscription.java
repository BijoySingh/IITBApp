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
import android.widget.ImageView;
import android.widget.TextView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.feed.RSSFeedConstants;
import com.gymkhana.iitbapp.fragment.RSSFragment;
import com.gymkhana.iitbapp.util.SharedPreferenceManager;
import com.rey.material.widget.Switch;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterRSSSubscription extends ArrayAdapter<RSSFeedConstants.Feed> {
    private final Context mContext;
    private final RSSFeedConstants.Feed[] mValues;
    private final Integer mLayoutId;

    public LVAdapterRSSSubscription(Context context, RSSFeedConstants.Feed[] values) {
        super(context, R.layout.feed_list_item_layout, values);
        this.mLayoutId = R.layout.feed_list_item_layout;
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final RSSFeedConstants.Feed data = mValues[position];

        int layout = mLayoutId;

        GenericViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new GenericViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
            viewHolder.logoImage = (ImageView) convertView.findViewById(R.id.lv_logo);
            viewHolder.status = (Switch) convertView.findViewById(R.id.lv_right_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GenericViewHolder) convertView.getTag();
        }

        if (data != null) {
            viewHolder.title.setText(data.title);
            viewHolder.subtitle.setText(data.description);

            viewHolder.logoImage.setImageResource(R.drawable.feed_options_icon);
            viewHolder.status.setVisibility(View.VISIBLE);
            if (SharedPreferenceManager.load(mContext, data.feed_id).contentEquals(SharedPreferenceManager.Tags.FALSE)) {
                viewHolder.status.setChecked(false);
            } else {
                viewHolder.status.setChecked(true);
            }
        }

        View.OnClickListener openFeed = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RSSFragment.mFeed = data;
                ((MainActivity) mContext).displayFragment(MainActivity.SHOW_FEED);
            }
        };
        viewHolder.title.setOnClickListener(openFeed);
        viewHolder.subtitle.setOnClickListener(openFeed);
        viewHolder.logoImage.setOnClickListener(openFeed);

        viewHolder.status.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(Switch aSwitch, boolean b) {
                if (b) {
                    SharedPreferenceManager.save(mContext, data.feed_id, SharedPreferenceManager.Tags.TRUE);
                } else {
                    SharedPreferenceManager.save(mContext, data.feed_id, SharedPreferenceManager.Tags.FALSE);
                }
            }
        });

        return convertView;
    }

    public class GenericViewHolder {
        ImageView logoImage;
        TextView title, subtitle;
        Switch status;
    }
}