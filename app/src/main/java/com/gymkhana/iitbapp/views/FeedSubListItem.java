package com.gymkhana.iitbapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.R;
import com.rey.material.widget.Switch;

/**
 * Created by bijoy on 9/3/15.
 */
public class FeedSubListItem extends LinearLayout {

    TextView mTitle;
    Switch mSubscribed;

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
        View rootView = mInflater.inflate(R.layout.now_card_list_item, this, true);

        mTitle = (TextView) rootView.findViewById(R.id.title);
        mSubscribed = (Switch) rootView.findViewById(R.id.subscribed);
    }

    public void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setSubscribed(Boolean subscribed) {
        mSubscribed.setChecked(subscribed);
    }

    public void setup(String title, Boolean subscribed) {
        setTitle(title);
        setSubscribed(subscribed);
    }

}
