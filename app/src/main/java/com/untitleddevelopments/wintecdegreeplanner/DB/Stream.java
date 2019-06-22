package com.untitleddevelopments.wintecdegreeplanner.DB;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class Stream {
    private int stream_ID;
    private String name;
    private String iconURI;

    //constructor
    public Stream(int stream_ID, String name, String iconURI) {
        this.stream_ID = stream_ID;
        this.name = name;
        this.iconURI = iconURI;
    }
    //constructor passing in an ID (to get from DB
    public Stream(int stream_ID) {
        this.stream_ID = stream_ID;
        String query = "SELECT * FROM " + DBHelper.TBL_STREAM +
                " WHERE " + DBHelper.STREAM_ID + " = " + stream_ID;
        Log.d(TAG, "getCurrentStream: " + query);
        DBManager.getInstance().openDatabase();
        Cursor cursor = DBManager.getInstance().getDetails(query);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            this.stream_ID = stream_ID;
            this.name = cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_NAME));
            this.iconURI = (cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_ICONURI)));
        }
    }

    public static ArrayList<Stream> getAllStreams() {

        ArrayList<Stream> streams = new ArrayList<Stream>();

        String query = "SELECT * FROM " + DBHelper.TBL_STREAM;

        Log.d(TAG, "-- getAllStreams: " + query);

        DBManager.getInstance().openDatabase();

        Cursor cursor = DBManager.getInstance().getDetails(query);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {


                int streamID = cursor.getInt(cursor.getColumnIndex(DBHelper.STREAM_ID));
                String streamName = cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_NAME));
                String streamIconURI = cursor.getString(cursor.getColumnIndex(DBHelper.STREAM_ICONURI));

                streams.add(new Stream(streamID, streamName, streamIconURI));

                String msg = String.format("-- Adding stream: ID=%d, name=%s, iconurl=%s", streamID, streamName, streamIconURI);
                Log.d(TAG, msg);

                cursor.moveToNext();
            }
        }

        return streams;

    }

    //getter setter

    public int getStream_ID() {
        return stream_ID;
    }

    public void setStream_ID(int stream_ID) {
        this.stream_ID = stream_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconURI() {
        return iconURI;
    }

    public void setIconURI(String iconURI) {
        this.iconURI = iconURI;
    }
}
