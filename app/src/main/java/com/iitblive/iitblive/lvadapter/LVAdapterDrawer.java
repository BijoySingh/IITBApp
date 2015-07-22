package com.iitblive.iitblive.lvadapter;

/**
 * Created by Bijoy on 1/15/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.GenericItem;

/*Listview adapter for the navigation drawer listview*/
public class LVAdapterDrawer extends ArrayAdapter<GenericItem> {
    private final Context mContext;
    private final GenericItem[] mValues;

    public LVAdapterDrawer(Context context, GenericItem[] values) {
        super(context, R.layout.drawer_list_item, values);
        this.mContext = context;
        this.mValues = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String title = mValues[position].title;
        if (title.contentEquals("")) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView;
            rowView = inflater.inflate(R.layout.drawer_title_list_view, parent, false);

            return rowView;
        }
        LayoutInflater layoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView;
        rowView = layoutInflater.inflate(R.layout.drawer_list_item, parent, false);

        TextView textView = (TextView) rowView.findViewById(R.id.lv_title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.lv_image);

        textView.setText(mValues[position].title);
        imageView.setImageResource(mValues[position].img_res);

        return rowView;
    }
}
