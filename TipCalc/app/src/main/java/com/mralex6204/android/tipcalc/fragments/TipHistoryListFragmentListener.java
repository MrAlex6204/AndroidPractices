package com.mralex6204.android.tipcalc.fragments;

import com.mralex6204.android.tipcalc.models.TipRecord;

/**
 * Created by oavera on 6/06/16.
 */
public interface TipHistoryListFragmentListener {

    void addToList(TipRecord record);
    void clearList();

}
