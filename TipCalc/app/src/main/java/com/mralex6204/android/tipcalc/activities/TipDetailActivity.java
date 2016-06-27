package com.mralex6204.android.tipcalc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mralex6204.android.tipcalc.MainActivity;
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
    @BindView(R.id.toolbar)
    Toolbar tooBar;

    public final static  String
                                TIP_KEY         =   "tip",
                                DATE_KEY        =   "timestap",
                                BILL_TOTAL_KEY  =   "total";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);

        this.setSupportActionBar(tooBar);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        this.getMenuInflater().inflate(R.menu.menu_tip_detail,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_go_back){
            this.finish();
//            Intent mainActivity =new Intent(this.getApplicationContext(), MainActivity.class);
//            mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(mainActivity);
        }

        return super.onOptionsItemSelected(item);
    }
}
