package com.example.testapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ConcertDBManager extends SQLiteOpenHelper {


    static final String DB_CONCERT = "concert.db";
    static final String TABLE_CONCERT = "concerts";
    static final int DB_VERSION = 1;

    Context mContext = null;

    private static ConcertDBManager mDBManager = null;

    // concert.db 이름으로 DB 생성
    public static ConcertDBManager getInstance( Context context ){
        if (mDBManager == null){
            mDBManager = new ConcertDBManager( context, DB_CONCERT, null, DB_VERSION );
        }
        return mDBManager;
    }

    private ConcertDBManager (Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version ){

        super(context, dbName, factory, version);
        mContext = context;

    }

    //@override
    // TABLE_CONCERT 으로 concert.db에 테이블 생성
    public void onCreate( SQLiteDatabase db ){

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CONCERT + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, img TEXT, start_date TEXT, end_date TEXT, time TEXT, price TEXT, place TEXT, theater TEXT, playtime TEXT, genre TEXT, search TEXT)" );

    }

    //@override
    public void onOpen (SQLiteDatabase db){

        super.onOpen(db);

    }

    //@override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){

        {
            if(oldVersion < newVersion ){
                db.execSQL ("DROP TABLE IF EXISTS " + TABLE_CONCERT );
                onCreate(db);
            }
        }
    }

    public void resetTable( SQLiteDatabase db ){
        db.execSQL ("DROP TABLE IF EXISTS " + TABLE_CONCERT );
        onCreate(db);
    }

    public long insert(ContentValues addRowValue) {

        return getWritableDatabase().insert(TABLE_CONCERT, null, addRowValue);

    }

    public int insertAll ( ContentValues[] values ){

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        for(ContentValues contentValues : values){
            db.insert( TABLE_CONCERT, null, contentValues );
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        Log.d("Data Inserted", "DATA INSERT_ALL TASK COMPLETED");
        return values.length;
    }

    public Cursor query( String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy ){

        return getReadableDatabase().query( TABLE_CONCERT, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public int update ( ContentValues updateRowValue, String whereClause, String[] whereArgs ){

        return getWritableDatabase().update( TABLE_CONCERT, updateRowValue, whereClause, whereArgs);

    }

    public int delete(String whereClause, String[] whereArgs ){

        return getWritableDatabase().delete(TABLE_CONCERT, whereClause, whereArgs);
    }



}

