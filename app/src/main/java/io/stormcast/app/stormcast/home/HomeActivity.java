package io.stormcast.app.stormcast.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.navdrawer.NavDrawerFragment;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

public class HomeActivity extends AppCompatActivity implements NavDrawerFragment.NavDrawerCallbacks,
        FragmentManager.OnBackStackChangedListener,
        CustomizeCallbacks {

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private DrawerLayout mDrawerLayout;
    private NavDrawerFragment mNavDrawerFragment;
    private ActionBar mActionBar;
    private Toolbar mToolbar;
    private StyledTextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        if (savedInstanceState == null) {
            mHomeFragment = HomeFragment.newInstance();
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, mHomeFragment)
                    .commit();
        }
        mFragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void onNavDrawerListItemClicked(int position) {

    }

    @Override
    public void onBackStackChanged() {
        mNavDrawerFragment.toggleMenu((mFragmentManager.getBackStackEntryCount() == 0));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(GravityCompat.START);
        MenuItem menuItem = menu.findItem(R.id.add_location_menu_item);
        if(menuItem != null) {
            menuItem.setVisible(!isDrawerOpen);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    @Override
    public void setToolbarTextColor(int color) {
        mToolbarTitle.setTextColor(color);
        ActionBarDrawerToggle drawerToggle = mNavDrawerFragment.getActionBarDrawerToggle();
        drawerToggle.getDrawerArrowDrawable().setColor(color);
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
}
