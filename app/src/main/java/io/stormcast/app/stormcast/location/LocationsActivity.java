package io.stormcast.app.stormcast.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.stormcast.app.stormcast.R;
import io.stormcast.app.stormcast.views.toolbar.ToolbarCallbacks;
import io.stormcast.app.stormcast.location.add.AddLocationFragment;
import io.stormcast.app.stormcast.location.list.LocationsListFragment;
import io.stormcast.app.stormcast.views.styled.StyledTextView;

/**
 * Created by sudharti on 10/3/17.
 */

public class LocationsActivity extends AppCompatActivity implements ToolbarCallbacks {

    public final static String FRAGMENT = "fragment";
    public final static int LOCATIONS_LIST_FRAGMENT = 0;
    public final static int ADD_LOCATION_FRAGMENT = 1;

    private StyledTextView mToolbarTitle;
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (StyledTextView) findViewById(R.id.toolbar_title);

        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

        mFragmentManager = getSupportFragmentManager();

        int fragment = LOCATIONS_LIST_FRAGMENT;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fragment = bundle.getInt(FRAGMENT, LOCATIONS_LIST_FRAGMENT);
        }

        if (savedInstanceState == null) {
            switch (fragment) {
                case LOCATIONS_LIST_FRAGMENT:
                    mFragmentManager
                            .beginTransaction()
                            .replace(R.id.locations_content, LocationsListFragment.newInstance())
                            .commit();
                    break;
                case ADD_LOCATION_FRAGMENT:
                    mFragmentManager
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left_exit, R.anim.slide_right_enter, R.anim.slide_right_exit)
                            .replace(R.id.locations_content, AddLocationFragment.newInstance(true))
                            .commit();
                    break;
            }
        }
    }

    @Override
    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }

    @Override
    public void setToolbarTextColor(int color) {
        mToolbarTitle.setTextColor(color);
    }

    @Override
    public void setToolbarBackgroundColor(int color) {
        mToolbar.setBackgroundColor(color);
    }
}
