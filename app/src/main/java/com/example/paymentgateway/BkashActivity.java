package com.example.paymentgateway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.zip.CheckedOutputStream;

public class BkashActivity extends AppCompatActivity {

    WebView webPayment;
    ProgressBar progressBar;
    String amount = "";
    String request = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bkash);

        webPayment = findViewById(R.id.webPayment);
        progressBar = findViewById(R.id.progressBar);


        if (getIntent().getExtras() == null) {
            //no data
            Toast.makeText(this, "Amount is empty, You can not pay through bKash. Try again", Toast.LENGTH_SHORT).show();
            return;
        } else {
            amount = getIntent().getExtras().getString("Amount");//make sure your is some as Main activity.


        //Create a payment request model
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount(amount);
        paymentRequest.setIntent("sale");

        Gson gson = new Gson();
        request = gson.toJson(paymentRequest);

        WebSettings webSettings = webPayment.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Enabling some setting so that browser can work properly
        webPayment.getSettings().setLoadsImagesAutomatically(true);
        webPayment.getSettings().setJavaScriptEnabled(true);
        webPayment.getSettings().setAllowFileAccess(true);
        webPayment.getSettings().setLoadWithOverviewMode(true);
        webPayment.getSettings().setUseWideViewPort(true);
        webPayment.getSettings().setPluginState(WebSettings.PluginState.ON);
        webPayment.getSettings().setAllowFileAccess(true);
        webPayment.getSettings().setDatabaseEnabled(true);
        webPayment.getSettings().setSaveFormData(true);
        webPayment.getSettings().setLoadWithOverviewMode(true);


        webPayment.getSettings().setDomStorageEnabled(true);
        webPayment.getSettings().setCacheMode(webSettings.LOAD_NO_CACHE);
        // webPayment.getSettings().setAppCacheEnable(false);
        //-------------------new setting
        webPayment.getSettings().setBlockNetworkLoads(false);
        webPayment.getSettings().setDomStorageEnabled(true);
        //ZoomIn
        webPayment.zoomIn();
        webPayment.getSettings().setBuiltInZoomControls(true);

        //To control any kind of interaction from file
        webPayment.addJavascriptInterface(new BkashJavascriptInterface(BkashActivity.this), "KinYardsPaymentDATA");
        webPayment.loadUrl("https://KartikWorld.com/apps/bkash_payment.php");//api host link
        webPayment.setWebViewClient(new CheckoutWebViewClient());


        }
   }

  public class CheckoutWebViewClient extends WebViewClient {

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError sslError){
        handler.proceed();
    }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //log.d("External URL: ",url);
            if (url.equals("https://www.bkash.com/terms-and-conditions")){
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
            return super.shouldOverrideUrlLoading(view,url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon){
            progressBar.setVisibility(View.VISIBLE);
      }

      @Override
      public void onPageFinished(WebView view,String url){
        String paymentRequest = "{paymentRequest:"+request+"}";
        webPayment.loadUrl("javascript:callReconfigure("+paymentRequest+")");
        webPayment.loadUrl("javascript:clickPayButton()");
        progressBar.setVisibility(View.GONE);
      }
  }
  @Override
    public void onBackPressed(){
        popupPaymentCancelAlert();
  }

    private void popupPaymentCancelAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Want to cancel progress?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.ic_launcher_background);
        alert.setTitle("Alert!");
        alert.setPositiveButton( "Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(BkashActivity.this, "Payment Canceled", Toast.LENGTH_SHORT).show();
                    BkashActivity.super.onBackPressed();
                    }


                });
        alert.setNegativeButton( "Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                    }


                });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();

    }
}