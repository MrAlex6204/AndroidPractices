package com.mralex6204.android.timaster.domain;

import android.content.Context;
import android.database.sqlite.*;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import com.mralex6204.android.timaster.db.*;
import com.mralex6204.android.timaster.models.*;

/**
 * Created by MrAlex6204 on 20/10/16.
 */

public class DbRepository extends SQLiteOpenHelper {
    private static DbRepository _instance = null;
    private final static String DB_NAME = "dbTiMaster";
    private final static int DB_VERSION = 1;

    protected DbRepository(Context Ctx) {
        super(Ctx, DB_NAME, null, DB_VERSION);
    }

    public ArrayList<Model> Models = new ArrayList<Model>();

    public SQLiteDatabase getDbContext() {
        return this.getReadableDatabase();
    }

    public String getConfigName(String configName) {
        Configuration config = new Configuration();

        config.setName(configName);

        config = (Configuration) Model.FindById(this.getDbContext(), config);

        return config.exists() ? config.getValue() : null;
    }

    public boolean sigUp(String userName, String password, String question, String questionAnswer) throws Exception {
        boolean success = false;

        Configuration
                userConfig = new Configuration(),
                passwordConfig = new Configuration(),
                questionConf = new Configuration(),
                questionAnswerConfig = new Configuration();

        userConfig.set(Config.USER_NAME, userName);
        passwordConfig.set(Config.USER_PASSWORD, password);
        questionConf.set(Config.USER_SECURITY_QUESTION, questionAnswer);
        questionAnswerConfig.set(Config.QUESTION_ANSWER, questionAnswer);

        success = Model.Update(this.getDbContext(), questionAnswerConfig) &
                Model.Update(this.getDbContext(), questionAnswerConfig) &
                Model.Update(this.getDbContext(), passwordConfig) &
                Model.Update(this.getDbContext(), userConfig);

        return success;
    }

    public boolean logIn(String userName, String password) {

        Configuration
                userConf = new Configuration(),
                passwordConf = new Configuration();

        userConf.setName(Config.USER_NAME);
        passwordConf.setName(Config.USER_PASSWORD);

        userConf = (Configuration) Model.FindById(this.getDbContext(), userConf.getModel());
        passwordConf = (Configuration) Model.FindById(this.getDbContext(), passwordConf.getModel());

        if(userName.trim().equals(userConf.getValue().trim()) && password.trim().equals(passwordConf.getValue().trim())){
             return true;
        }else{
            return false;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        for (Model iModel : Models) {
            String createTableQry = iModel.getCreateString();

            Log.i("TABLE " + iModel.TableName, createTableQry);
            db.execSQL(createTableQry);

        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        for (Model iModel : Models) {
            String dropTableQry = iModel.getDropString();

            Log.i("TABLE " + iModel.TableName, dropTableQry);

            db.execSQL(dropTableQry);
        }
        onCreate(db);
    }

    public void build() {//Call on build create function ti build the Repository tables
        onCreate(this.getReadableDatabase());
    }

    public static DbRepository getInstance(Context Ctx) {

        if (_instance == null) {//Make sure instance still is not created
            _instance = new DbRepository(Ctx);
        }

        return _instance;
    }

}
