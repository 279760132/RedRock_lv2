package com.redrock.liye.redrock_lv2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.redrock.liye.redrock_lv2.model.CityHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by a on 2016/2/21.
 */
public class CoolWeatherDBHelper extends SQLiteOpenHelper {
    private Context ctx;
    SQLiteDatabase myDataBase;

    public CoolWeatherDBHelper (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        ctx = context;
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        myDataBase = db;
        try {
            initDataBase();
            Log.i("redrock", "A");
        } catch (IOException e) {
            Log.i("redrock", "B"+e.getMessage());

            e.printStackTrace();
        }
    }

    /**
     * inital your database from your local res-raw-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled.
     * */
    private void initDataBase() throws IOException {
        InputStream myInput = ctx.getResources().getAssets().open("citycode.sql");
        InputStreamReader reader = new InputStreamReader(myInput);
        BufferedReader breader = new BufferedReader(reader);

        String str;

        while ((str = breader.readLine()) != null) {
            myDataBase.execSQL(str); //exec your SQL line by line.
        }

        myInput.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            initDataBase();
            Log.i("redrock", "A");
        } catch (IOException e) {
            Log.i("redrock", "B"+e.getMessage());

            e.printStackTrace();
        }
    }
}