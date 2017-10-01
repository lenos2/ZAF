package com.kapture.zaf.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kapture.zaf.R;
import com.kapture.zaf.pojos.Ticket;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTicketFragment extends Fragment {


    ListView lvTicklist;
    View v;
    ArrayList<Ticket> tickets = new ArrayList<>();
    TicketAdapter adapter;

    public BuyTicketFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_ticket, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;
        adapter = new TicketAdapter(v.getContext(),tickets);

        lvTicklist = (ListView)v.findViewById(R.id.listview);
        lvTicklist.setAdapter(adapter);
        lvTicklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Send The Item clicked to the details view
                listener.onClick(tickets.get(i));
            }
        });

        //GetTickets from the server
        gettickets();
    }

    private void gettickets(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("zap/tickets");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot :dataSnapshot.getChildren()){
                    Ticket t = snapshot.getValue(Ticket.class);
                    tickets.add(t);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public interface OnTicketPickedListener{
        void onClick(Ticket t);
    }
    private OnTicketPickedListener listener;
    public void setOnTicketPickedListener(OnTicketPickedListener listener){
        this.listener = listener;
    }

    class TicketAdapter extends ArrayAdapter<Ticket>{

        public TicketAdapter(@NonNull Context context, @NonNull List<Ticket> objects) {
            super(context, R.layout.layout_ticket_list_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            //return super.getView(position, convertView, parent);
            View v2 = convertView;
            if (v2 == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v2 = inflater.inflate(R.layout.layout_ticket_list_item,parent,false);
            }

            Ticket t = (Ticket)getItem(position);

            TextView tvTicketType = (TextView)v2.findViewById(R.id.tvTicketType);
            ImageView ivTicketPic = (ImageView)v2.findViewById(R.id.ivTicketPicture);

            /** TODO: Add the ticket list to the server and link using firebase database */
            tvTicketType.setText(t.getName());
            Glide.with(v2.getContext()).load(t.getImage()).into(ivTicketPic);
            return  v2;
        }
    }
}
