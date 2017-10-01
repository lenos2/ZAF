package com.kapture.zaf.classes;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kapture.zaf.R;
import com.kapture.zaf.pojos.Event;

import java.util.List;

/**
 * Created by lenos on 28/9/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Event> mDataset;
    View v2;
    public int selectedItem;
    public Event item;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // each data item is just a string in this case
        public TextView mTextView;

        CardView cv;
        TextView tvEventName;
        TextView tvSummaryHeading;
        TextView tvSummaryBody;
        ImageView ivEventHero;
        public ViewHolder(View v,OnMyViewHolderClickListener listener) {
            super(v);
            this.listener = listener;
            cv = (CardView)v.findViewById(R.id.cv);
            tvEventName = (TextView)v.findViewById(R.id.tvEventName);
            tvSummaryBody = (TextView)v.findViewById(R.id.tvSummaryBody);
            tvSummaryHeading = (TextView)v.findViewById(R.id.tvSummaryHeading);
            ivEventHero = (ImageView)v.findViewById(R.id.ivEventHero);

            cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick();
        }

        public interface OnMyViewHolderClickListener{
            void onClick();
        }
        private OnMyViewHolderClickListener listener;

    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<Event> myDataset, OnSelectedItemClickListener listener) {
        this.listener = listener;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        v2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_event_list_item, parent, false);
        ViewHolder pvh = new ViewHolder(v2, new ViewHolder.OnMyViewHolderClickListener() {
            @Override
            public void onClick() {
                listener.onClick(item,selectedItem);
            }
        });
        return pvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        item = mDataset.get(position);
        selectedItem = position;

        holder.tvEventName.setText(item.getName());
        //holder.tvSummaryBody.setText(item.getSummaryBody());
        //holder.tvSummaryHeading.setText(item.getSummaryHeader());

        Glide.with(v2.getContext()).load(item.getImage()).centerCrop().into(holder.ivEventHero);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface OnSelectedItemClickListener{
        void onClick(Event event, int position);
    }
    private OnSelectedItemClickListener listener;
}