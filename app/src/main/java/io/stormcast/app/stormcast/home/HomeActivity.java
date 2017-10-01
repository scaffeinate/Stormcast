package io.stormcast.app.stormcast.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.stormcast.app.stormcast.R;

public class HomeActivity extends AppCompatActivity implements NavDrawerFragment.NavDrawerCallbacks, FragmentManager.OnBackStackChangedListener {

    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;
    private DrawerLayout mDrawerLayout;
    private NavDrawerFragment mNavDrawerFragment;
    private ActionBar mActionBar;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavDrawerFragment = (NavDrawerFragment) mFragmentManager.findFragmentById(R.id.fragment_nav_drawer);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
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
}
