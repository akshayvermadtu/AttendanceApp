package com.akshayvermadtugmail.dtusmartattender;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class mydbhandler extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "roll.db";
    public static final String TABLE_NAME = "roll";
    public static final String TABLE_NAME_2 = "roll2";
    public static final String TABLE_NAME_3 = "roll3";
    public static final String TABLE_NAME_4 = "roll4";
    public static final String TABLE_NAME_5 = "roll5";
    public static final String TABLE_NAME_6 = "roll6";
    public static final String TABLE_NAME_7 = "roll7";
    public static final String TABLE_NAME_8 = "roll8";
    public static final String TABLE_NAME_9= "roll9";
    public static final String TABLE_NAME_10 = "roll10";


    public static final String ROLL_NO="roll_no_student" ;
    public static final String STATUS = "status";


    public mydbhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                ROLL_NO+ " INTEGER " +
                STATUS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST " + TABLE_NAME);
        onCreate(db);
    }

    //ADDING ROWS

    public void addroll(rollnos roll) {
        ContentValues values = new ContentValues();
        //values.put(ROLL_NO, roll.getRoll_no_student());
        values.put(STATUS, roll.getStatus());


        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //DELETE TABLE
    /*
    public void deleteroll(String status) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + "WHERE" + ROLL_NO + "=\"" + status + "\"");

    }
    */

    //PRINT DATABASE
    String datatostring() {

        String dbstring = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE 1";

        //cursor

        Cursor c = db.rawQuery(query,null);
        //move to 1st
        c.moveToLast();

        dbstring +=c.getString(c.getColumnIndex("status"));
        dbstring+="\n";

        /*
        while(!c.isAfterLast()){

            if(c.getString(c.getColumnIndex("status"))!=null){
                dbstring +=c.getString(c.getColumnIndex("status"));
                dbstring+="\n";
            }
        }
        */
        db.close();

        return dbstring;
    }
    /*
    public String datatostring2() {

        String dbstring = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT ALL FROM" + TABLE_NAME + "WHERE 1";

        //cursor

        Cursor c =db.rawQuery(query,null);
        //move to 1st
        c.moveToFirst();

        while(!c.isAfterLast()){

            if(c.getString(c.getColumnIndex("status"))!=null  && c.getString(c.getColumnIndex("roll_no_student"))!=null){
                dbstring +=c.getString(c.getColumnIndex("roll_no_student"))+c.getString(c.getColumnIndex("status")) ;
                dbstring+="\n";
            }


        }
        db.close();
        return dbstring;
    }
    */

}
