package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

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
import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.common.LocationBuilder;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.data.locations.remote.RemoteLocationsDataSource;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sudhar on 8/15/17.
 */
public class AddLocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback,
        CompoundButton.OnCheckedChangeListener, AddLocationContract.View {

    private static final String TAG = AddLocationFragment.class.toString();

    private static final String LOCATION = "location";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    private static final float TILT = 10f;
    private static final float ZOOM = 12f;

    private Context mContext;

    private ActionBar mActionBar;
    private EditText mEditText;
    private MapView mMapView;
    private ImageButton mBackgroundColorImageButton;
    private ImageButton mTextColorImageButton;
    private Switch mAutoUnitsSwitch;
    private RadioGroup mUnitsRadioGroup;
    private RelativeLayout unitsLayout;

    private CameraPosition mCameraPosition;
    private LocationsRepository mLocationsRepository;
    private AddLocationPresenter mPresenter;
    private LocationBuilder mLocationBuilder;

    public static AddLocationFragment newInstance() {
        AddLocationFragment addLocationFragment = new AddLocationFragment();
        return addLocationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource.getInstance(mContext),
                RemoteLocationsDataSource.getInstance());
        mPresenter = new AddLocationPresenter(this, mLocationsRepository);
        mLocationBuilder = new LocationBuilder();
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
        mAutoUnitsSwitch = (Switch) view.findViewById(R.id.auto_units_switch);
        mUnitsRadioGroup = (RadioGroup) view.findViewById(R.id.units_radio_group);
        unitsLayout = (RelativeLayout) view.findViewById(R.id.units_layout);

        mEditText.setOnClickListener(this);
        mBackgroundColorImageButton.setOnClickListener(this);
        mTextColorImageButton.setOnClickListener(this);
        mAutoUnitsSwitch.setOnCheckedChangeListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        mActionBar.setTitle("Add Location");

        if (savedInstanceState != null) {
            Location restored = savedInstanceState.getParcelable(LOCATION);
            mLocationBuilder.setName(restored.getName())
                    .setLatitude(restored.getLatitude())
                    .setLongitude(restored.getLongitude())
                    .setBackgroundColor(restored.getBackgroundColor())
                    .setTextColor(restored.getTextColor())
                    .setUnit(restored.getUnit());
        }

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
                mLocationBuilder.setUnit(mAutoUnitsSwitch.isChecked() ? Location.UNIT_AUTO :
                        (mUnitsRadioGroup.getCheckedRadioButtonId() == R.id.imperial_radio_button) ? Location.UNIT_IMPERIAL : Location.UNIT_METRIC);
                mPresenter.validateLocation(mLocationBuilder.build());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(mContext, data);
                mEditText.setText(place.getName());
                mEditText.setCursorVisible(false);
                mEditText.clearFocus();
                mLocationBuilder.setName(place.getName().toString())
                        .setLatLng(place.getLatLng());
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
                ColorPickerHelper.showColorPicker(mContext, Location.DEFAULT_BACKGROUND_COLOR, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(int color) {
                        GradientDrawable drawable = (GradientDrawable) mBackgroundColorImageButton.getBackground();
                        drawable.setColor(color);
                        mLocationBuilder.setBackgroundColor(color);
                    }
                });
                break;
            case R.id.text_color_image_button:
                ColorPickerHelper.showColorPicker(mContext, Location.DEFAULT_TEXT_COLOR, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(int color) {
                        GradientDrawable drawable = (GradientDrawable) mTextColorImageButton.getBackground();
                        drawable.setColor(color);
                        mLocationBuilder.setTextColor(color);
                    }
                });
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            unitsLayout.setVisibility(View.GONE);
        } else {
            unitsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapView.setVisibility(View.VISIBLE);
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LOCATION, mLocationBuilder.build());
    }

    @Override
    public void onValidLocation(Location location) {
        mPresenter.saveLocation(location);
    }

    @Override
    public void invalidLocation(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationSaved() {
        Toast.makeText(mContext, "Location saved", Toast.LENGTH_LONG).show();
        getFragmentManager().popBackStack();
    }

    @Override
    public void onLocationSaveFailed(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }


    private void addMarker(Place place) {
        mCameraPosition = CameraPosition.builder()
                .target(place.getLatLng())
                .tilt(TILT)
                .zoom(ZOOM)
                .build();
        mMapView.getMapAsync(AddLocationFragment.this);
    }
}
