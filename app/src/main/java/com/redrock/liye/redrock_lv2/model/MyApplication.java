package com.redrock.liye.redrock_lv2.model;
import android.app.Application;
import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by a on 2016/2/26.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        // TODO您的其他初始化流程
        ApiStoreSDK.init(this, "8136cde431f9dd87ebaa9ab36e25591d");
        super.onCreate();
    }

}
