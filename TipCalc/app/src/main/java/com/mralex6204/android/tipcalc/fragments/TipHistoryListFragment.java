package com.mralex6204.android.tipcalc.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mralex6204.android.tipcalc.R;
import com.mralex6204.android.tipcalc.activities.TipDetailActivity;
import com.mralex6204.android.tipcalc.adapters.OnItemClickListener;
import com.mralex6204.android.tipcalc.adapters.TipAdapter;
import com.mralex6204.android.tipcalc.models.TipRecord;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class TipHistoryListFragment extends Fragment implements TipHistoryListFragmentListener, OnItemClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private  TipAdapter adapter;


    public TipHistoryListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip_history_list, container, false);
        ButterKnife.bind(this, view);
        initAdapter();
        initRecyclerView();
        return view;
    }

    private void initAdapter() {

        if (adapter == null) {
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }

    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(this.adapter);
    }


    @Override
    public void addToList(TipRecord record) {
        adapter.add(record);
    }

    @Override
    public void clearList() {
        adapter.clear();
    }

    @Override
    public void onItemClick(TipRecord tipRecord) {
        //        Log.i("Click",tipRecord.toString());
        //        Toast.makeText(this.getActivity(),"Tip : "+tipRecord.toString(),Toast.LENGTH_LONG).show();
        Intent i = new Intent(this.getActivity(), TipDetailActivity.class);

        i.putExtra(TipDetailActivity.TIP_KEY,tipRecord.getTip());
        i.putExtra(TipDetailActivity.BILL_TOTAL_KEY,tipRecord.getBill());
        i.putExtra(TipDetailActivity.DATE_KEY,tipRecord.getDateFormatted());

        this.startActivity(i);

    }
}
