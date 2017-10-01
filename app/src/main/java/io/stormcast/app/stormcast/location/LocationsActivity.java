package io.stormcast.app.stormcast.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;
import io.stormcast.app.stormcast.location.list.LocationsListFragment;

/**
 * Created by sudhar on 8/15/17.
 */

public class LocationsActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    public static final String FRAGMENT = "fragment";
    public static final int FRAGMENT_LOCATIONS_LIST = 0;
    public static final int FRAGMENT_ADD_LOCATION = 1;

    private Toolbar mToolbar;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private int fragment = FRAGMENT_LOCATIONS_LIST;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mFragmentManager = getSupportFragmentManager();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragment = bundle.getInt(FRAGMENT, FRAGMENT_LOCATIONS_LIST);
        }

        if (savedInstanceState == null) {
            switch (fragment) {
                case FRAGMENT_LOCATIONS_LIST:
                    mFragment = LocationsListFragment.newInstance();
                    break;
                case FRAGMENT_ADD_LOCATION:
                    mFragment = AddLocationFragment.newInstance();
                    break;
            }

            mFragmentManager.beginTransaction()
                    .replace(R.id.locations_content, mFragment)
                    .addToBackStack(null)
                    .commit();
        }

        mFragmentManager.addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            finish();
        }
    }
}
