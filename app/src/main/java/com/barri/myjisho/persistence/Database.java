package com.barri.myjisho.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Barri on 20/03/2016.
 */
public class Database extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "Traducciones.db";
    private static final int DATABASE_VERSION = 1;

    public Database(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //TODO preparar upgrade
    }
}
