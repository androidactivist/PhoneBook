package com.example.a20125991.phonebook;

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

public class EditContactActivity extends AppCompatActivity {

    private EditText mNewFirstNameEditText;
    private EditText mNewLastNameeEditText;
    private EditText mNewPhoneNumberEditText;
    private EditText mNewNickNameeEditText;
    private long id;

    // Create a constant string LOG_TAG that is equal to the class.getSimpleName()
    private final static String LOG_TAG = AddContactActivity.class.getSimpleName();

    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

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

        id = getIntent().getLongExtra("ID",0);

        DataSource db = new DataSource(this);
        db.open();
        Cursor cursor = db.getContactByID(id);
        cursor.moveToFirst();

        mNewFirstNameEditText.setText(cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME)));
        mNewLastNameeEditText.setText(cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME)));
        mNewPhoneNumberEditText.setText(""+cursor.getLong(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER)));
        mNewNickNameeEditText.setText(cursor.getString(cursor.getColumnIndex(PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME)));
        db.close();



    }

    /**
     * This method is called when user clicks on the Update to phonebook button
     *
     *
     */
    public void onUpdateContact(View v){

        //  First thing, check if any of the EditTexts are empty, return if so
        if (mNewFirstNameEditText.getText().length() == 0 ||
                mNewLastNameeEditText.getText().length() == 0 ||
                mNewPhoneNumberEditText.getText().length() == 0 ||
                mNewNickNameeEditText.getText().length() == 0
                ) {
            return;
        }

        long phoneNumber = 0;
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
            db.updateEntry(id,fName,lName,phoneNumber,nName);
            db.close();

        }catch(Exception e){

            didItWork = false;
            String error = e.toString();
            Toast.makeText(EditContactActivity.this,error, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }finally{
            if (didItWork) {
                Toast.makeText(EditContactActivity.this,
                        "Contact updated sucessfully",
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

        showDialog();


    }

    private void showDialog() {

        new MaterialDialog.Builder(EditContactActivity.this)
                .title(R.string.dialog_title)
                .content(R.string.content)
                .positiveText(R.string.agree)
                .negativeText(R.string.disagree)

                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        // TODO
                        try{
                            DataSource db = new DataSource(EditContactActivity.this);
                            db.open();
                            db.deleteEntry(id);
                            db.close();
                            finish();

                        }   catch (Exception e){
                            Log.e(LOG_TAG,"Error while deleting");
                            e.printStackTrace();
                        }
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
