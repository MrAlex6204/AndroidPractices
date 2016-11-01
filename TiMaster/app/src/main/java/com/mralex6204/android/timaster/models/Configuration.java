package com.mralex6204.android.timaster.models;

import android.graphics.PorterDuff;

import com.mralex6204.android.timaster.db.Column;
import com.mralex6204.android.timaster.db.Model;

/**
 * Created by oavera on 25/10/16.
 */

public class Configuration extends Model {

    public Column Name = new Column("Name", Column.Type.TEXT,true);
    public Column Value = new Column("Value", Column.Type.TEXT);

    public Configuration() {
        super("TblConfig");
        onBuild();
    }

    @Override
    protected void onBuild() {

        this.Columns.add(Name);
        this.Columns.add(Value);

    }

    public void setName(String name){
        this.Name.Value = name;
    }

    public String getName(){
        return (String)Name.Value;

    }

    public void setValue(String value){
        this.Value.Value = value;
    }

    public String getValue(){
       return (String) this.Value.Value;
    }

    public void set(String name,String value){
        this.Name.Value = name;
        this.Value.Value = value;
    }



}
