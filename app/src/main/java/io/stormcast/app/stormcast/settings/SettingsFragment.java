package io.stormcast.app.stormcast.settings;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.stormcast.app.stormcast.AppConstants;
import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.home.CustomizeCallbacks;

/**
 * Created by sudharti on 10/1/17.
 */

public class SettingsFragment extends Fragment {

    private CustomizeCallbacks mCustomizeCallbacks;

    public static SettingsFragment newInstance() {
        SettingsFragment settingsFragment = new SettingsFragment();
        return settingsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCustomizeCallbacks = (CustomizeCallbacks) getActivity();

        mCustomizeCallbacks.setToolbarTitle("Settings");
        mCustomizeCallbacks.setToolbarTextColor(Color.WHITE);
        mCustomizeCallbacks.setToolbarBackgroundColor(AppConstants.DEFAULT_BACKGROUND_COLOR);
    }
}
