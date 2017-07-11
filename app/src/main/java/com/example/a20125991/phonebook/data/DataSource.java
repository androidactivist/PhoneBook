package com.example.a20125991.phonebook.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DataSource {

    private static PhoneBookDBHelper mHelper;
    private final Context mContext;
    private SQLiteDatabase mDatabase;

    //Constructor
    public DataSource(Context context){
        this.mContext = context;
    }


    //Initializing the PhoneBookDBHelper to create database and table
    public DataSource open() {
        mHelper = PhoneBookDBHelper.getInstance(mContext);
        mDatabase = mHelper.getWritableDatabase();
        return this;
    }

    //Closing the database
    public void close() {
        mHelper.close();
    }



    private static class PhoneBookDBHelper extends SQLiteOpenHelper {

        // The name of the database
        private static final String DATABASE_NAME = "phonebookDb.db";

        // If we  change the database schema, we must increment the database version
        private static final int VERSION = 1;

        public PhoneBookDBHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        public static synchronized PhoneBookDBHelper getInstance(Context context) {

            // Use the application context, which will ensure that you
            // don't accidentally leak an Activity's context.
            if (mHelper == null) {
                mHelper = new PhoneBookDBHelper(context.getApplicationContext());
            }
            return mHelper;
        }


        /**
         * Called when the tasks database is created for the first time.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // Creating phonebook table
            final String CREATE_TABLE = "CREATE TABLE " + PhoneBookContract.PhoneBookEntry.TABLE_NAME + " (" +
                    PhoneBookContract.PhoneBookEntry._ID + " INTEGER PRIMARY KEY, " +
                    PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                    PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                    PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER + " LONG NOT NULL, " +
                    PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME + " TEXT NOT NULL);";

            db.execSQL(CREATE_TABLE);
        }


        /**
         * This method discards the old table of data and calls onCreate to recreate a new one.
         * This only occurs when the version number for this database (DATABASE_VERSION) is incremented.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + PhoneBookContract.PhoneBookEntry.TABLE_NAME);
            onCreate(db);
        }

    }

    /**
     * Query the mDatabase and get all contact from the phonebook table
     *
     * @return Cursor containing the list of guests
     */
    public Cursor getAllContact() {
        return mDatabase.query(
                PhoneBookContract.PhoneBookEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    //Remove contact from phonebook table based on id
    public boolean removeContact(long id) {
        // COMPLETED (2) Inside, call mDb.delete to pass in the TABLE_NAME and the condition that WaitlistEntry._ID equals id
        return mDatabase.delete(PhoneBookContract.PhoneBookEntry.TABLE_NAME, PhoneBookContract.PhoneBookEntry._ID + "=" + id, null) > 0;
    }

    //  Create a new contact method
    public long addNewContact(String fName,
                               String lName,
                               long phone,
                               String nName) {
        //  Inside, create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();

        // call put to insert the name value with the key COLUMN_FIRST_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME, fName);
        // call put to insert the party size value with the key COLUMN_LAST_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME, lName);
        // call put to insert the name value with the key COLUMN_PHONE_NUMBER
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER, phone);
        // call put to insert the party size value with the key COLUMN_NICK_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME, nName);

        // COMPLETED (8) call insert to run an insert query on TABLE_NAME with the ContentValues created
        return mDatabase.insert(PhoneBookContract.PhoneBookEntry.TABLE_NAME, null, cv);


    }

    public Cursor getContactByID(long id){

        String[] columns = new String[] { PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME,
        PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME,PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER,
        PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME};

        Cursor cursor = mDatabase.query(PhoneBookContract.PhoneBookEntry.TABLE_NAME,columns,PhoneBookContract.PhoneBookEntry._ID+"=" + id,
                null,null,null,null);

        if(cursor != null){
            return cursor;
        }
        return null;
    }

    public void updateEntry(long lRow, String fName, String lName,
                            long mPhone, String nName)
            throws SQLException {
        String[] columns = new String[] { PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME,
                PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME,PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER,
                PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME};

        //  Inside, create a ContentValues instance to pass the values onto the insert query
        ContentValues cv = new ContentValues();

        // call put to insert the name value with the key COLUMN_FIRST_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_FIRST_NAME, fName);
        // call put to insert the party size value with the key COLUMN_LAST_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_LAST_NAME, lName);
        // call put to insert the name value with the key COLUMN_PHONE_NUMBER
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER, mPhone);
        // call put to insert the party size value with the key COLUMN_NICK_NAME
        cv.put(PhoneBookContract.PhoneBookEntry.COLUMN_NICK_NAME, nName);

        mDatabase.update(PhoneBookContract.PhoneBookEntry.TABLE_NAME, cv, PhoneBookContract.PhoneBookEntry._ID + "=" + lRow,
                null);
    }

    public void deleteEntry(long id) throws SQLException {
        mDatabase.delete(PhoneBookContract.PhoneBookEntry.TABLE_NAME, PhoneBookContract.PhoneBookEntry._ID + "=" + id, null);
    }

    public void deleteEntryByPhone(long phoneNumber) {
        mDatabase.delete(PhoneBookContract.PhoneBookEntry.TABLE_NAME, PhoneBookContract.PhoneBookEntry.COLUMN_PHONE_NUMBER+ "=" + phoneNumber, null);
    }




}









