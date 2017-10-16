package com.kapture.zaf.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import com.kapture.zaf.R;
import com.kapture.zaf.custom.MyAdapter;
import com.kapture.zaf.pojos.Event;
import com.kapture.zaf.pojos.Event2;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsLineUpFragment extends Fragment {

    View v;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Event> myEvents = new ArrayList<>();
    ProgressDialog progressDialog;



    public EventsLineUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events_line_up, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v=view;

        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(v.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Get the events from Firebase Database
        fetchEvents();
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myEvents, new MyAdapter.OnSelectedItemClickListener() {
            @Override
            public void onClick(Event event, int position) {
                listener.onClick(event);
            }
        });
        progressDialog.dismiss();
        mRecyclerView.setAdapter(mAdapter);



    }

    private void fetchEvents(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("zap/events");

        //Toast.makeText(v.getContext(),"Getting Data",Toast.LENGTH_SHORT).show();

        progressDialog = ProgressDialog.show(v.getContext(),null,"Loading...");
        //Toast.makeText(v.getContext(),"Dialogue",Toast.LENGTH_SHORT).show();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                myEvents.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Event event = snapshot.getValue(Event.class);

                    myEvents.add(event);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(v.getContext(),"Something went wrong. Error:" + databaseError.getDetails(),Toast.LENGTH_SHORT).show();
            }
        });
    }




    public interface OnListItemClickListener{
        void onClick(Event event);
    }
    private OnListItemClickListener listener;
    public void setOnListItemClickListener(OnListItemClickListener listener){
        this.listener = listener;
    }
}
