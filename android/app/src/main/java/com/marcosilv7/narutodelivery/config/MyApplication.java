package com.marcosilv7.narutodelivery.config;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("com.marcosilv7.narutodelivery.realm")
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
