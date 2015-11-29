package com.example.tgk.integration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CarbCalcDbAdapter {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Trips";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";

    private static final String TAG = "CarbCalcDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private final Context mCtx;

    public static final String TABLE_NAME_TRIP = "Trip";
    public static final String KEY_TRIP_ID = "_id";
    public static final String KEY_CATEGORY = "Category";
    public static final String KEY_VEHICLE_TYPE = "VehicleType";
    public static final String KEY_DISTANCE = "Distance";
    public static final String KEY_DATE = "Date";
    public static final String KEY_NOTE = "Note";
    public static final String KEY_SUMMARY = "Summary";


    private static final String SQL_CREATE_TRIPS_DATABASE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME_TRIP + " (" +
                    KEY_TRIP_ID + " INTEGER PRIMARY KEY autoincrement, " +
                    KEY_CATEGORY + TEXT_TYPE + ", "+ KEY_VEHICLE_TYPE  + TEXT_TYPE + ", " +
                    KEY_DISTANCE + TEXT_TYPE + ", " + KEY_DATE + TEXT_TYPE + ", " +
                    KEY_NOTE + TEXT_TYPE + ", " + KEY_SUMMARY + TEXT_TYPE +  // Any other options for the CREATE command
                    " );";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TRIP;



    /*public static abstract class ArticleReaderContract implements BaseColumns {
        public static final String TABLE_NAME = "Article";
        *//*public static final String KEY_ARTICLE_ID = "_id";*//*
        public static final String KEY_TITLE = "Title";
        public static final String KEY_BODY = "Body";
    }*/

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.v(TAG, SQL_CREATE_TRIPS_DATABASE);
            db.execSQL(SQL_CREATE_TRIPS_DATABASE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            Log.w(TAG, "Upgrading database from version " + i + " to "
                    + i1 + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRIP);
            onCreate(db);
        }

    }

    public CarbCalcDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public CarbCalcDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public Cursor fetchTripById (long id) throws SQLException {
        Log.w(TAG, String.valueOf(id));
        Cursor mCursor = null;
        if (id < 0)  {
            mCursor = mDb.query(TABLE_NAME_TRIP, new String[] {KEY_TRIP_ID,
                            KEY_CATEGORY, KEY_VEHICLE_TYPE, KEY_DISTANCE, KEY_DATE,
                            KEY_NOTE, KEY_SUMMARY},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, TABLE_NAME_TRIP, new String[] {KEY_TRIP_ID,
                            KEY_CATEGORY, KEY_VEHICLE_TYPE, KEY_DISTANCE, KEY_DATE,
                            KEY_NOTE, KEY_SUMMARY},
                    KEY_TRIP_ID + " = " + id, null, null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllTrips() {

        Cursor mCursor = mDb.query(TABLE_NAME_TRIP, new String[] {KEY_TRIP_ID,
                        KEY_CATEGORY, KEY_VEHICLE_TYPE, KEY_DISTANCE, KEY_DATE,
                        KEY_NOTE, KEY_SUMMARY},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public long insertTrip(String category, String vehicleType, long distance, String date,
                           String note, String summary) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY, category);
        initialValues.put(KEY_VEHICLE_TYPE, vehicleType);
        initialValues.put(KEY_DISTANCE, distance);
        initialValues.put(KEY_DATE, date);
        initialValues.put(KEY_NOTE, note);
        initialValues.put(KEY_SUMMARY, summary);

        return mDb.insert(TABLE_NAME_TRIP, null, initialValues);
    }

    public void removeTrip(long id) {
        // don't know if this works yet
        mDb.delete(TABLE_NAME_TRIP, "_id = " + id, null);
    }

    public boolean deleteAllArticles() {
        int doneDelete = 0;
        doneDelete = mDb.delete(TABLE_NAME_TRIP, null , null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }



}
