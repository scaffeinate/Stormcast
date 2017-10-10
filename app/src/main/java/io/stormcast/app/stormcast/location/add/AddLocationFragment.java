package io.stormcast.app.stormcast.location.add;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import io.stormcast.app.stormcast.util.AppConstants;
import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.common.models.LocationModelBuilder;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.views.toolbar.ToolbarCallbacks;
import io.stormcast.app.stormcast.views.colorpick.MaterialColorPickDialog;
import io.stormcast.app.stormcast.views.styled.StyledButton;
import io.stormcast.app.stormcast.views.styled.StyledEditText;
import io.stormcast.app.stormcast.views.tabswitches.SwitchTabSelector;

import static android.app.Activity.RESULT_OK;

/**
 * Created by sudhar on 8/15/17.
 */
public class AddLocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, AddLocationContract.View {

    private static final String TAG = AddLocationFragment.class.toString();

    private static final String LOCATION = "location";
    private static final String LOCATION_MODEL = "location_model";
    private static final String FINISH_ACTIVITY_ON_ACTION = "finish_activity_on_action";
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
    private static final float TILT = 10f;
    private static final float ZOOM = 12f;

    private Context mContext;

    private StyledEditText mEditText;
    private MapView mMapView;
    private StyledButton mBackgroundColorBtn;
    private StyledButton mTextColorBtn;

    private CameraPosition mCameraPosition;
    private LocationsRepository mLocationsRepository;
    private AddLocationPresenter mPresenter;
    private LocationModelBuilder mLocationModelBuilder;
    private MaterialColorPickDialog.Builder mBgColorDialogBuilder;
    private MaterialColorPickDialog.Builder mTextColorDialogBuilder;
    private SwitchTabSelector mSwitchTabSelector;

    private ToolbarCallbacks mToolbarCallbacks;
    private int backgroundColor, textColor;
    private boolean finishActivityOnAction = false;

    private Animation fadeInAnimation;

    public static AddLocationFragment newInstance(boolean finishActivityOnAction) {
        return newInstance(new LocationModel(), finishActivityOnAction);
    }

    public static AddLocationFragment newInstance(LocationModel locationModel, boolean finishActivityOnAction) {
        AddLocationFragment addLocationFragment = new AddLocationFragment();

        Bundle args = new Bundle();
        args.putParcelable(LOCATION_MODEL, locationModel);
        args.putBoolean(FINISH_ACTIVITY_ON_ACTION, finishActivityOnAction);
        addLocationFragment.setArguments(args);

        return addLocationFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

        fadeInAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);

        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource
                .getInstance(getActivity().getApplicationContext()));
        mPresenter = new AddLocationPresenter(this, mLocationsRepository);

        mLocationModelBuilder = new LocationModelBuilder((LocationModel) getArguments().getParcelable(LOCATION_MODEL));
        finishActivityOnAction = getArguments().getBoolean(FINISH_ACTIVITY_ON_ACTION);

        mBgColorDialogBuilder = MaterialColorPickDialog.with(mContext);
        mTextColorDialogBuilder = MaterialColorPickDialog.with(mContext);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        mEditText = (StyledEditText) view.findViewById(R.id.location_edit_text);
        mMapView = (MapView) view.findViewById(R.id.location_map_view);
        mBackgroundColorBtn = (StyledButton) view.findViewById(R.id.background_color_btn);
        mTextColorBtn = (StyledButton) view.findViewById(R.id.text_color_btn);
        mSwitchTabSelector = (SwitchTabSelector) view.findViewById(R.id.units_switch_tab_selector);

        mEditText.setOnClickListener(this);
        mBackgroundColorBtn.setOnClickListener(this);
        mTextColorBtn.setOnClickListener(this);

        mSwitchTabSelector.addTabs(new SwitchTabSelector.SwitchTab[]{
                new SwitchTabSelector.SwitchTab<>("Auto", LocationModel.UNIT_AUTO),
                new SwitchTabSelector.SwitchTab<>("Imperial", LocationModel.UNIT_IMPERIAL),
                new SwitchTabSelector.SwitchTab<>("Metric", LocationModel.UNIT_METRIC)
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mToolbarCallbacks = (ToolbarCallbacks) getActivity();

        backgroundColor = AppConstants.DEFAULT_BACKGROUND_COLOR;
        textColor = AppConstants.DEFAULT_TEXT_COLOR;

        if (savedInstanceState != null) {
            final LocationModel restored = savedInstanceState.getParcelable(LOCATION);

            backgroundColor = Color.parseColor(restored.getBackgroundColor());
            textColor = Color.parseColor(restored.getTextColor());

            mLocationModelBuilder.setName(restored.getName())
                    .setAddress(restored.getAddress())
                    .setLatitude(restored.getLatitude())
                    .setLongitude(restored.getLongitude())
                    .setBackgroundColor(restored.getBackgroundColor())
                    .setTextColor(restored.getTextColor())
                    .setUnit(restored.getUnit());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    addMarker(new LatLng(restored.getLatitude(), restored.getLongitude()));
                }
            }, 250);
        }

        mToolbarCallbacks.setToolbarTitle("Add Location");

        GradientDrawable drawable = (GradientDrawable) mBackgroundColorBtn.getBackground();
        drawable.setColor(backgroundColor);

        drawable = (GradientDrawable) mTextColorBtn.getBackground();
        drawable.setColor(textColor);

        mMapView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mMapView.onCreate(savedInstanceState);
                MapsInitializer.initialize(getActivity().getApplicationContext());
                mMapView.onResume();
                mMapView.startAnimation(fadeInAnimation);
            }
        }, 500);
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
                mLocationModelBuilder.setUnit((Integer) mSwitchTabSelector.getSelectedValue());
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
                String name = place.getName().toString();
                String address = decodeAddress(place);
                mEditText.setText(address);
                mEditText.setCursorVisible(false);
                mEditText.clearFocus();
                mLocationModelBuilder.setName(name)
                        .setAddress(address)
                        .setLatLng(place.getLatLng());
                addMarker(place.getLatLng());
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
                            .IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .setFilter(filter)
                            .build(getActivity());
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.background_color_btn:
                ColorPickerHelper.showColorPicker(mBgColorDialogBuilder, null, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(String colorHex) {
                        GradientDrawable drawable = (GradientDrawable) mBackgroundColorBtn.getBackground();
                        drawable.setColor(Color.parseColor(colorHex));
                        mLocationModelBuilder.setBackgroundColor(colorHex);
                    }
                });
                break;
            case R.id.text_color_btn:
                ColorPickerHelper.showColorPicker(mTextColorDialogBuilder, null, new ColorPickerHelper.ColorPickerCallback() {
                    @Override
                    public void onColorSelected(String colorHex) {
                        GradientDrawable drawable = (GradientDrawable) mTextColorBtn.getBackground();
                        drawable.setColor(Color.parseColor(colorHex));
                        mLocationModelBuilder.setTextColor(colorHex);
                    }
                });
                break;
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

    private String decodeAddress(Place place) {
        LatLng latLng = place.getLatLng();
        StringBuilder builder = new StringBuilder();
        builder.append(place.getName());
        if (latLng != null) {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            try {
                List<Address> addressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                String adminArea;
                if (addressList.size() > 0 && (adminArea = addressList.get(0).getAdminArea()) != null
                        && !adminArea.isEmpty()) {
                    builder.append(",").append(adminArea);
                }
            } catch (IOException e) {

            }
        }
        return builder.toString();
    }

    private void goBack() {
        if (finishActivityOnAction) {
            getActivity().finish();
        } else {
            getFragmentManager().popBackStack();
        }
    }
}