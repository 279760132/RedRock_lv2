package com.redrock.liye.redrock_lv2.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.redrock.liye.redrock_lv2.db.CoolWeatherDBHelper;
import com.redrock.liye.redrock_lv2.R;
import com.redrock.liye.redrock_lv2.model.Area;
import com.redrock.liye.redrock_lv2.model.AreaAdapter;
import com.redrock.liye.redrock_lv2.model.CityHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CoolWeatherDBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);

        dbHelper = new CoolWeatherDBHelper(this,"citycode.com.redrock.liye.redrock_lv2.db",null,1);
        db = dbHelper.getWritableDatabase();
        final TextView textView = (TextView) findViewById(R.id.title_text);
        textView.setText("中国");

        final CityHelper cityHelper = new CityHelper();
        final List<Area> provinceList = cityHelper.loadProvinces(db);

        final AreaAdapter areaAdapter = new AreaAdapter(MainActivity.this,R.layout.area_item,provinceList);
        final ListView listView = (ListView) findViewById(R.id.list_view);
        final EditText editText = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,WeatherActivity.class);
                intent.putExtra("name3",editText.getText().toString());
                startActivity(intent);
                Log.i("redrock","ssssss");
            }
        });
        listView.setAdapter(areaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Area area = provinceList.get(position);
                textView.setText(area.getName());
                Toast.makeText(MainActivity.this,area.getName(),Toast.LENGTH_SHORT).show();
                final List<Area> cityList =  cityHelper.loadCity(db,area.getCid());
                AreaAdapter cityAdapter = new AreaAdapter(MainActivity.this,R.layout.area_item,cityList);
                listView.setAdapter(cityAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Area area1 = cityList.get(position);
                        textView.setText(area1.getName());
                        final List<Area> countyList = cityHelper.loadCounty(db, area1.getCid());
                        AreaAdapter countyAdapter = new AreaAdapter(MainActivity.this,R.layout.area_item,countyList);
                        listView.setAdapter(countyAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Area area2 = countyList.get(position);
                                String name1=area2.getName();
                                String name2=area1.getName();
                                Intent intent =new Intent(MainActivity.this,WeatherActivity.class);
                                intent.putExtra("name1",name1);
                                intent.putExtra("name2",name2);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

}
