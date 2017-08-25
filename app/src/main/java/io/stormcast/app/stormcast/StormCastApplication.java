package io.stormcast.app.stormcast;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by sudharti on 8/24/17.
 */

public class StormCastApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
