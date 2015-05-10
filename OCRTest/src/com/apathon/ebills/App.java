package com.apathon.ebills;

import java.io.IOException;
import java.sql.SQLException;

import android.app.Application;
import android.content.Context;
import ebills.db.DataBaseHelper;


/**
 * Created by suraj on 12/2/2014.
 */
public class App extends Application {

    private static App instance;
    private static DataBaseHelper db;

    @Override
    public void onCreate() {
        super.onCreate();
        App.instance = this;
        db = new DataBaseHelper(getContext());
        try {
            doDbCall(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static final Context getContext() {
        return getInstance().getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onTerminate() {
        db.close();
        super.onTerminate();
    }

    public void doDbCall(DataBaseHelper db) throws SQLException {
        try {
            db.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            db.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }
    }

    public static DataBaseHelper getDb() {
        return db;
    }



}
