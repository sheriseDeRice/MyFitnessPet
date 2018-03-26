package com.example.sherisesinyeelam.seprojectbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Class that interacts with database.
 * Created by Michal
 * Version 0.1
 * Date 03.2018
 */

public class DatabaseHandler extends SQLiteOpenHelper{
    private final Context context;
    SQLiteDatabase db;
    //Information of database
    private static String DB_PATH = "";
    private static String DB_NAME = "calories1.db";
    private static final String TABLE_CAL ="CaloriesIntake" ;
    private static int CurrentVersion = 3;

    public DatabaseHandler(Context context){

        super(context,DB_NAME,null, CurrentVersion);

        //Load Database on creation
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;

    }


    public ArrayList<Entry> readFromDB(String query) {
        //ToDo: remove logging to console
        Log.d("readFromDB",query);
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = query;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor == null) {
            return new ArrayList<>();
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return new ArrayList<>();
        }

        ArrayList<Entry> results = new ArrayList<>();
        while(!cursor.isAfterLast()){
            Entry entry = new Entry(cursor.getString(cursor.getColumnIndex("Name")),cursor.getInt(cursor.getColumnIndex("Calories")),cursor.getString(cursor.getColumnIndex("Date")));
            results.add(entry);
            Log.d("DB",entry.getName()+"//"+entry.getCalories()+"//"+entry.getDate());
            cursor.moveToNext();
        }
        cursor.close();

        return results;

    }

    public boolean writeToDB(String name, int calories, String date){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Calories", calories);
        values.put("Date", date);
        long newRowId = db.insert(TABLE_CAL, null, values);
        if(newRowId<=0){
            return false;
        }
        return true;
    }

    public void create() throws IOException {
        //Log.d("DBcheck", "create1");
        boolean dbExist = checkDataBase();
        if (dbExist) {
            dbUpdate();
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = context.getAssets().open(DB_NAME);

        String outFileName = DB_PATH + DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    private void dbUpdate() throws IOException {
        String path = DB_PATH + DB_NAME;

        SQLiteDatabase upd = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        int ver = upd.getVersion();
        if(ver!= CurrentVersion){
            upd.execSQL("DROP TABLE " + TABLE_CAL);
            copyDataBase();
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            e.printStackTrace();
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public boolean open() {
        try {
            String myPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
            return true;
        } catch (SQLException sqle) {
            db = null;
            return false;
        }
    }

    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
