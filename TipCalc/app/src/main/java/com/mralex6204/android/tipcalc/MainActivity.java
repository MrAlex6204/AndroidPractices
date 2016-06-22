package com.mralex6204.android.tipcalc;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.mralex6204.android.tipcalc.activities.TipCalcApp;
import com.mralex6204.android.tipcalc.fragments.TipHistoryListFragment;
import com.mralex6204.android.tipcalc.fragments.TipHistoryListFragmentListener;
import com.mralex6204.android.tipcalc.models.TipRecord;

import java.util.Date;

import butterknife.*;

public class MainActivity extends AppCompatActivity {

    private final int DEFAULT_TIP_PERCENTAGE = 10;
    private final int TIP_STEP_CHANGE = 1;

    private TipHistoryListFragmentListener fragmentListener;

    @BindView(R.id.inputBill)
        EditText inputBill;
    @BindView(R.id.txtTip)
        TextView txtTip;
    @BindView(R.id.inputPercentage)
        TextView inputPercentage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager()
                                            .findFragmentById(R.id.fragmentList);

        fragment.setRetainInstance(true);
        fragmentListener = (TipHistoryListFragmentListener)fragment;

    }

    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit() {
        hideKeyboard();
        String stringInputTotal = inputBill.getText().toString().trim();

        if (!stringInputTotal.isEmpty()) {
            double total = Double.parseDouble(stringInputTotal);
            int tipPercentage = getTipPercentage();

            TipRecord tipRecord = new TipRecord();

            tipRecord.setBill(total);
            tipRecord.setTipPercentage(tipPercentage);
            tipRecord.setTimestamp(new Date());

            String strTip = String.format(getString(R.string.global_message_tip),
                                          tipRecord.getTip());

            txtTip.setVisibility(View.VISIBLE);
            txtTip.setText(strTip);

            //===>Notificar atravez del fragment
            fragmentListener.addToList(tipRecord);


        }

    }

    @OnClick(R.id.btnClear)
    public void handleClickClear(){
        fragmentListener.clearList();
    }

    private void handleTipChange(int change){
        int tipPercentage = this.getTipPercentage();

        tipPercentage += change;

        if(tipPercentage > 0 ){
            inputPercentage.setText(String.valueOf(tipPercentage));
        }


    }

    @OnClick(R.id.btnIncrease)
    public void handleClickIncrease(){
        hideKeyboard();
        handleTipChange(TIP_STEP_CHANGE);

    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease(){
        hideKeyboard();
        handleTipChange(-TIP_STEP_CHANGE);
    }

    private int getTipPercentage() {
        int tipPercentage = DEFAULT_TIP_PERCENTAGE;
        String strInputTipPercentage = inputPercentage.getText().toString().trim();

        if(!strInputTipPercentage.isEmpty()){
            tipPercentage = Integer.parseInt(strInputTipPercentage);
        }else{
            //===>Set tip percentage if the input is empty
            inputPercentage.setText(String.valueOf(tipPercentage));
        }

        return tipPercentage;
    }

    private void hideKeyboard() {
        InputMethodManager in = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        try {

            in.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        } catch (NullPointerException npe) {
            Log.e(this.getLocalClassName(), "Click en Submit");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_about) {
            about();

        }
        return super.onOptionsItemSelected(item);
    }

    private void about() {
//        TipCalcApp app = (TipCalcApp)this.getApplication();
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(TipCalcApp.PROFILE_URL));
        startActivity(i);
    }

}
