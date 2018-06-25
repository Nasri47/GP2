package com.example.nasri.gp;

public class ReservesInfo {

    String userName ;
    String userPhoneNumber ;
    String reserveStartTime ;
    String reserveEndTime ;

    public ReservesInfo (String user , String phone , String start , String end){
        userName = user ;
        userPhoneNumber = phone ;
        reserveStartTime = start ;
        reserveEndTime = end ;
    }

    public String getUserName(){return userName;}
    public String getUserPhoneNumber (){return userPhoneNumber ;}
    public String getStartTime(){return reserveStartTime;}
    public String getEndTime (){return reserveEndTime ;}
}

