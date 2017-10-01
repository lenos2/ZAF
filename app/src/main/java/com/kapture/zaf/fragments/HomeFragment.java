package com.kapture.zaf.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kapture.zaf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View v;

    View artists,events, pictures;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;

        artists = (LinearLayout)v.findViewById(R.id.layout_artists);
        events = (LinearLayout)v.findViewById(R.id.layout_events);
        pictures = (LinearLayout)v.findViewById(R.id.layout_pictures);

        artists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickArtists();
            }
        });
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickEvents();
            }
        });
        pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickPictures();
            }
        });

    }

    public interface OnHomeClickListener{
        void onClickEvents();
        void onClickArtists();
        void onClickPictures();
    }

    private OnHomeClickListener listener;
    public void setOnClickListener(OnHomeClickListener listener){
        this.listener = listener;
    }
}
