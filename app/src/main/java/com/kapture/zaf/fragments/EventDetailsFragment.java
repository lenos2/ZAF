package com.kapture.zaf.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kapture.zaf.R;
import com.kapture.zaf.pojos.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {

    View v;
    ImageView ivEventPicture;
    TextView tvDescription, tvHeading;
    Event mEvent;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){
            Bundle extra = getArguments();
            mEvent = (Event)extra.getSerializable("event");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v=view;
        ivEventPicture = (ImageView)v.findViewById(R.id.ivEventPicture);
        Glide.with(v.getContext()).load(mEvent.getImage()).into(ivEventPicture);

        tvDescription = (TextView)v.findViewById(R.id.tvDescription);
        tvDescription.setText(mEvent.getDescription());

        tvHeading = (TextView)v.findViewById(R.id.tvHeading);
        tvHeading.setText(mEvent.getSummaryHeader());

    }
}
