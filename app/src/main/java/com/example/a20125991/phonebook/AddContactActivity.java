package com.example.a20125991.phonebook;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.a20125991.phonebook.data.DataSource;
import com.example.a20125991.phonebook.data.PhoneBookContract;



public class AddContactActivity extends AppCompatActivity {


    private EditText mNewFirstNameEditText;
    private EditText mNewLastNameeEditText;
    private EditText mNewPhoneNumberEditText;
    private EditText mNewNickNameeEditText;

    // Create a constant string LOG_TAG that is equal to the class.getSimpleName()
    private final static String LOG_TAG = AddContactActivity.class.getSimpleName();

    private SQLiteDatabase mDb;
    long phoneNumber;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        mNewFirstNameEditText = (EditText) findViewById(R.id.editTextFirstName);
        mNewLastNameeEditText = (EditText) findViewById(R.id.editTextLastName);
        mNewPhoneNumberEditText = (EditText) findViewById(R.id.editTextPhoneNumber);
        mNewNickNameeEditText = (EditText) findViewById(R.id.editTextNickName);

        mNewFirstNameEditText.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.focus_border);
                }
                else{
                    view.setBackgroundResource( R.drawable.rounded_corner);
                }
            }
        });

        mNewLastNameeEditText.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.focus_border);
                }
                else{
                    view.setBackgroundResource( R.drawable.rounded_corner);
                }
            }
        });

        mNewPhoneNumberEditText.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.focus_border);
                }
                else{
                    view.setBackgroundResource( R.drawable.rounded_corner);
                }
            }
        });

        mNewNickNameeEditText.setOnFocusChangeListener( new View.OnFocusChangeListener(){

            public void onFocusChange( View view, boolean hasfocus){
                if(hasfocus){

                    view.setBackgroundResource( R.drawable.focus_border);
                }
                else{
                    view.setBackgroundResource( R.drawable.rounded_corner);
                }
            }
        });

        /*int id = getIntent().getIntExtra("ID",0);

        DataSource db = new DataSource(this);
        db.open();
        Cursor cursor = db.getContactByID(id);

        mNewFirstNameEditText.setText(cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME)));
*/



    }

    /**
     * This method is called when user clicks on the Add to phonebook button
     *
     *
     */
    public void onAddContact(View v){

        //  First thing, check if any of the EditTexts are empty, return if so
        if (mNewFirstNameEditText.getText().length() == 0 ||
                mNewLastNameeEditText.getText().length() == 0 ||
                mNewPhoneNumberEditText.getText().length() == 0 ||
                mNewNickNameeEditText.getText().length() == 0
                ) {
            return;
        }


        String fName = mNewFirstNameEditText.getText().toString();
        String lName = mNewLastNameeEditText.getText().toString();
        String nName = mNewNickNameeEditText.getText().toString();

        try {
            //mNewPhoneNumberEditText inputType="number", so this should always work
            phoneNumber = Long.parseLong(mNewPhoneNumberEditText.getText().toString());
        } catch (NumberFormatException ex) {
            //  Make sure you surround the Integer.parseInt with a try catch and log any exception
            Log.e(LOG_TAG, "Failed to parse phonenumber text to number: " + ex.getMessage());
        }

        boolean didItWork = true;
        try{
            DataSource db = new DataSource(this);
            db.open();
            db.addNewContact(fName,lName,phoneNumber,nName);
            db.close();

        }catch(Exception e){

            didItWork = false;
            String error = e.toString();
            Toast.makeText(AddContactActivity.this,error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally{
            if (didItWork) {
                Toast.makeText(AddContactActivity.this,
                        "New contact added sucessfully",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

    }


    /**
     * This method is called when user clicks on the Delete to phonebook button
     *
     *
     */
    public void onDeleteContact(View v){
        shoDialog();

    }

    public void shoDialog(){
        new MaterialDialog.Builder(AddContactActivity.this)
                .title(R.string.dialog_title)
                .content(R.string.contents)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        finish();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        dialog.dismiss();
                    }
                }).show();
    }
    }








