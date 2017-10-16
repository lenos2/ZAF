package com.kapture.zaf.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.kapture.zaf.MainActivity;
import com.kapture.zaf.R;
import com.kapture.zaf.pojos.Sale;
import com.kapture.zaf.pojos.Ticket;
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyTicketDetailFragment extends Fragment {

    private static final String TICKET = "ticket";
    private static final String[] NUMBERS =  {"1","2","3","4","5","6","7","8","9","10"};

    View v;
    Button btnBuy;
    Spinner spNumber;
    TextView tvTicketType, tvPrice;
    ImageView ivTicketPicture;
    Ticket t;
    Sale s;

    SmsVerifyCatcher smsVerifyCatcher;

    int numberOfTickets = 1;

    public BuyTicketDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!= null){
            t = (Ticket)getArguments().getSerializable(TICKET);
            Log.d("Ticket","The ticket was received");
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
        btnBuy = (Button)v.findViewById(R.id.btnBuy);

        spNumber = (Spinner)v.findViewById(R.id.spinner);


        //need to find a nice spinner library
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),android.R.layout.simple_spinner_item,NUMBERS);

        spNumber.setAdapter(adapter);
        spNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                numberOfTickets = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (t != null){
            tvTicketType.setText(t.getName());
            tvPrice.setText(Double.toString(t.getPrice()));
            Glide.with(v.getContext()).load(t.getImage()).into(ivTicketPicture);
        }


        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(v.getContext()).setTitle("Select Payment Method").setItems(R.array.payment_methods, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0 :
                                /** Make payment using Ecocash short code*/
                                dialogInterface.dismiss();
                                paymentTransaction();
                                break;
                            case 1 :
                                /** Make payment using Paynow*/
                                dialogInterface.dismiss();
                                s.setNumber(numberOfTickets);
                                s.setTicketType(t.getName());
                                s.setTotal(numberOfTickets * t.getPrice());
                                s.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                break;
                            default:
                        }
                    }
                }).create().show();
            }
        });


    }

    private void paymentTransaction(){
        //Send order to shop with pending payment status and get order number

        double totalPrice = numberOfTickets * t.getPrice();

        if (totalPrice > Math.round(totalPrice)){
            totalPrice = Math.round(totalPrice);
        }
        /** TODO: form the Sale class properly, add the ticket to a cart then implement a checkout for all the tickets chosen */

        listener.onSale(s);
        //Make payment via ecocash
        String ussdCode = "*151*1*1*0771535326*"+ String.format("%.0f",totalPrice)+ Uri.encode("#");
        Intent kufona = new Intent("android.intent.action.CALL", Uri.parse("tel:" + ussdCode));
        startActivity(kufona);


        //Send Payment Confirmation to shop when payment is complete
        //progressDialog.setMessage("Waiting for Payment confirmation");
        //progressDialog.show();


    }

    public interface OnSaleListener{
        void onSale(Sale s);
    }
    private OnSaleListener listener;
    public void setOnSaleListener(OnSaleListener listener){
        this.listener = listener;
    }
}
