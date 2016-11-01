package com.mralex6204.android.timaster.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;

/**
 * Created by oavera on 20/10/16.
 */

public abstract class Model {

    public enum Query {
        AND, LIKE
    }

    private boolean Exists = false;
    public String TableName;
    public ArrayList<Column> Columns = new ArrayList<Column>();


    public Model(String TableName) {
        this.TableName = TableName;
    }

    protected abstract void onBuild();

    protected void setExists(boolean exists) {
        Exists = exists;
    }

    public String getCreateString() {
        String columns = "";
        String primaryKeysColumns = "";
        boolean containsPrimaryKeys = false;

        for (Column iColumn : this.Columns) {

            columns += iColumn.toString() + ",";

            if (iColumn.isPrimaryKey()) {
                containsPrimaryKeys = true;
            }

        }


        if (containsPrimaryKeys) {
            primaryKeysColumns += " ,PRIMARY KEY(";
            for (Column iColumn : this.Columns) {

                if (iColumn.isPrimaryKey() || iColumn.isAutoIncrement()) {
                    primaryKeysColumns += iColumn.Name + ",";
                }

            }

            if (primaryKeysColumns.lastIndexOf(',') > -1) {
                int idx = primaryKeysColumns.lastIndexOf(',');
                primaryKeysColumns = primaryKeysColumns.substring(0, idx);
            }

            primaryKeysColumns += ")";

        }

        if (columns.lastIndexOf(',') > -1) {
            int idx = columns.lastIndexOf(',');
            columns = columns.substring(0, idx - 1);
        }

        return String.format("CREATE TABLE IF NOT EXISTS %s (%s %s)", this.TableName, columns, primaryKeysColumns);

    }

    public String getDropString() {

        return String.format("DROP TABLE IF EXISTS %s", this.TableName);

    }

    protected String getWhereString(Query filterType) {
        String query = "";
        String statement = "";

        switch (filterType) {

            case AND:
                statement = "AND";
                break;
            case LIKE:
                statement = "LIKE";
                break;

        }


        for (Column iColumn : this.Columns) {
            if (iColumn.isPrimaryKey() || iColumn.isAutoIncrement()) {

                if (iColumn.Value != null) {
                    query += String.format(" %s = ? %s", iColumn.Name, statement);
                }

            }
        }
        if (query.lastIndexOf(statement) > -1) {
            query = query.substring(0, query.length() - (statement.length() + 1));
        }

        return query;
    }

    protected ContentValues getContentValues() {
        ContentValues tableValues = new ContentValues();

        for (Column iColumn : this.Columns) {

            if (iColumn.isAutoIncrement()) {

                tableValues.putNull(iColumn.Name);

            } else {

                switch (iColumn.ColumnType) {

                    case TEXT:
                        tableValues.put(iColumn.Name, (String) iColumn.Value);
                        break;
                    case TIME_STAMP:
                        tableValues.put(iColumn.Name, (Long) iColumn.Value);
                        break;
                    case INTEGER:
                        tableValues.put(iColumn.Name, (Integer) iColumn.Value);
                        break;
                    case FLOAT:
                        tableValues.put(iColumn.Name, (Float) iColumn.Value);
                        break;

                }

            }

        }

        return tableValues;
    }

    public boolean exists() {

        return Exists;
    }

    public String[] getColumns() {
        ArrayList<String> columns = new ArrayList<>();

        for (Column iColumn : this.Columns) {
            columns.add(iColumn.Name);
        }

        return columns.toArray(new String[columns.size()]);
    }

    public String[] getWhereValues() {
        ArrayList<String> values = new ArrayList<>();

        for (Column iColumn : this.Columns) {
            if (iColumn.isPrimaryKey() || iColumn.isAutoIncrement()) {
                if (iColumn.Value != null) {
                    values.add(iColumn.Value.toString());
                }
            }
        }

        return values.toArray(new String[values.size()]);
    }

    public Model getModel() {
        return this;
    }

    public static boolean CreateModel(SQLiteDatabase dbContext, Model tableModel) {
        String createString = tableModel.getCreateString();

        Log.i("Create Table", createString);
        dbContext.execSQL(createString);
        return true;
    }

    public static boolean DropModel(SQLiteDatabase dbContext, Model tableModel) {
        String dropString = tableModel.getDropString();

        Log.i("Drop Table", dropString);
        dbContext.execSQL(dropString);
        return true;
    }

