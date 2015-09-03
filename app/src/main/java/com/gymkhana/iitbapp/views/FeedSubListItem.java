package com.gymkhana.iitbapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.gymkhana.iitbapp.R;
import com.rey.material.widget.CheckBox;

/**
 * Created by bijoy on 9/3/15.
 */
public class FeedSubListItem extends LinearLayout {

    public CheckBox mSubscribed;

    public FeedSubListItem(Context context) {
        super(context);
        init(context);
    }

    public FeedSubListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FeedSubListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.feed_sub_item_layout, this, true);

        mSubscribed = (CheckBox) rootView.findViewById(R.id.subscribed);
    }

    public void setup(String title, Boolean subscribed) {
        mSubscribed.setCheckedImmediately(subscribed);
        mSubscribed.setText(title);
    }

}
