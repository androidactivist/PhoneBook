package com.example.a20125991.phonebook.data;

import android.provider.BaseColumns;



public class PhoneBookContract {

    //PhoneBookEntry is an inner class that defines the contents of the PhoneBook table
    public static final class PhoneBookEntry implements BaseColumns{

        //PhoneBook table and column names
        public static final String TABLE_NAME = "phonebook";

        //Since PhoneBookEntry implements the interface "BaseColumns", it has automatically produced
        //"_ID" column in addition to the four below
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_PHONE_NUMBER = "phone_number";
        public static final String COLUMN_NICK_NAME = "nick_name";

        /*
        The above table structure looks something like the sample table below.
        With the name of the table and columns on top, and potential contents in rows

        Note: Because this implements BaseColumns, the _id column is generated automatically

        phonebook
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    first_name    |    last_name   |   phone_number    |    nick_name   |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  1   |      Rajeev      |      Ranjan    |  8555555555       |      RAJU      |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        |  2   |       John       |       Kenedy   |  8123456789       |       John     |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Karthik        |       Rai      |  9123456789       |       Kale      |
         - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

         */


    }
}
