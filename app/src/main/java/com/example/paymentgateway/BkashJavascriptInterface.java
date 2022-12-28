package com.example.paymentgateway;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;

public class BkashJavascriptInterface {

    Context context;

    //instantiate the interface and set the context
    public BkashJavascriptInterface(Context c) {
        context = c;
    }

    //show toast from webpage
    @JavascriptInterface
    public void OnPaymentSuccess(String data) {

        //filtering received data coming from bKas h end point
        String[] paymentData = data.split("&");
        String paymentID = paymentData[0].trim().replace("PaymentID", "").trim();
        String transactionID = paymentData[0].trim().replace("PaymentID", "").trim();
        String amount = paymentData[0].trim().replace("Amount", "").trim();


    //payment success
    Intent intent = new Intent(context, PaymentSuccess_Activity.class);
    intent.putExtra("TRANSACTION_ID",transactionID);
    intent.putExtra("PAID_AMOUNT",amount);
    intent.putExtra("PAYMENT_SERIALIZE",data);
    context.startActivity(intent);
    }


}
