package com.kapture.zaf.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapture.zaf.MainActivity;
import com.kapture.zaf.R;
import com.kapture.zaf.custom.GalleryAdapter;
import com.kapture.zaf.pojos.GalleryImage;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "https://api.androidhive.info/json/glide.json";
    private ArrayList<GalleryImage> images;
    private ProgressDialog pDialog;
    private GalleryAdapter mAdapter;
    private RecyclerView recyclerView;
    View v;

    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v=view;

        recyclerView = (RecyclerView) v.findViewById(R.id.gallery_recylerview);

        pDialog = new ProgressDialog(v.getContext());
        images = new ArrayList<>();
        mAdapter = new GalleryAdapter(v.getContext(), images);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(v.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnItemTouchListener(new GalleryAdapter.RecyclerTouchListener(v.getContext(), recyclerView, new GalleryAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                new ImageViewer.Builder<>(v.getContext(), images)
                        .setFormatter(new ImageViewer.Formatter<GalleryImage>() {
                            @Override
                            public String format(GalleryImage image) {
                                return image.getDownloadLink();
                            }
                        })
                        .show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        fetchImages();
    }

    private void fetchImages(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("zap/gallery");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                images.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    images.add(snapshot.getValue(GalleryImage.class));
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(v.getContext(),"Something Went wrong. Error :" + databaseError.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
