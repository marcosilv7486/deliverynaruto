package com.marcosilv7.narutodelivery.config;

import android.app.Application;

import com.marcosilv7.narutodelivery.BuildConfig;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import me.yokeyword.fragmentation.Fragmentation;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("com.marcosilv7.narutodelivery.realm")
                .build();
        Realm.setDefaultConfiguration(config);
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
    }
}
