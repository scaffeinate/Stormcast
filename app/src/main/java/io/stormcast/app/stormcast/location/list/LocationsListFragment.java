package io.stormcast.app.stormcast.location.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsListFragment extends Fragment implements LocationsListContract.View {

	private ListView mListView;

	public static LocationsListFragment newInstance() {
		LocationsListFragment locationsListFragment = new LocationsListFragment();
		return locationsListFragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_locations, container, false);
		mListView = (ListView) view.findViewById(R.id.locations_list_view);
		return view;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_edit_locations_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_location_menu_item:
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.locations_content, AddLocationFragment.newInstance())
						.addToBackStack(null)
						.commit();
				return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
