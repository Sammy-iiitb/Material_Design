package com.plivo.castleblack;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @InjectView(R.id.app_bar) Toolbar _toolbar;
    @InjectView(R.id.main_drawer) NavigationView _drawer;
    @InjectView(R.id.drawer_layout) DrawerLayout _drawerLayout;
    @InjectView(R.id.tab_layout) TabLayout _tabLayout;
    @InjectView(R.id.pager) ViewPager _pager;

    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private ActionBarDrawerToggle _drawerToggle;
    private int _selectedId;
    private MyPagerAdapter _adapter;
    public Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.inject(this);
        ctx = getApplicationContext();
        setSupportActionBar(_toolbar);
        _drawer.setNavigationItemSelectedListener(this);
        _drawerToggle = new ActionBarDrawerToggle(this, _drawerLayout,_toolbar,R.string.drawer_open, R.string.drawer_close);
        _drawerLayout.setDrawerListener(_drawerToggle);
        _drawerToggle.syncState();
        _selectedId = savedInstanceState == null ? R.id.navigation_item_1 : savedInstanceState.getInt(SELECTED_ITEM_ID );
        navigate(_selectedId);

        _adapter = new MyPagerAdapter(getSupportFragmentManager());

        _pager.setAdapter(_adapter);

        _tabLayout.setTabsFromPagerAdapter(_adapter);
        _tabLayout.setupWithViewPager(_pager);

        _pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(_tabLayout));

    }

    private void navigate(int mSelectedId) {
        Intent intent = null;
        if (mSelectedId == R.id.navigation_item_2) {
            _drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        if (mSelectedId == R.id.navigation_item_3) {
            _drawerLayout.closeDrawer(GravityCompat.START);
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        _drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        menuItem.setChecked(true);
        _selectedId = menuItem.getItemId();

        navigate(_selectedId);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_ITEM_ID, _selectedId);
    }

    @Override
    public void onBackPressed() {
        if (_drawerLayout.isDrawerOpen(GravityCompat.START)) {
            _drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    public static class MyFragment extends Fragment {


        public MyFragment() {

        }

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container,false);
            return rootView;
        }
    }





    class MyPagerAdapter extends FragmentStatePagerAdapter {
        String[] tabs;
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
            tabs=getResources().getStringArray(R.array.tabs);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment frgmt = null;

            switch (position) {
                case 0:
                    frgmt = new MyFragment2();
                    break;
                case 1:
                    frgmt = new NavigationActivity.MyFragment();
                    break;

                default:
                    break;
            }

            return frgmt;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

