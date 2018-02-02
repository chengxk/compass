package com.cxk.compass;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

/**
 * Created by chengxiakuan on 2018/2/1.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);

        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this,
                "5a72f01ff29d986b00000101", "yingyongbao", MobclickAgent.EScenarioType.E_UM_NORMAL
                , true);
        MobclickAgent.startWithConfigure(config);

    }
}
