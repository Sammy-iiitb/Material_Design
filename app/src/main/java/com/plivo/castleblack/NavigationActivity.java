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
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.provider.ContactsContract;
import android.net.Uri;
import android.database.Cursor;


import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.melnykov.fab.FloatingActionButton;
import com.plivo.endpoint.Endpoint;
import com.plivo.endpoint.EventListener;
import com.plivo.endpoint.Incoming;
import com.plivo.endpoint.Outgoing;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int NUMBER_STAR = 10;
    public static final int NUMBER_SHARP = 11;
    @InjectView(R.id.app_bar) Toolbar _toolbar;
    @InjectView(R.id.main_drawer) NavigationView _drawer;
    @InjectView(R.id.drawer_layout) DrawerLayout _drawerLayout;
    @InjectView(R.id.backspace) View mBackspaceBtn;
    @InjectView(R.id.edit_phone) EditText mEditText;


    Endpoint endpoint = DataHolder.getEndpoint();
    Outgoing outgoing = new Outgoing(endpoint);
    private static final String SELECTED_ITEM_ID = "selected_item_id";
    private ActionBarDrawerToggle _drawerToggle;
    private int _selectedId;
    private boolean isKeyboard;

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

        disableSoftInputFromAppearing(mEditText);
        mBackspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable currentText = mEditText.getText();
                int selectionStart = mEditText.getSelectionStart();
                if (selectionStart != 0) {
                    currentText.delete(selectionStart - 1, selectionStart);
                }
            }

        });
        mBackspaceBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mEditText.getText().clear();
                return true;
            }
        });


        ViewGroup v = (ViewGroup) findViewById(R.id.lay);

        for (int i = 0; i < 12; i++) {
            View button = v.findViewWithTag("" + i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tag = (String) v.getTag();
                    int number = Integer.parseInt(tag);
                    if (number < 10) {
                        updateText(String.valueOf(number));
                    } else if (number == NUMBER_STAR) {
                        updateText("*");
                    } else if (number == NUMBER_SHARP) {
                        updateText("#");
                    }
                }
            });
            if (i == 0 || i == NUMBER_SHARP || i == NUMBER_STAR) {
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        String tag = (String) v.getTag();
                        int number = Integer.parseInt(tag);
                        if (number == 0) {
                            updateText("+");

                            return true;
                        } else if (mEditText.getText().length() > 0) {
                            if (number == NUMBER_STAR) {
                                updateText(",");

                                return true;
                            } else if (number == NUMBER_SHARP) {
                                updateText(";");

                                return true;
                            }
                        }
                        return false;
                    }
                });
            }
        }


    }

    public void callNow(View view) {
        // Log into plivo cloud
        String number = mEditText.getText().toString();
        outgoing = endpoint.createOutgoingCall();
        outgoing.call(number);
        DataHolder.setOutgoing(outgoing);
        Log.v("PlivoOutbound", "Create outbound call object");

    }

    public void textNow(View view){
        if(!isKeyboard) {
            showSoftKeyboard(mEditText);
            isKeyboard=true;
        }
        else {
            disableSoftInputFromAppearing(mEditText);
            isKeyboard=false;
        }
    }

    public void getContacts(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();

            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver()
                            .query(uri,
                                    new String[] {
                                            ContactsContract.CommonDataKinds.Phone.NUMBER,
                                            ContactsContract.CommonDataKinds.Phone.TYPE },
                                    null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String num = c.getString(0);
                        String _num = CallHelper.helpCall(num);
                        if (_num.length() > 0)
                            mEditText.setText(_num);
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Number should start with country code",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
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

    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
    }

    private void updateText(String symbol) {
        Editable text = mEditText.getText();
        int selectionStart = mEditText.getSelectionStart();
        text.insert(selectionStart, symbol);
    }

    public static void disableSoftInputFromAppearing(EditText editText) {
        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setTextIsSelectable(true);
    }

    public void showSoftKeyboard(EditText editText) {
        Log.v("PlivoOutbound", "show keyboard");
        if (editText.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}

