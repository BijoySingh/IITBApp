package com.iitblive.iitblive.lvadapter;

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

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.GenericListViewItem;

import java.util.List;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterGeneric extends ArrayAdapter<GenericListViewItem> {
    private final Context mContext;
    private final List<GenericListViewItem> mValues;
    private final Integer mLayoutId;

    public LVAdapterGeneric(Context context, List<GenericListViewItem> values) {
        super(context, R.layout.list_item_layout, values);
        this.mLayoutId = R.layout.list_item_layout;
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        GenericListViewItem data = mValues.get(position);

        int layout = mLayoutId;
        if (data.tag == GenericListViewItem.IS_URGENT) {
            layout = R.layout.urgent_list_item_layout;
        }

        GenericViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layout, parent, false);
            viewHolder = new GenericViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);
            viewHolder.logoImage = (ImageView) convertView.findViewById(R.id.lv_logo);
            viewHolder.rightIconImage = (ImageView) convertView.findViewById(R.id.lv_right_icon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GenericViewHolder) convertView.getTag();
        }

        if (data != null) {
            viewHolder.title.setText(data.title);
            if (data.show_subtext)
                viewHolder.subtitle.setText(data.subtitle);

            viewHolder.logoImage.setImageResource(data.img_res);

            if (data.show_right_icon) {
                viewHolder.rightIconImage.setVisibility(View.VISIBLE);
                viewHolder.rightIconImage.setImageResource(data.img_right_res);
            }
        }

        return convertView;
    }

    public class GenericViewHolder {
        ImageView logoImage, rightIconImage;
        TextView title, subtitle;
    }
}