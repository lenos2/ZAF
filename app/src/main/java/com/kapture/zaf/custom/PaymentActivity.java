/*
package com.kapture.zaf.custom;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.hamburgerhut.hamburgerhut.SharedClasses.FormatToJSON;
import com.hamburgerhut.hamburgerhut.SharedClasses.Order;
import com.hamburgerhut.hamburgerhut.SharedClasses.ServerConn;
import com.hamburgerhut.hamburgerhut.SharedClasses.SharedValues;
import com.kapture.zaf.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

public class PaymentActivity extends AppCompatActivity {

    static final String ORDERS = "orders";
    static final String PAYMENT_STATUS = "paymentStatus";
    static final String CUSTOMER_NAME = "customerName";

    ImageButton btnEcocash;
    Button btnPayOnCollection;
    float packagePrice;
    static ProgressDialog progressDialog;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    String key;
    String delivery;

    CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {

        public void onTick(long millisUntilFinished) {
            //progressDialog.setMessage("seconds remaining: " + millisUntilFinished / 1000);

            //checks if the ecocash text message has been received in 1 minute
            if (SharedValues.paymentConfirmation.equals("+263164")){
                progressDialog.setMessage("Payment Confirmed");
                //sending payment confirmation to server
                sendConfirmation();
                this.onFinish();
                cancel();
            }
        }

        public void onFinish() {
            progressDialog.hide();
            if (SharedValues.paymentConfirmation.equals("")){
                AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this)
                        .setMessage("If Payment was done but verification failed but payment made, please contact our customer help line for assistance.")
                        .setTitle("Verification Failed")
                        .setPositiveButton("Ok",null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        */
/**Setting up the toolbar*//*

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setTitle("PAY-NOW!!!");

        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationIcon(R.mipmap.back_arrow_black_2);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        delivery = getIntent().getStringExtra("delivery");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Running USSD...");

        */
/**Get user reference*//*

        user = FirebaseAuth.getInstance().getCurrentUser();

        */
/**Todo: change this package reference*//*

        //packagePrice = SharedValues.getCartValue();
        btnEcocash = (ImageButton)findViewById(R.id.btnPayment_Ecocash);
        btnEcocash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment();
            }
        });

        btnPayOnCollection = (Button)findViewById(R.id.btnPayment_Pay_On_Collection);
        btnPayOnCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder("On collection");
            }
        });

    }

    private void makePayment(){
        */
/**This handles sending an order and making the payment*//*

        paymentTransaction();
    }

    */
/**Todo: Send the order first with a pending payment message then start the payment intent, when successful send payment confirmation*//*

    private void paymentTransaction(){
        //Send order to shop with pending payment status and get order number
        if (user!=null){
            sendOrder("ecocash");
            if (packagePrice > Math.round(packagePrice)){
                packagePrice = Math.round(packagePrice);
            }
            //Make payment via ecocash
            String ussdCode = "*151*1*1*0778027511*"+ String.format("%.0f",packagePrice)+ Uri.encode("#");
            Intent kufona = new Intent("android.intent.action.CALL",Uri.parse("tel:" + ussdCode));
            startActivity(kufona);
            //Send Payment Confirmation to shop when payment is complete
            progressDialog.setMessage("Waiting for Payment confirmation");
            progressDialog.show();
            countDownTimer.start();
        }

    }
    private void sendOrder(String paymentMethod){
        */
/** This is used to send order to server*//*

        final ProgressDialog sendDialog = ProgressDialog.show(PaymentActivity.this, null,
                getString(R.string.alert_wait));
        SharedPreferences prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        String customerName= prefs.getString(CUSTOMER_NAME, "");

        Calendar c = Calendar.getInstance();
        SharedValues.mOrder.setPaymentStatus(paymentMethod);
        SharedValues.mOrder.setCustomerName(customerName);
        SharedValues.mOrder.setCustomerID(user.getUid());
        SharedValues.mOrder.setDelivery(delivery);
        SharedValues.mOrder.setDate(c.get(Calendar.DAY_OF_MONTH) + "/" + (c.get(Calendar.MONTH)+1) + "/" + c.get(Calendar.YEAR)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE));
        firebaseDatabase = FirebaseDatabase.getInstance();
        key = firebaseDatabase
                .getReference(ORDERS)
                .push().getKey();
        firebaseDatabase.getReference(ORDERS).child(key)
                .setValue(SharedValues.mOrder)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        sendDialog.hide();
                        if (task.isSuccessful()) {
                            Toast.makeText(PaymentActivity.this, "Order placed.", Toast.LENGTH_LONG).show();
                            SharedValues.mOrder = new Order();
                            SharedValues.setCartValue(0);
                        } else {
                            Toast.makeText(PaymentActivity.this, "Something went wrong in sending order. Please please check network connection and try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        sendDialog.hide();
                        Toast.makeText(PaymentActivity.this, "Something went wrong in sending order. Please please check network connection and try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void sendConfirmation(){
        firebaseDatabase.getReference(ORDERS +"/"+ key).child(PAYMENT_STATUS).setValue("paid")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (!task.isSuccessful()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                            builder.setMessage("Failed to notify The Store of payment. Please retry sending confirmaton or contact the store for assistance")
                                    .setTitle("An error occurred")
                                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sendConfirmation();
                                        }
                                    })
                                    .setNegativeButton("Cancel", null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        } else {
                            Toast.makeText(PaymentActivity.this, "Confirmation sent!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
*/
