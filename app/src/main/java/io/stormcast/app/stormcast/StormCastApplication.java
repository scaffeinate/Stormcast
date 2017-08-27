package io.stormcast.app.stormcast;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;

/**
 * Created by sudharti on 8/24/17.
 */

public class StormCastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) return;
        LeakCanary.install(this);
        Realm.init(this);
    }
}
