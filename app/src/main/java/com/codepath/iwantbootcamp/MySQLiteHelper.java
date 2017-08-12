package com.codepath.iwantbootcamp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Jeonsim on 2017-08-12.
 */

public class MySQLiteHelper  extends SQLiteOpenHelper {
    String TAG = "MySQLiteHelper";
    Context gContext;

    public MySQLiteHelper(Context context) {

        super(context, GlobalInfo.DATABASE_NAME, null, GlobalInfo.DATABASE_VERSION);
        gContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create table
        String CREATE_SQL = "create table " + GlobalInfo.TABLE_NAME + "("
                + "ITEM TEXT"
                + ")";
        try {
            db.execSQL(CREATE_SQL);
        } catch(Exception ex) {
            Log.e(TAG, "Exception in CREATE_SQL", ex);
        }

        Toast.makeText(gContext, "Table Create Done", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
