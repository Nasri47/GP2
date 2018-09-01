package com.example.nasri.gp;

/**
 * Created by Bors on 8/10/2018.
 */

public class LoginInfo {

    int response ;
    static int fieldId ;
    int suspendState ;
    String suspendResons ;
    String phoneNumber ;
    String password ;
    int userId ;
    public LoginInfo(){}
    public LoginInfo(String phone , String pass){
        phoneNumber = phone ;
        password = pass ;
    }
    public LoginInfo(int res , int fId , int uId , int state , String resons){
        response = res ;
        fieldId = fId ;
        userId = uId ;
        suspendResons = resons ;
        suspendState = state ;
    }
    public LoginInfo(int res){
        response = res ;
    }

    public int getResponse(){return response;}
    public static int getFieldId(){return fieldId ;}
    public int getUserId(){return userId ;}
    public void setResponse(int res){response = res;}
    public static void setFieldId(int id){fieldId = id;}
    public int getSuspendState(){return suspendState;}
    public String getSuspendResons(){return suspendResons;}
    public String getPhoneNumber(){return phoneNumber;}
    public String getlogPassword(){return password;}
}
