package com.mralex6204.android.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mralex6204.android.tipcalc.R;
import com.mralex6204.android.tipcalc.models.TipRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by oavera on 9/06/16.
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder> {
    private Context context;
    private List<TipRecord> dataset;
    private OnItemClickListener onItemClickListener;


    public  TipAdapter(Context context,List<TipRecord> dataset,OnItemClickListener onItemClickListener){

        this.context = context;
        this.dataset = dataset;
        this.onItemClickListener = onItemClickListener;

    }

    public  TipAdapter(Context context,OnItemClickListener onItemClickListener){

        this.context = context;
        this.dataset = new ArrayList<TipRecord>();
        this.onItemClickListener = onItemClickListener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecord element = dataset.get(position);
        String strTip = String.format("Propina %,.1f",element.getTip());

        Log.e("Tip percentage ",strTip);
        holder.txtContent.setText(strTip);
        holder.txtTimeStamp.setText(element.getDateFormatted());
        holder.setOnItemClickListener(element,this.onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(TipRecord record) {

        Log.d("Adding Record",record.toString());
        dataset.add(0,record);
        notifyDataSetChanged();
        Log.d("Total Items", Integer.toString(dataset.size()));


    }

    public void clear() {

        dataset.clear();
        notifyDataSetChanged();

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.txtContent)
        TextView txtContent;

        @BindView(R.id.txtTimeStamp)
        TextView txtTimeStamp;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final TipRecord element, final OnItemClickListener onItemClickListener) {

            itemView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    onItemClickListener.onItemClick(element);
                }

            });

        }
    }


}
