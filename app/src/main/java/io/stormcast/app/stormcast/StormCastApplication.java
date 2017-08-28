package io.stormcast.app.stormcast;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.stormcast.app.stormcast.common.migrations.AddUnitsMigration;

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
        runPendingMigration();
    }

    private void runPendingMigration() {
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("add_units.realm")
                .schemaVersion(1)
                .migration(new AddUnitsMigration())
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
