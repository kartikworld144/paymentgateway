package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText etAmount;
    Button payButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etAmount=findViewById(R.id.etAmount);
        payButton=findViewById(R.id.payButton);


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInputs();
            }

            private void checkInputs() {
                String s=etAmount.getText().toString().trim();

                //checking validity
                double amount =0.0;
                try {
                    amount=Double.parseDouble(s);
                }catch (Exception e){
                    amount=0.0;

                }
                if (amount<1){
                    etAmount.setError("You to pay at least BDT 500 ");
                    etAmount.requestFocus();
                }
                else {
                    Intent intent=new Intent(MainActivity.this,BkashActivity.class);
                    intent.putExtra("Amount", String.valueOf(amount));//sent to bKas_h Activity
                    startActivity(intent);
                }
            }
        });



    }
}