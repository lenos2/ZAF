/*
package com.kapture.zaf.custom;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsMessage;
import android.util.Log;

import com.hamburgerhut.hamburgerhut.SharedClasses.SharedValues;

*/
/**
 * Created by Leo on 7/26/2016.
 *//*

public class NotificationService extends IntentService{

    String msgBody;
    public static final int notifyID = 9001;

    public NotificationService() {
        super("TextIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from = null;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    }
                }catch(Exception e){
                    Log.d("Exception caught", e.getMessage());
                }
            }
            sendNotification(msg_from);
        }
    }

    private void sendNotification(String msg) {
        SharedValues.paymentConfirmation = msg;
    }
}
*/
