package com.mralex6204.android.tipcalc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mralex6204.android.tipcalc.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipDetailActivity extends AppCompatActivity {

    @BindView(R.id.txtBillTotal)
        TextView    txtBillTotal;
    @BindView(R.id.txtTip)
        TextView    txtTip;
    @BindView(R.id.txtTimeStamp)
    TextView    txtTimeStamp;

    public final static  String
                                TIP_KEY         =   "tip",
                                DATE_KEY        =   "timestap",
                                BILL_TOTAL_KEY  =   "total";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);

        //===>Crear un objeto intent para poder leer la informacion recibida
        //atravez del intent
        Intent i = this.getIntent();

        //===>Dar formato al los values
        String
                strTotal = String.format(getString(R.string.tipdetail_message_bill),
                                        i.getDoubleExtra(BILL_TOTAL_KEY,0d)),
                strTip = String.format(getString(R.string.global_message_tip),
                        i.getDoubleExtra(TIP_KEY,0d));

        //===>Mostrar los datos leidos
        txtBillTotal.setText(strTotal);
        txtTip.setText(strTip);
        txtTimeStamp.setText(i.getStringExtra(DATE_KEY));

    }
}
