package io.stormcast.app.stormcast.location.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.common.models.LocationModel;
import io.stormcast.app.stormcast.data.locations.LocationsRepository;
import io.stormcast.app.stormcast.data.locations.local.LocalLocationsDataSource;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;
import io.stormcast.app.stormcast.location.list.helpers.ItemTouchHelperAdapter;
import io.stormcast.app.stormcast.location.list.helpers.ItemTouchHelperCallback;
import io.stormcast.app.stormcast.location.list.helpers.OnStartDragListener;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsListFragment extends Fragment implements LocationsListContract.View,
        ItemTouchHelperAdapter, OnStartDragListener {

    private Context mContext;

    private Toolbar mToolbar;
    private StyledTextView mToolbarTitle;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mNoDataTextView;
    private LocationsListAdapter mAdapter;

    private LocationsRepository mLocationsRepository;
    private LocationsListPresenter mPresenter;
    private List<LocationModel> mLocationModelList;

    private ItemTouchHelper mItemTouchHelper;
    private ItemTouchHelper.Callback mCallback;

    private int mPosition = 0;

    public static LocationsListFragment newInstance() {
        LocationsListFragment locationsListFragment = new LocationsListFragment();
        return locationsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mLocationsRepository = LocationsRepository.getInstance(LocalLocationsDataSource.getInstance(mContext));
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

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbarTitle = (StyledTextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbarTitle.setText("Locations");
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
    public void onLocationsLoaded(List<LocationModel> locationModelList) {
        mProgressBar.setVisibility(View.GONE);
        this.mLocationModelList = locationModelList;
        mAdapter = new LocationsListAdapter(mLocationModelList, this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setVisibility(View.VISIBLE);

        mCallback = new ItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(mCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDataNotAvailable() {
        mProgressBar.setVisibility(View.GONE);
        mNoDataTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLocationDeleted() {
        Toast.makeText(mContext, "Location deleted successfully", Toast.LENGTH_SHORT).show();
        mLocationModelList.remove(this.mPosition);
        mAdapter.notifyItemRemoved(this.mPosition);
        this.mPosition = 0;
    }

    @Override
    public void onLocationDeleteFailed(String errorMessage) {
        Toast.makeText(mContext, errorMessage, Toast.LENGTH_SHORT).show();
        this.mPosition = 0;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mLocationModelList, fromPosition, toPosition);
        mAdapter.notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        LocationModel locationModel = mLocationModelList.get(position);
        this.mPosition = position;
        mPresenter.deleteLocation(locationModel);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
