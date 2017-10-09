package io.stormcast.app.stormcast.views.navdrawer;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.views.toolbar.AnimatedActionBarDrawerToggle;

/**
 * Created by sudharti on 9/30/17.
 */

public class NavDrawerFragment extends Fragment {
    public static final int POSITION_FORECAST = 0;
    public static final int POSITION_EDIT_LOCATIONS = 1;
    public static final int POSITION_SETTINGS = 2;
    public static final int POSITION_SHARE = 4;
    public static final int POSITION_RATE_US = 5;
    public static final int POSITION_SEND_FEEDBACK = 6;
    public static final int POSITION_VERSION = 7;
    private View mDrawerFragment;
    private View mNavHeaderView;
    private ListView mListView;
    private DrawerLayout mDrawerLayout;
    private AnimatedActionBarDrawerToggle mActionBarToggle;
    private List<NavDrawerItem> mNavDrawerList;
    private NavDrawerAdapter mNavDrawerAdapter;
    private NavDrawerCallbacks mNavDrawerCallbacks;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mNavDrawerCallbacks = (NavDrawerCallbacks) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNavDrawerList = new ArrayList<>();
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_weather_lightning_white_24dp, "Forecast"));
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_map_marker_plus_white_18dp, "Edit Locations"));
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_settings_white_18dp, "Settings"));

        mNavDrawerList.add(new NavDrawerItem());

        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_share_variant_white_18dp, "Share"));
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_star_white_18dp, "Rate us"));
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_comment_white_18dp, "Send Feedback"));
        mNavDrawerList.add(new NavDrawerItem(R.drawable.ic_information_white_18dp, "v.1.0.0"));
        mNavDrawerAdapter = new NavDrawerAdapter(getContext(), mNavDrawerList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        mNavHeaderView = view.findViewById(R.id.nav_header_view);
        mListView = (ListView) view.findViewById(R.id.nav_drawer_list_view);
        mListView.setAdapter(mNavDrawerAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem(i);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavDrawerCallbacks = null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setUp(int navDrawerFragmentId, DrawerLayout drawerLayout) {
        this.mDrawerLayout = drawerLayout;
        this.mDrawerFragment = getActivity().findViewById(navDrawerFragmentId);

        mActionBarToggle = new AnimatedActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.string.nav_drawer_open, R.string.nav_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };
        mActionBarToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarToggle);

        selectItem(POSITION_FORECAST);
    }

    public ActionBarDrawerToggle getActionBarDrawerToggle() {
        return this.mActionBarToggle;
    }

    public View getNavHeaderView() {
        return this.mNavHeaderView;
    }

    public void setItemChecked(int position) {
        mListView.setItemChecked(position, true);
    }

    private void selectItem(final int position) {
        if (mNavDrawerCallbacks != null) {
            if (isDrawerOpen()) closeDrawer();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mNavDrawerCallbacks.onNavDrawerListItemClicked(position);
                }
            }, 200);
            setItemChecked(position);
        }
    }

    private boolean isDrawerOpen() {
        return mDrawerLayout.isDrawerOpen(mDrawerFragment);
    }

    private void closeDrawer() {
        mDrawerLayout.closeDrawer(mDrawerFragment);
    }

    public void toggleMenu(boolean enabled) {
        if (enabled) {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
            mActionBarToggle.animateToMenu();
        } else {
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            mActionBarToggle.animateToBackArrow();
        }
    }

    public interface NavDrawerCallbacks {
        void onNavDrawerListItemClicked(int position);
    }
}
