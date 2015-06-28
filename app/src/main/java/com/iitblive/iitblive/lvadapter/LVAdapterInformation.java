package com.iitblive.iitblive.lvadapter;

/**
 * Created by Bijoy on 5/27/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.items.InformationListViewItem;
import com.iitblive.iitblive.util.Functions;

import java.util.List;

/*Listview adapter for the navigation drawer listview*/

public class LVAdapterInformation extends ArrayAdapter<InformationListViewItem> {
  private final Context mContext;
  private final List<InformationListViewItem> mValues;
  private final Integer mLayoutId;

  public LVAdapterInformation(Context context, List<InformationListViewItem> values) {
    super(context, R.layout.information_list_item_layout, values);
    this.mLayoutId = R.layout.information_list_item_layout;
    this.mContext = context;
    this.mValues = values;
  }

  public class InformationViewHolder {
    TextView title, description;
    ImageView logo, phone, email, website, facebook;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    InformationViewHolder viewHolder;
    if (convertView == null) {
      LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
      convertView = inflater.inflate(mLayoutId, parent, false);
      viewHolder = new InformationViewHolder();
      viewHolder.title = (TextView) convertView.findViewById(R.id.title);
      viewHolder.description = (TextView) convertView.findViewById(R.id.description);
      viewHolder.logo = (ImageView) convertView.findViewById(R.id.logo);
      viewHolder.phone = (ImageView) convertView.findViewById(R.id.phone_icon);
      viewHolder.email = (ImageView) convertView.findViewById(R.id.email_icon);
      viewHolder.website = (ImageView) convertView.findViewById(R.id.website_icon);
      viewHolder.facebook = (ImageView) convertView.findViewById(R.id.facebook_icon);

      convertView.setTag(viewHolder);
    } else {
      viewHolder = (InformationViewHolder) convertView.getTag();
    }

    final InformationListViewItem data = mValues.get(position);

    if (data != null) {
      viewHolder.logo.setImageResource(data.img_resource);
      viewHolder.title.setText(data.title);
      viewHolder.description.setText(data.description);
      if (!data.phone.contentEquals("")) {
        viewHolder.phone.setImageResource(R.drawable.info_icon_phone);
        viewHolder.phone.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(data.phone));
            mContext.startActivity(intent);
          }
        });
      }

      if (!data.email.contentEquals("")) {
        viewHolder.email.setImageResource(R.drawable.info_icon_email);
        viewHolder.email.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{data.email});
            intent.putExtra(Intent.EXTRA_SUBJECT, "");
            intent.putExtra(Intent.EXTRA_TEXT, "");
            mContext.startActivity(Intent.createChooser(intent, ""));
          }
        });
      }

      if (!data.website.contentEquals("")) {
        viewHolder.website.setImageResource(R.drawable.info_icon_website);
        viewHolder.website.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Functions.openWebsite(mContext, data.website);
          }
        });
      }

      if (!data.facebook.contentEquals("")) {
        viewHolder.facebook.setImageResource(R.drawable.info_icon_facebook);
        viewHolder.facebook.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.facebook));
            mContext.startActivity(browserIntent);
          }
        });
      }
    }

    return convertView;
  }
}