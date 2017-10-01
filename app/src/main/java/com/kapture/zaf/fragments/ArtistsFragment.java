package com.kapture.zaf.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kapture.zaf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistsFragment extends Fragment {

    View v;
    TextView tvArtist1, tvArtist2, tvArtist3, tvArtist4;

    public ArtistsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v=view;

        tvArtist1 = (TextView)v.findViewById(R.id.tvArtist1);
        tvArtist2 = (TextView)v.findViewById(R.id.tvArtist2);
        tvArtist3 = (TextView)v.findViewById(R.id.tvArtist3);
        tvArtist4 = (TextView)v.findViewById(R.id.tvArtist4);

        tvArtist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArtistClick("Oliver Mutukudzi");
            }
        });
        tvArtist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArtistClick("Winky D");
            }
        });
        tvArtist3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArtistClick("Soul Jah Luv");
            }
        });
        tvArtist4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArtistClick("Jah Prayzah");
            }
        });
    }

    public interface OnArtistClickListener{
        void onArtistClick(String artist);
    }

    private OnArtistClickListener listener;
    public void setOnArtistClickListener(OnArtistClickListener listener){
        this.listener = listener;
    }
}
