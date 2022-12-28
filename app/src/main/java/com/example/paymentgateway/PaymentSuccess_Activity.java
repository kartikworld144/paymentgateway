package com.example.paymentgateway;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class PaymentSuccess_Activity extends AppCompatActivity {

    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_sucess);

        tvResult = findViewById(R.id.tvResult);
        if (getIntent().getExtras() == null) {
            tvResult.setText("Failed to get data from bKash");
            return;
        } else {
            tvResult.setText(
                    "TransactionID" + getIntent().getExtras().getString("TRANSACTION_ID") + "\n\n" +
                            "PaidAmount= " + getIntent().getExtras().getString("PAID_AMOUNT") + "\n\n" +
                            "OtherData= " + getIntent().getExtras().getString("PAYMENT_SERIALIZE") + "\n\n"
            );
        }
    }

    @Override
    public void onBackPressed() {
        popupPaymentCancelAlert();
    }

    private void popupPaymentCancelAlert() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Want to cancel progress?");
        alert.setCancelable(false);
        alert.setIcon(R.drawable.ic_launcher_background);
        alert.setTitle("Alert!");
        alert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PaymentSuccess_Activity.super.onBackPressed();
                    }


                });
        alert.setNegativeButton("Cancel",
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