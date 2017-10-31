package com.lddm.tp2.data;

import android.provider.BaseColumns;

/**
 * Created by coras on 29/10/2017.
 */

public final class DisciplinaContract {

    private DisciplinaContract(){}

    public static abstract class DisciplinaEntry implements BaseColumns{

        public static final String TABLE_NAME ="disciplinas";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME ="nome";

        private static final String TEXT_TYPE = "TEXT";
        private static final String COMMA_SEP = ",";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + DisciplinaEntry.TABLE_NAME + " (" +
                        DisciplinaEntry._ID + " INTEGER PRIMARY KEY,"+
                        DisciplinaEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                        " )";
        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + DisciplinaEntry.TABLE_NAME;

    }


}
