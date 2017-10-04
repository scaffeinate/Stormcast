package io.stormcast.app.stormcast.home;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import io.stormcast.app.stormcast.AppConstants;
import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.location.LocationsActivity;
import io.stormcast.app.stormcast.navdrawer.NavDrawerCallbacks;
import io.stormcast.app.stormcast.navdrawer.NavDrawerFragment;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

public class HomeActivity extends AppCompatActivity implements NavDrawerFragment.NavDrawerCallbacks,
        FragmentManager.OnBackStackChangedListener,
        ToolbarCallbacks, NavDrawerCallbacks {

    private FragmentManager mFragmentManager;
    private DrawerLayout mDrawerLayout;
    private NavDrawerFragment mNavDrawerFragment;
    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private StyledTextView mToolbarTitle;
    private Drawable mMenuDrawable;
    private int mMenuColor = AppConstants.DEFAULT_TEXT_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFragmentManager = getSupportFragmentManager();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerFragment = (NavDrawerFragment) mFragmentManager.findFragmentById(R.id.fragment_nav_drawer);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (StyledTextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setElevation(0);

        mNavDrawerFragment.setUp(R.id.fragment_nav_drawer, mDrawerLayout);
        mFragmentManager.addOnBackStackChangedListener(this);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, HomeFragment.newInstance()).commit();
        }
    }

    @Override
    public void onNavDrawerListItemClicked(int position) {
        switch (position) {
            case NavDrawerFragment.POSITION_EDIT_LOCATIONS:
                Intent intent = new Intent(this, LocationsActivity.class);
                Bundle args = new Bundle();
                args.putInt(LocationsActivity.FRAGMENT, LocationsActivity.LOCATIONS_LIST_FRAGMENT);
                intent.putExtras(args);
                startActivity(intent);
                break;
            case NavDrawerFragment.POSITION_SETTINGS:
                break;
            case NavDrawerFragment.POSITION_SHARE:
                break;
            case NavDrawerFragment.POSITION_RATE_US:
                break;
            case NavDrawerFragment.POSITION_SEND_FEEDBACK:
                break;
            case NavDrawerFragment.POSITION_VERSION:
                break;
        }
    }

    @Override
    public void onBackStackChanged() {
        mNavDrawerFragment.toggleMenu((mFragmentManager.getBackStackEntryCount() == 0));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(GravityCompat.START);
        MenuItem menuItem = menu.findItem(R.id.add_location_menu_item);
        if (menuItem != null) {
            menuItem.setVisible(!isDrawerOpen);
            mMenuDrawable = menuItem.getIcon();
            if (mMenuDrawable != null) {
                mMenuDrawable.mutate();
                setMenuColor(mMenuDrawable, mMenuColor);
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_location_menu_item:
                Intent intent = new Intent(this, LocationsActivity.class);
                Bundle args = new Bundle();
                args.putInt(LocationsActivity.FRAGMENT, LocationsActivity.ADD_LOCATION_FRAGMENT);
                intent.putExtras(args);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    @Override
    public void setToolbarTextColor(int color) {
        this.mMenuColor = color;
        mToolbarTitle.setTextColor(color);
        ActionBarDrawerToggle drawerToggle = mNavDrawerFragment.getActionBarDrawerToggle();
        drawerToggle.getDrawerArrowDrawable().setColor(color);
        if (mMenuDrawable != null) {
            setMenuColor(mMenuDrawable, color);
        }
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mToolbar.setBackgroundColor(color);
    }

    @Override
    public void setNavDrawerHeaderBackgroundColor(int color) {
        View navBackgroundView = mNavDrawerFragment.getNavHeaderView();
        navBackgroundView.setBackgroundColor(color);
    }

    @Override
    public void setNavDrawerSelected(int position) {
    }

    private void setMenuColor(Drawable drawable, int color) {
        drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }
}