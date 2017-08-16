package io.stormcast.app.stormcast.locations.add_edit_location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.stormcast.app.stormcast.R;

/**
 * Created by sudhar on 8/15/17.
 */

public class AddEditLocationFragment extends Fragment {

	private ListView mListView;

	public static AddEditLocationFragment newInstance() {
		AddEditLocationFragment addEditLocationFragment = new AddEditLocationFragment();
		return addEditLocationFragment;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mListView = (ListView) inflater.inflate(R.layout.fragment_add_edit_location, container, false);
		return mListView;
	}
}
