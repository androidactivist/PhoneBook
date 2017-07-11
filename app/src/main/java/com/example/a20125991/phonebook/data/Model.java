package com.example.a20125991.phonebook.data;

/**
 * Created by 20125991 on 5/22/2017.
 */

public class Model {

    public int id;
    public String mFname;
    public String mLname;
    public long mPhone;
    public String mNname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmFname() {
        return mFname;
    }

    public void setmFname(String mFname) {
        this.mFname = mFname;
    }

    public String getmLname() {
        return mLname;
    }

    public void setmLname(String mLname) {
        this.mLname = mLname;
    }

    public long getmPhone() {
        return mPhone;
    }

    public void setmPhone(int mPhone) {
        this.mPhone = mPhone;
    }

    public String getmNname() {
        return mNname;
    }

    public void setmNname(String mNname) {
        this.mNname = mNname;
    }

    public Model(int id, String mFname, String mLname, long mPhone, String mNname) {
        this.id = id;
        this.mFname = mFname;
        this.mLname = mLname;
        this.mPhone = mPhone;
        this.mNname = mNname;
    }







}
