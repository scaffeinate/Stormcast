package io.stormcast.app.stormcast.location.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.Location;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.data.locations.remote.RemoteLocationsDataSource;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsListFragment extends Fragment implements LocationsListContract.View {

    private Context mContext;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mNoDataTextView;
    private LocationsListAdapter mAdapter;

    private LocationsRepository mLocationsRepository;
    private LocationsListPresenter mPresenter;

    public static LocationsListFragment newInstance() {
        LocationsListFragment locationsListFragment = new LocationsListFragment();
        return locationsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource.getInstance(mContext),
                RemoteLocationsDataSource.getInstance());
        mPresenter = new LocationsListPresenter(this, mLocationsRepository);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_locations, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.locations_list_view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.locations_list_progress_bar);
        mNoDataTextView = (TextView) view.findViewById(R.id.no_data_text_view);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.getLocations();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.locations_menu, menu);
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

    @Override
    public void onLocationsLoaded(List<Location> locationList) {
        mProgressBar.setVisibility(View.GONE);
        mAdapter = new LocationsListAdapter(locationList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDataNotAvailable() {
        mProgressBar.setVisibility(View.GONE);
        mNoDataTextView.setVisibility(View.VISIBLE);
    }
}
