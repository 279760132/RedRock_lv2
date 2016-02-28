package com.redrock.liye.redrock_lv2.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.redrock.liye.redrock_lv2.R;
import com.redrock.liye.redrock_lv2.db.CoolWeatherDBHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.sax.SAXResult;

/**
 * Created by a on 2016/2/24.
 */
public class CityHelper {



    public List<Area> loadCounty(SQLiteDatabase db,int cityCode){
        String where = "level" + "='" + 3 +"' and "+"parentId"+"='"+cityCode+"'";
        List<Area> list = new ArrayList<>();
        Cursor cursor = db.query("citycode",null,where,null,null,null,"id");
        if (cursor.moveToFirst()){
            do {
                Area county = new Area();
                county.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
                county.setName(cursor.getString(cursor.getColumnIndex("name")));
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                county.setParentId(cursor.getInt(cursor.getColumnIndex("parentId")));
                list.add(county);
            }
            while (cursor.moveToNext());
        }
        return list;
    }
    public List<Area> loadCity(SQLiteDatabase db ,int provinceCode){
        String where = "level" + "='" + 2 +"' and "+"parentID"+"='"+provinceCode+"'";
        List<Area> list = new ArrayList<Area>();
        Cursor cursor = db.query("citycode",null,where,null,null,null,"id");
        if (cursor.moveToFirst()){
            do {
                Area city = new Area();
                city.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
                city.setName(cursor.getString(cursor.getColumnIndex("name")));
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                city.setParentId(cursor.getInt(cursor.getColumnIndex("parentId")));
                list.add(city);
            }
            while (cursor.moveToNext());
        }
        return list;
    }
    public List<Area> loadProvinces(SQLiteDatabase db){
        List<Area> list = new ArrayList<Area>();
        Cursor cursor = db.query("citycode",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (cursor.getInt(cursor.getColumnIndex("level")) == 1){

                    Area province = new Area();
                    province.setCid(cursor.getInt(cursor.getColumnIndex("cid")));
                    province.setName(cursor.getString(cursor.getColumnIndex("name")));
                    province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    province.setLevel(cursor.getInt(cursor.getColumnIndex("level")));
                    province.setParentId(cursor.getInt(cursor.getColumnIndex("parentId")));
                    list.add(province);
                }
            }while (cursor.moveToNext());
        }
        return list;
    }

}
