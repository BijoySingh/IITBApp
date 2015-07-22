package com.iitblive.iitblive.lvadapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitblive.iitblive.R;
import com.iitblive.iitblive.activity.ArticleActivity;
import com.iitblive.iitblive.fragment.HomeFragment;
import com.iitblive.iitblive.items.ApiItem;
import com.iitblive.iitblive.items.NowCardItem;
import com.iitblive.iitblive.views.NowCardListItem;

/**
 * Created by bijoy on 7/7/15.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private static final int MAX_COUNT = 3;

    private OnItemClickListener mClickListener;
    private Context mContext;

    public HomeRecyclerViewAdapter(Context context) {
        mContext = context;
    }

    public void setListener(OnItemClickListener onItemClickListener) {
        mClickListener = onItemClickListener;
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
        holder.description.setText(data.mDescription);

        if (data.mColor != null) {
            holder.title.setTextColor(Color.WHITE);
            holder.title.setBackgroundColor(data.mColor);
        }

        holder.content.removeAllViewsInLayout();
        int count = 0;
        for (final ApiItem apiItem : data.mData) {
            NowCardListItem item = new NowCardListItem(mContext);
            item.setTitle(apiItem.title);
            item.setDescription(apiItem.description);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArticleActivity.mArticle = apiItem;
                    Intent article = new Intent(mContext, ArticleActivity.class);
                    mContext.startActivity(article);
                }
            });
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

    public interface OnItemClickListener {
        void itemClicked(View v, int position);
        void itemLongClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        LinearLayout content;
        TextView title, description;

        public ViewHolder(View v) {
            super(v);
            content = (LinearLayout) v.findViewById(R.id.content);
            title = (TextView) v.findViewById(R.id.title);
            description = (TextView) v.findViewById(R.id.description);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                mClickListener.itemClicked(v, getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (mClickListener != null) {
                mClickListener.itemLongClick(v, getAdapterPosition());
            }
            return false;
        }
    }


}