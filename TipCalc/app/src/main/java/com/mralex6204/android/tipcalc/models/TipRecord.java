package com.mralex6204.android.tipcalc.models;

/**
 * Created by oavera on 9/06/16.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

public class TipRecord {
    private double bill;
    private int tipPercentage;
    private Date timestamp;


    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getTipPercentage() {
        return tipPercentage;
    }

    public void setTipPercentage(int tipPercentage) {
        this.tipPercentage = tipPercentage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getTip() {
        return bill * (tipPercentage / 100d);
    }

    public String getDateFormatted() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");

        return simpleDateFormat.format(timestamp);
    }

    @Override
    public String toString(){
        return  "Date - "+ this.getDateFormatted()+" - " +this.bill + " * ("+this.tipPercentage+"/100) = "+this.getTip();
    }
}
