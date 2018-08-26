package com.example.nasri.gp;

/**
 * Created by Bors on 8/10/2018.
 */

public class LoginInfo {

    int response ;
    static int fieldId ;
    int userId ;
    public LoginInfo(){}
    public LoginInfo(int res , int fId , int uId){
        response = res ;
        fieldId = fId ;
        userId = uId ;
    }
    public LoginInfo(int res){
        response = res ;
    }

    public int getResponse(){return response;}
    public static int getFieldId(){return fieldId ;}
    public int getUserId(){return userId ;}
    public void setResponse(int res){response = res;};
    public static void setFieldId(int id){fieldId = id;}
}
