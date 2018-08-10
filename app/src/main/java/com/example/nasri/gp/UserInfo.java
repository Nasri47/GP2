package com.example.nasri.gp;

/**
 * Created by Bors on 8/10/2018.
 */

public class UserInfo {

    String phone;
    String name ;
    String password ;

    public UserInfo(String phoneNumber , String userName){
        phone = phoneNumber ;
        name = userName ;
    }
    public UserInfo(String phoneNumber , String userName , String pass){
        phone = phoneNumber ;
        name = userName ;
        password = pass ;
    }

    public String getPhone(){return phone;}
    public String getUserName(){return name;}
    public String getPassword(){return password;}
}
