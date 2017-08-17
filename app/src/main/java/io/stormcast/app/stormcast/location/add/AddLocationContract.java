package io.stormcast.app.stormcast.location.add;

import android.support.v7.app.AlertDialog;
import android.widget.ImageButton;

import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import io.stormcast.app.stormcast.common.Location;

/**
 * Created by sudhar on 8/15/17.
 */

public interface AddLocationContract {
    interface View {
        void onValidLocation();
        void invalidLocation(String message);
        void onSaveLocation();
    }

    interface Presenter {
        void validateLocation();
        void saveLocation(Location location);
    }
}
