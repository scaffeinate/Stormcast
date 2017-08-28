package io.stormcast.app.stormcast.common.migrations;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

/**
 * Created by sudharti on 8/28/17.
 */

public class AddUnitsMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if (oldVersion == 0) {
            RealmObjectSchema forecastSchema = schema.get("ForecastModel");
            forecastSchema.addField("units", String.class);
            oldVersion++;
        }
    }
}
