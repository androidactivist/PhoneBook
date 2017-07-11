package com.example.a20125991.phonebook;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a20125991.phonebook.data.DataSource;
import com.example.a20125991.phonebook.data.Model;
import com.example.a20125991.phonebook.data.PhoneBookContract;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase mDb;
    RecyclerView phonebookRecyclerView;
    List<Model> mContactList;
    private TextView mEmptyText;

    // Create a constant string LOG_TAG that is equal to the class.getSimpleName()
    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set local attributes to corresponding views
        phonebookRecyclerView = (RecyclerView) this.findViewById(R.id.recyclerViewTasks);

        mEmptyText = (TextView) findViewById(R.id.tv_no_data);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // Set layout for the RecyclerView, because it's a list we are using the linear layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        phonebookRecyclerView.setLayoutManager(layoutManager);
        phonebookRecyclerView.addItemDecoration(new DividerItemDecoration(this, layoutManager.getOrientation()));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "oCreate");

        boolean didItWork = true;
        mContactList = new ArrayList<Model>();

        try {
            DataSource db = new DataSource(this);
            db.open();
            // Get all guest info from the database and save in a cursor
            Cursor cursor = db.getAllContact();


            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                Model model = new Model(cursor.getInt(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME)),
                        Long.parseLong(cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER))),
                        cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME)));
                mContactList.add(model);

            }

            db.close();


        } catch (Exception e) {

            didItWork = false;
            String error = e.toString();
            Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            if (didItWork) {
                if (mContactList.isEmpty()) {
                    Log.d(LOG_TAG,"if");
                    phonebookRecyclerView.setVisibility(View.GONE);
                    mEmptyText.setVisibility(View.VISIBLE);
                } else {
                    Log.d(LOG_TAG,"else");
                    phonebookRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyText.setVisibility(View.GONE);
                    ContactListAdapter adapter = new ContactListAdapter(this, mContactList);
                    phonebookRecyclerView.setAdapter(adapter);

                }


            }
        }


    }



}



