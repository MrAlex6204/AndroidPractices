package com.mralex6204.android.timaster.db;

/**
 * Created by oavera on 20/10/16.
 */

public class Column {

    public enum Type {
        TEXT, INTEGER, FLOAT, BOOLEAN, TIME_STAMP
    }

    public String Name;
    public Column.Type ColumnType;
    public Object Value = null;

    private boolean IsAutoIncrement = false;
    private String Default = null;
    private boolean isPrimaryKey = false;


    public Column(String Name, Column.Type ColumnType) {
        this.Name = Name;
        this.ColumnType = ColumnType;
    }

    public Column(String Name, Column.Type ColumnType, boolean IsPrimaryKey) {

        this.isPrimaryKey = IsPrimaryKey;
        this.Name = Name;
        this.ColumnType = ColumnType;

    }

    public Column(String Name, Column.Type ColumnType, String Default) {

        this.Name = Name;
        this.ColumnType = ColumnType;
        this.Default = Default;

    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public boolean isAutoIncrement() {
        return IsAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        IsAutoIncrement = autoIncrement;
    }

    public String toString() {
        String columnDefinition = "";

        switch (this.ColumnType) {

            case TEXT:
                columnDefinition = "TEXT";
                break;
            case INTEGER:
                columnDefinition = "INTEGER";
                break;
            case TIME_STAMP:
                columnDefinition = "TIMESTAMP";
                break;
            case FLOAT:
                columnDefinition = "FLOAT";
                break;
            case BOOLEAN:
                columnDefinition = "BOOLEAN";
                break;

        }

        if(this.isPrimaryKey()){
            columnDefinition += " NOT NULL";
        }


        if (this.Default != null) {

            columnDefinition += String.format(" DEFAULT %s", this.Default);
        }


        return String.format(" %s %s ", this.Name, columnDefinition);
    }

}
