package io.stormcast.app.stormcast.location.add;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
public class AddLocationFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

	private static final String TAG = AddLocationFragment.class.toString();

	private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 100;
	private static final float TILT = 10f;
	private static final float ZOOM = 12f;

	private ActionBar mActionBar;
	private EditText mEditText;
	private MapView mMapView;

	private CameraPosition mCameraPosition;

	public static AddLocationFragment newInstance() {
		AddLocationFragment addLocationFragment = new AddLocationFragment();
		return addLocationFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_location, container, false);
		mEditText = (EditText) view.findViewById(R.id.location_edit_text);
		mMapView = (MapView) view.findViewById(R.id.location_map_view);
		mEditText.setOnClickListener(this);
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
				mMapView.setVisibility(View.INVISIBLE);
			}
		}, 300);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.location_edit_text:
				try {
					Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
					startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
				} catch (GooglePlayServicesRepairableException e) {
					e.printStackTrace();
				} catch (GooglePlayServicesNotAvailableException e) {
					e.printStackTrace();
				}
				break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Place place = PlaceAutocomplete.getPlace(getContext(), data);
				mEditText.setText(place.getName());
				addMarker(place);
			}
		}
	}

	private void addMarker(Place place) {
		mCameraPosition = CameraPosition.builder()
				.target(place.getLatLng())
				.tilt(TILT)
				.zoom(ZOOM)
				.build();
		MapsInitializer.initialize(getActivity().getApplicationContext());
		mMapView.getMapAsync(AddLocationFragment.this);
		mMapView.onResume();
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		mMapView.setVisibility(View.VISIBLE);
		googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
	}
}
