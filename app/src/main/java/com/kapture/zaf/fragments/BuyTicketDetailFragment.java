package com.kapture.zaf.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.kapture.zaf.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTicketDetailFragment extends Fragment {

    private static final String TICKET = "ticket";

    View v;
    Button btnBuy;
    Spinner spNumber;
    TextView tvTicketType, tvPrice;
    ImageView ivTicketPicture;
    Ticket t;

    public BuyTicketDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null){
            t = (Ticket)getArguments().getParcelable(TICKET);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buy_ticket_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v = view;

        tvTicketType = (TextView)v.findViewById(R.id.tvTicketType);
        tvPrice = (TextView)v.findViewById(R.id.tvPrice);
        ivTicketPicture = (ImageView)v.findViewById(R.id.ivTicketPicture);
        btnBuy = (Button)v.findViewById(R.id.buy_now);

        //need to find a nice spinner library

        if (t != null){
            //tvTicketType.setText(t.getTicketType());
            //tvPrice.setText(t.getPrice());
            //Glide.with(v.getContext()).load(t.getImage()).into(ivTicketPicture);
        }


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(v.getContext()).setTitle("Select Payment Method").setItems(R.array.payment_methods, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                /** Make payment using Paynow*/
                                dialogInterface.dismiss();

                                break;
                            case 1 :
                                /** Make payment using Ecocash short code*/
                                dialogInterface.dismiss();

                                break;
                            default:
                        }
                    }
                }).create().show();
            }
        });
    }
}
