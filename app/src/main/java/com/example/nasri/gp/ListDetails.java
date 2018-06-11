package com.example.nasri.gp;

public class ListDetails {

    private String mUserName;
    private String mPhoneNumber;
    private String mMessage;
    private int mType;
    public ListDetails(String userName , String phoneNumber , String message,int type) {
        mUserName = userName;
        mPhoneNumber = phoneNumber;
        mMessage = message;
        mType = type;
    }

    public String getmUserName() {
        return mUserName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmMessage() {
        return mMessage;
    }

    public int getmType() {
        return mType;
    }
}
