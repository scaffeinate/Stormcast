package io.stormcast.app.stormcast.location.add;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;

import io.stormcast.app.stormcast.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sudhar on 8/15/17.
 */
public class AddLocationFragment extends Fragment implements AddLocationContract.View, View.OnClickListener, OnMapReadyCallback, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = AddLocationFragment.class.toString();

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    private static final float TILT = 10f;
    private static final float ZOOM = 12f;

    private ActionBar mActionBar;
    private EditText mEditText;
    private MapView mMapView;
    private ImageButton mBackgroundColorImageButton;
    private ImageButton mTextColorImageButton;
    private Switch autoUnitsSwitch;
    private RelativeLayout unitsLayout;

    private CameraPosition mCameraPosition;

    public static AddLocationFragment newInstance() {
        AddLocationFragment addLocationFragment = new AddLocationFragment();
        return addLocationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        mEditText = (EditText) view.findViewById(R.id.location_edit_text);
        mMapView = (MapView) view.findViewById(R.id.location_map_view);
        mBackgroundColorImageButton = (ImageButton) view.findViewById(R.id.background_color_image_button);
        mTextColorImageButton = (ImageButton) view.findViewById(R.id.text_color_image_button);
        autoUnitsSwitch = (Switch) view.findViewById(R.id.auto_units_switch);
        unitsLayout = (RelativeLayout) view.findViewById(R.id.units_layout);

        mEditText.setOnClickListener(this);
        mBackgroundColorImageButton.setOnClickListener(this);
        mTextColorImageButton.setOnClickListener(this);
        autoUnitsSwitch.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Add Location");

        mMapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.onCreate(savedInstanceState);
                MapsInitializer.initialize(getActivity().getApplicationContext());
                mMapView.onResume();
            }
        }, 300);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_location_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_location_menu_item:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                mEditText.setText(place.getName());
                mEditText.setCursorVisible(false);
                mEditText.clearFocus();
                addMarker(place);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_edit_text:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.background_color_image_button:
                showColorPicker(mBackgroundColorImageButton, R.color.colorPrimary);
                break;
            case R.id.text_color_image_button:
                showColorPicker(mTextColorImageButton, android.R.color.white);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        unitsLayout.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView.setVisibility(View.VISIBLE);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
    }

    @Override
    public void onValidLocation() {

    }

    @Override
    public void invalidLocation(String message) {

    }

    @Override
    public void onSaveLocation() {

    }

    private void addMarker(Place place) {
        mCameraPosition = CameraPosition.builder()
                .target(place.getLatLng())
                .tilt(TILT)
                .zoom(ZOOM)
                .build();
        mMapView.getMapAsync(AddLocationFragment.this);
    }

    private void showColorPicker(final ImageButton imageButton, int defaultColor) {
        ColorPickerDialogBuilder.with(getContext())
                .setTitle("Choose color")
                .initialColor(ContextCompat.getColor(getContext(), defaultColor))
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("Ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        GradientDrawable drawable = (GradientDrawable) imageButton.getBackground();
                        drawable.setColor(selectedColor);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }
}
