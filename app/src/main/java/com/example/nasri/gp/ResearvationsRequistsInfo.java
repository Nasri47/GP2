package com.example.nasri.gp;

/**
 * Created by Bors on 8/11/2018.
 */

public class ResearvationsRequistsInfo {
    int reservId ;
    String reserveStart ;
    String reserveEnd ;
    String userName ;
    String userPhone ;

    public ResearvationsRequistsInfo(int id , String start , String end , String name , String phone){
        reservId = id ;
        reserveStart = start ;
        reserveEnd = end ;
        userName = name ;
        userPhone = phone ;
    }

    public ResearvationsRequistsInfo(){}

    public int getReservId(){return reservId;}
    public String getUserName(){return userName;}
    public String getUserPhone(){return userPhone;}
    public String getReserveStart(){return reserveStart;}
    public String getReserveEnd(){return reserveEnd;}
}
