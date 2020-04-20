package com.example.scrollview_sinhvien;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myDBhelper extends SQLiteOpenHelper {
    private static final String DBname = "dbsv.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "SinhVien";
    private static final String ID = "_id";
    private static final String NAME = "name";
    private static final String YEAROB = "yearob";
    private SQLiteDatabase myDB;

    public myDBhelper(@Nullable Context context) {
        super(context, DBname, null, VERSION);
    }

    public static String getID() {
        return ID;
    }

    public static String getNAME() {
        return NAME;
    }

    public static String getYEAROB() {
        return YEAROB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryTB = "CREATE TABLE "+TABLE_NAME+"( "+
                ID + " INTEGER PRIMARY KEY, " +
                NAME + " TEXT NOT NULL, "+
                YEAROB + " INTEGER NOT NULL )";
        db.execSQL(queryTB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDB(){
        myDB = getWritableDatabase();
    }

    public void closeDB(){
        if (myDB!=null && myDB.isOpen()){
            myDB.close();
        }
    }

    public long Insert(int id, String name, int yearob){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID ,id);
        contentValues.put(NAME,name);
        contentValues.put(YEAROB,yearob);
        return myDB.insert(TABLE_NAME, null, contentValues);
    }

    public long Update(int id, String name, int yearob){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID ,id);
        contentValues.put(NAME,name);
        contentValues.put(YEAROB,yearob);
        String where = ID +" = "+id;
        return myDB.update(TABLE_NAME, contentValues, where, null);
    }

    public long Delete(int id){
        String where = ID +" = "+id;
        return myDB.delete(TABLE_NAME, where, null);
    }

    public Cursor getAllRecord(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        return myDB.rawQuery(query, null);
    }
}
