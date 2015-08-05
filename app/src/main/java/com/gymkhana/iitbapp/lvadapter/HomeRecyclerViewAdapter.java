package com.gymkhana.iitbapp.lvadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gymkhana.iitbapp.MainActivity;
import com.gymkhana.iitbapp.R;
import com.gymkhana.iitbapp.activity.ArticleActivity;
import com.gymkhana.iitbapp.activity.RSSViewerActivity;
import com.gymkhana.iitbapp.feed.RSSFeedItem;
import com.gymkhana.iitbapp.fragment.HomeFragment;
import com.gymkhana.iitbapp.fragment.RSSFragment;
import com.gymkhana.iitbapp.items.ApiItem;
import com.gymkhana.iitbapp.items.NowCardItem;
import com.gymkhana.iitbapp.util.Constants;
import com.gymkhana.iitbapp.views.NowCardListItem;

/**
 * Created by bijoy on 7/7/15.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private static final int MAX_COUNT = 3;

    private Context mContext;

    public HomeRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    @Override
    public HomeRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.now_card_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final NowCardItem data = HomeFragment.mCards.get(position);
        holder.title.setText(data.mTitle);

        View.OnClickListener showFragment = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.mType == Constants.DATA_TYPE_RSS) {
                    RSSFragment.mFeed = data.mFeed;

                }
                ((MainActivity) mContext).displayFragment(data.mFragmentId);
            }
        };
        holder.titlebar.setOnClickListener(showFragment);
        holder.showMore.setOnClickListener(showFragment);

        holder.description.setText(data.mDescription);

        if (data.mColor != null) {
            holder.title.setTextColor(Color.WHITE);
            holder.titlebar.setBackgroundColor(data.mColor);
            holder.logo.setColorFilter(Color.WHITE);
        }
        if (data.mIconResource != null) {
            holder.logo.setImageResource(data.mIconResource);
        }

        holder.content.removeAllViewsInLayout();
        int count = 0;
        for (final Object object : data.mData) {
            NowCardListItem item = new NowCardListItem(mContext);

            if (data.mType == Constants.DATA_TYPE_RSS) {
                final RSSFeedItem rssFeedItem = (RSSFeedItem) object;
                item.setTitle(rssFeedItem.title);
                item.setDescription(Html.fromHtml(rssFeedItem.description.trim()).toString());
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent article = new Intent(mContext, RSSViewerActivity.class);
                        article.putExtra(RSSViewerActivity.INTENT_RSS, rssFeedItem);
                        mContext.startActivity(article);
                    }
                });
            } else {
                final ApiItem apiItem = (ApiItem) object;
                item.setTitle(apiItem.title);
                item.setDescription(apiItem.description.trim());
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent article = new Intent(mContext, ArticleActivity.class);
                        article.putExtra(ArticleActivity.INTENT_ARTICLE, apiItem);
                        mContext.startActivity(article);
                    }
                });
            }

            holder.content.addView(item);
            if (++count == MAX_COUNT) {
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return HomeFragment.mCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout content, titlebar, showMore;
        TextView title, description;
        ImageView logo;

        public ViewHolder(View v) {
            super(v);
            content = (LinearLayout) v.findViewById(R.id.content);
            titlebar = (LinearLayout) v.findViewById(R.id.titlebar);
            showMore = (LinearLayout) v.findViewById(R.id.show_more);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
            logo = (ImageView) v.findViewById(R.id.logo);
        }
    }


}