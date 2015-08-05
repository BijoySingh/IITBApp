package com.gymkhana.iitbapp.views;

import android.content.Context;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.R;

/**
 * Created by bijoy on 7/21/15.
 */
public class NowCardListItem extends LinearLayout {

    ImageView mImage;
    TextView mTitle, mDescription;

    public NowCardListItem(Context context) {
        super(context);
        init(context);
    }

    public NowCardListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NowCardListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = mInflater.inflate(R.layout.now_card_list_item, this, true);

        mTitle = (TextView) rootView.findViewById(R.id.title);
        mDescription = (TextView) rootView.findViewById(R.id.description);
    }

    public void setTitle(String text) {
        mTitle.setText(text);
    }

    public void setDescription(String text) {
        mDescription.setText(text);
    }

    public void setDescription(Spanned text) {
        mDescription.setText(text);
    }

}
