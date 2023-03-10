package com.example.paymentgateway;

import java.io.Serializable;

public class PaymentRequest implements Serializable {

    private String amount;
    private String intent;

    public String toString(){
        return "PaymentRequest{"+
                "amount='"+amount+'\''+
                ",intent='"+intent+'\''+
                '}';
    }
    public String getAmount(){return amount;}
    public void setAmount(String amount){this.amount=amount;}

    public String getIntent(){return intent;}
    public void setIntent(String intent){this.intent=intent;}

}
