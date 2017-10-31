package com.lddm.tp2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by coras on 29/10/2017.
 */

public class DisciplinaDbHelper extends SQLiteOpenHelper {

     public static final String LOG_TAG = DisciplinaDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "disciplinas.db";

    private static final int DATABASE_VERSION = 1;

    public DisciplinaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            // Execute the SQL statement

      String SQL_CREATE_TABLE =
              "CREATE TABLE " + DisciplinaContract.DisciplinaEntry.TABLE_NAME + " (" +
                        DisciplinaContract.DisciplinaEntry._ID + " INTEGER PRIMARY KEY,"+
                        DisciplinaContract.DisciplinaEntry.COLUMN_NAME +
                      " )";
        db.execSQL(SQL_CREATE_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}