    public static boolean Exists(SQLiteDatabase dbContext, Model record) {
        int count = 0;
        Cursor cursor;

        cursor = dbContext.query(record.TableName, record.getColumns(), record.getWhereString(Query.AND), record.getWhereValues(), null, null, null);

        count = cursor.getCount();
        cursor.close();

        return (count > 0); //Returns True if count was greater than 0

    }

    public static boolean Update(SQLiteDatabase dbContext, Model record) {
        int count = 0;

        if(Exists(dbContext,record)){
            count = dbContext.update(record.TableName, record.getContentValues(), record.getWhereString(Query.AND), record.getWhereValues());
        }else{
            Insert(dbContext,record);
            count = 1;
        }


        return (count > 0);
    }

    public static boolean Insert(SQLiteDatabase dbContext, Model record) {
        long count = 0;

        count = dbContext.insert(record.TableName, null, record.getContentValues());

        return (count > 0);
    }

    public static Model FindById(SQLiteDatabase dbContext, Model tableModel) {
        Cursor cursor;

        cursor = dbContext.query(tableModel.TableName, tableModel.getColumns(), tableModel.getWhereString(Query.AND), tableModel.getWhereValues(), null, null, null);

        if (cursor.getCount() > 0) {

            tableModel.setExists(true);
            cursor.moveToFirst();

            for (Column iColumn : tableModel.Columns) {
                int idx = cursor.getColumnIndexOrThrow(iColumn.Name);

                switch (iColumn.ColumnType) {

                    case TEXT:
                        iColumn.Value = cursor.getString(idx);
                        break;
                    case BOOLEAN:
                        iColumn.Value = cursor.getInt(idx) > 0;
                        break;
                    case TIME_STAMP:
                        iColumn.Value = cursor.getLong(idx);
                        break;
                    case INTEGER:
                        iColumn.Value = cursor.getInt(idx);
                        break;
                    case FLOAT:
                        iColumn.Value = cursor.getFloat(idx);
                        break;

                }

            }

        } else {
            tableModel.setExists(false);
        }

        cursor.close();

        return tableModel;
    }

    public static ArrayList<Model> Like(SQLiteDatabase dbContext, Model tableModel,String columnName,String value) {
        ArrayList<Model> tableResults = new ArrayList<Model>();
        Cursor cursor;

        cursor = dbContext.query(tableModel.TableName, tableModel.getColumns(), String.format(" %s LIKE ?",columnName),new String[]{value}, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Model iModel = tableModel;

                iModel.setExists(true);

                for (Column iColumn : iModel.Columns) {
                    int idx = cursor.getColumnIndexOrThrow(iColumn.Name);

                    switch (iColumn.ColumnType) {

                        case TEXT:
                            iColumn.Value = cursor.getString(idx);
                            break;
                        case BOOLEAN:
                            iColumn.Value = cursor.getInt(idx) > 0;
                            break;
                        case TIME_STAMP:
                            iColumn.Value = cursor.getLong(idx);
                            break;
                        case INTEGER:
                            iColumn.Value = cursor.getInt(idx);
                            break;
                        case FLOAT:
                            iColumn.Value = cursor.getFloat(idx);
                            break;

                    }

                }

                tableResults.add(iModel);

            }


        } else {
            tableModel.setExists(false);
        }

        cursor.close();

        return tableResults;
    }

    public static ArrayList<Model> GetAll(SQLiteDatabase dbContext, Model tableModel) {
        ArrayList<Model> tableResults = new ArrayList<Model>();
        Cursor cursor;

        cursor = dbContext.query(tableModel.TableName, tableModel.getColumns(), null, null, null, null, null);

        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Model iModel = tableModel;

                iModel.setExists(true);

                for (Column iColumn : iModel.Columns) {
                    int idx = cursor.getColumnIndexOrThrow(iColumn.Name);

                    switch (iColumn.ColumnType) {

                        case TEXT:
                            iColumn.Value = cursor.getString(idx);
                            break;
                        case BOOLEAN:
                            iColumn.Value = cursor.getInt(idx) > 0;
                            break;
                        case TIME_STAMP:
                            iColumn.Value = cursor.getLong(idx);
                            break;
                        case INTEGER:
                            iColumn.Value = cursor.getInt(idx);
                            break;
                        case FLOAT:
                            iColumn.Value = cursor.getFloat(idx);
                            break;

                    }

                }

                tableResults.add(iModel);

            }


        } else {
            tableModel.setExists(false);
        }

        cursor.close();

        return tableResults;
    }


}
