package com.plivo.castleblack;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.text.Editable;

import butterknife.InjectView;


/**
 * Created by lijo on 1/7/15.
 */
public class MyFragment2 extends Fragment {

    @InjectView(R.id.backspace)
    TextView mBackspaceBtn;
    @InjectView(R.id.edit_phone)
    EditText mEditText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main3, container,false);
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
        return rootView;
    }





}
