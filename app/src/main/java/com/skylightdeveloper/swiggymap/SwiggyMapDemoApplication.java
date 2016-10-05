package com.skylightdeveloper.swiggymap;

import android.app.Application;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Akash Wangalwar on 24-09-2016.
 */

public class SwiggyMapDemoApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
