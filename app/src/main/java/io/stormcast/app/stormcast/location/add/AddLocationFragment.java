package io.stormcast.app.stormcast.location.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudhar on 8/15/17.
 */

public class AddLocationFragment extends Fragment {

	public static AddLocationFragment newInstance() {
		AddLocationFragment addLocationFragment = new AddLocationFragment();
		return addLocationFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_add_location, container, false);
		return super.onCreateView(inflater, container, savedInstanceState);
	}
}
