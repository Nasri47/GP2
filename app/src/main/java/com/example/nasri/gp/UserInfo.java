package com.example.nasri.gp;

/**
 * Created by Bors on 8/10/2018.
 */

public class UserInfo {

    String phone;
    String name ;
    String password ;
    String response ;
    int id ;
    int reserveId ;
    String reserveBegg ;
    String reserveEn ;

    public UserInfo(){}
    public UserInfo(String phoneNumber , String userName){
        phone = phoneNumber ;
        name = userName ;
    }
    public UserInfo(String resp){
        response = resp ;
    }
    public UserInfo(int id , int reId , String phoneNumber , String userName , String reB , String reE){
        phone = phoneNumber ;
        name = userName ;
        this.id = id ;
        reserveId = reId ;
        reserveBegg = reB ;
        reserveEn = reE ;
    }
    public UserInfo(String phoneNumber , String userName , String pass){
        phone = phoneNumber ;
        name = userName ;
        password = pass ;
    }
    public String getResponse(){return response;}
    public String getPhone(){return phone;}
    public String getUserName(){return name;}
    public String getPassword(){return password;}
    public int getId(){return id;}
    public int getReserveId(){return reserveId;}
    public String getReserveBegg(){return reserveBegg;}
    public String getReserveEn(){return reserveEn;}
}
