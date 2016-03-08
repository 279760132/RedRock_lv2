package com.redrock.liye.redrock_lv2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.redrock.liye.redrock_lv2.R;
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
        final int[] i = {0};
        Intent intent = getIntent();
        String cityName3 = intent.getStringExtra("name3");
        final String cityName = intent.getStringExtra("name1");
        final String cityName2 = intent.getStringExtra("name2");
        final TextView tep = (TextView) findViewById(R.id.cTextView);
        final TextView weathers = (TextView) findViewById(R.id.nTextView);
        final TextView flus = (TextView)findViewById(R.id.fTextView);
        final TextView winds = (TextView)findViewById(R.id.wTextView);
        TextView name = (TextView) findViewById(R.id.mTextView);
        ApiCallBack apiCallBack = new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
                Log.i("sdkdemo", "onSuccess");
                Log.i("redrock", responseString);
                try {
                    Log.i("redrock", "lili");
                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather data service 3.0");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    JSONArray jsonArray1 = jsonObject1.getJSONArray("daily_forecast");
                    JSONObject jsonObject2 = jsonArray1.getJSONObject(1);
                    JSONObject jsonObject3 = jsonObject2.getJSONObject("cond");
                    JSONObject jsonObject4 = jsonObject2.getJSONObject("tmp");
                    JSONObject jsonObject5 = jsonObject2.getJSONObject("wind");
                    JSONObject jsonObject6 = jsonObject1.getJSONObject("suggestion");
                    JSONObject jsonObject7 = jsonObject6.getJSONObject("drsg");
                    String flu = jsonObject7.getString("txt");
                    String wind = jsonObject5.getString("dir");
                    String weather = jsonObject3.getString("txt_d");
                    String tmpMax = jsonObject4.getString("max");
                    String tmpMin = jsonObject4.getString("min");
                    if (weather != null) {
                        i[0]++;
                        if (i[0] == 1) {
                            tep.setText(tmpMax + "~" + tmpMin);
                            weathers.setText(weather);
                            flus.setText(flu);
                            winds.setText("风向："+wind);
                        }
                    }

                } catch (JSONException e) {
                    Log.i("redrock", "lili");
                    e.printStackTrace();
                }
            }

            @Override
            public void onComplete() {
                Log.i("sdkdemo", "onComplete");
            }

            @Override
            public void onError(int status, String responseString, Exception e) {
                Log.i("redrock", "onError, status: " + status);
                Log.i("sdkdemo", "errMsg: " + (e == null ? "" : e.getMessage()));
                Log.i("redrock", "lili");
            }
        };
        if (cityName3 == null || cityName3.trim().length() == 0) {
            name.setText(cityName);
            String cityName1 = cityName.substring(0, cityName.length() - 1);
            Log.i("redrock", cityName1);
            Parameters para3 = new Parameters();
            para3.put("city", cityName1);
            ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para3, apiCallBack);
            if (tep.getText().equals("")){
                String cityname1 = cityName.substring(0,2);
                Log.i("redrock","cityname");
                Log.i("redrock",cityname1);
                Parameters para1 = new Parameters();
                para1.put("city", cityname1);
                ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para1, apiCallBack);
                if (tep.getText().equals("")){
                    Parameters para2 = new Parameters();
                    para2.put("city", cityName2.substring(0,cityName2.length()-1));
                    ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para2, apiCallBack);
                }
            }
        } else{
            Parameters para = new Parameters();
            Log.i("redrock",cityName3);
            name.setText(cityName3);
            para.put("city", cityName3);
            ApiStoreSDK.execute("http://apis.baidu.com/heweather/weather/free", ApiStoreSDK.GET, para, apiCallBack);
        }
    }
}