package com.plivo.castleblack;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
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
import android.widget.TextView;


import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.inject(this);
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
            super.onBackPressed();
        }
    }

    public static class MyFragment extends Fragment {
        public static final java.lang.String ARG_PAGE = "arg_page";

        public MyFragment() {

        }

        public static MyFragment newInstance(int pageNumber) {
            MyFragment myFragment = new MyFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(ARG_PAGE, pageNumber + 1);
            myFragment.setArguments(arguments);
            return myFragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            int pageNumber = arguments.getInt(ARG_PAGE);
            TextView myText = new TextView(getActivity());
            myText.setText("Hello I am the text inside this Fragment " + pageNumber);
            myText.setGravity(Gravity.CENTER);
            return myText;
        }
    }
}

class MyPagerAdapter extends FragmentStatePagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        NavigationActivity.MyFragment myFragment = NavigationActivity.MyFragment.newInstance(position);
        return myFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "     Tab " + (position + 1) + "     ";
    }
}