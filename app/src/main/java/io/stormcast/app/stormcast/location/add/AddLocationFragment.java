package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.models.LocationModelBuilder;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.data.locations.remote.RemoteLocationsDataSource;
import io.stormcast.app.stormcast.views.colorpick.MaterialColorPickDialog;

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
    private LocationModelBuilder mLocationModelBuilder;
    private MaterialColorPickDialog.Builder mBgColorDialogBuilder;
    private MaterialColorPickDialog.Builder mTextColorDialogBuilder;

    public static AddLocationFragment newInstance() {
        AddLocationFragment addLocationFragment = new AddLocationFragment();
        return addLocationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource.getInstance(),
                RemoteLocationsDataSource.getInstance());
        mPresenter = new AddLocationPresenter(this, mLocationsRepository);
        mLocationModelBuilder = new LocationModelBuilder();
        mBgColorDialogBuilder = MaterialColorPickDialog.with(mContext);
        mTextColorDialogBuilder = MaterialColorPickDialog.with(mContext);
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (mActionBar != null) {
            mActionBar.setTitle(mContext.getResources().getString(R.string.add_location));
        }

        String backgroundColor = LocationModel.DEFAULT_BACKGROUND_COLOR;
        String textColor = LocationModel.DEFAULT_TEXT_COLOR;

        if (savedInstanceState != null) {
            final LocationModel restored = savedInstanceState.getParcelable(LOCATION);

            backgroundColor = restored.getBackgroundColor();
            textColor = restored.getTextColor();

            mLocationModelBuilder.setName(restored.getName())
                    .setLatitude(restored.getLatitude())
                    .setLongitude(restored.getLongitude())
                    .setBackgroundColor(restored.getBackgroundColor())
                    .setTextColor(restored.getTextColor())
                    .setUnit(restored.getUnit());

            /*new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addMarker(new LatLng(restored.getLatitude(), restored.getLongitude()));
                }
            }, 250);*/
        }

        GradientDrawable drawable = (GradientDrawable) mBackgroundColorImageButton.getBackground();
        drawable.setColor(Color.parseColor(backgroundColor));

        drawable = (GradientDrawable) mTextColorImageButton.getBackground();
        drawable.setColor(Color.parseColor(textColor));

        /*mMapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.onCreate(savedInstanceState);
                MapsInitializer.initialize(getActivity().getApplicationContext());
                mMapView.onResume();
            }
        }, 300);*/
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
                mLocationModelBuilder.setUnit(mAutoUnitsSwitch.isChecked() ? LocationModel.UNIT_AUTO :
                        (mUnitsRadioGroup.getCheckedRadioButtonId() == R.id.imperial_radio_button) ? LocationModel.UNIT_IMPERIAL : LocationModel.UNIT_METRIC);
                mPresenter.validateLocation(mLocationModelBuilder.build());
                return true;
            case android.R.id.home:
                goBack();
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
                mLocationModelBuilder.setName(place.getName().toString())
                        .setLatLng(place.getLatLng());
                //addMarker(place.getLatLng());
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.location_edit_text:
                try {
                    AutocompleteFilter filter = new AutocompleteFilter
                            .Builder()
                            .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                            .build();
                    Intent intent = new PlaceAutocomplete
                            .IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .setFilter(filter)
                            .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.background_color_image_button:
                ColorPickerHelper.showColorPicker(mBgColorDialogBuilder, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(String colorHex) {
                        GradientDrawable drawable = (GradientDrawable) mBackgroundColorImageButton.getBackground();
                        drawable.setColor(Color.parseColor(colorHex));
                        mLocationModelBuilder.setBackgroundColor(colorHex);
                    }
                });
                break;
            case R.id.text_color_image_button:
                ColorPickerHelper.showColorPicker(mTextColorDialogBuilder, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(String colorHex) {
                        GradientDrawable drawable = (GradientDrawable) mTextColorImageButton.getBackground();
                        drawable.setColor(Color.parseColor(colorHex));
                        mLocationModelBuilder.setTextColor(colorHex);
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
        outState.putParcelable(LOCATION, mLocationModelBuilder.build());
    }

    @Override
    public void onValidLocation(LocationModel locationModel) {
        mPresenter.saveLocation(locationModel);
    }

    @Override
    public void invalidLocation(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationSaved() {
        Toast.makeText(mContext, "Location saved", Toast.LENGTH_LONG).show();
        goBack();
    }

    @Override
    public void onLocationSaveFailed(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void addMarker(LatLng latLng) {
        mCameraPosition = CameraPosition.builder()
                .target(latLng)
                .tilt(TILT)
                .zoom(ZOOM)
                .build();
        mMapView.getMapAsync(AddLocationFragment.this);
    }

    private void goBack() {
        getFragmentManager().popBackStack();
    }
}