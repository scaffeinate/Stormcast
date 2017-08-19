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
        void onValidLocation(Location location);
        void invalidLocation(String message);
        void onLocationSaved();
        void onLocationSaveFailed(String errorMessage);
    }

    interface Presenter {
        void validateLocation(Location location);
        void saveLocation(Location location);
    }
}
