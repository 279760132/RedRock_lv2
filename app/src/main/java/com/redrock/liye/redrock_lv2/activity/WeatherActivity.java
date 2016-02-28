package com.redrock.liye.redrock_lv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.redrock.liye.redrock_lv2.R;
import com.redrock.liye.redrock_lv2.model.CityHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by a on 2016/2/26.
 */
public class WeatherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_layout);
        Intent intent = getIntent();
        String cityName = intent.getStringExtra("name");
        final TextView tep = (TextView) findViewById(R.id.cTextView);
        final TextView weathers = (TextView) findViewById(R.id.nTextView);
        TextView name = (TextView) findViewById(R.id.mTextView);
        name.setText(cityName);
        final String[] news = {null};
        String cityName1 = cityName.substring(0, cityName.length() - 1);
        Log.i("redrock", cityName1);
        Parameters para = new Parameters();
        para.put("city", cityName1);
        ApiCallBack apiCallBack = new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
                Log.i("sdkdemo", "onSuccess");
                Log.i("redrock", responseString);
                try {
                    Log.i("redrock","lili");
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(1);
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("cond");
                    JSONObject jsonObject4 = jsonObject2.getJSONObject("tmp");
                    String weather = jsonObject3.getString("txt_d");
                    String tmpMax = jsonObject4.getString("max");
                    String tmpMin = jsonObject4.getString("min" );
                    tep.setText(tmpMax+"~"+tmpMin);
                    weathers.setText(weather);

                }catch (JSONException e){
                    Log.i("redrock","lili");
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                Log.i("sdkdemo", "onComplete");
            }

            @Override
            public void onError(int status, String responseString, Exception e) {
                Log.i("sdkdemo", "onError, status: " + status);
                Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
            }
        };
        ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para, apiCallBack);
    }
}