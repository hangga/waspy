package com.waspy.sayekti.waspy;

import android.app.Application;

import com.waspy.sayekti.waspy.db.RealmMigrations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by sayekti on 10/13/17.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("waspy.realm").schemaVersion(1).build();
        //RealmConfiguration config = new RealmConfiguration.Builder().name("waspy.realm").schemaVersion(2).migration(new RealmMigrations()).build();

        Realm.setDefaultConfiguration(config);
    }
}
