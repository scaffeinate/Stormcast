package io.stormcast.app.stormcast.home;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import io.stormcast.app.stormcast.R;

public class HomeActivity extends AppCompatActivity {

    private ActionBar mActionBar;
    private FragmentManager mFragmentManager;
    private HomeFragment mHomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = getSupportActionBar();
        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            mHomeFragment = HomeFragment.newInstance();
            mFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_content, mHomeFragment)
                    .commit();
        }

        if (mActionBar != null) mActionBar.setElevation(0);
    }
}
