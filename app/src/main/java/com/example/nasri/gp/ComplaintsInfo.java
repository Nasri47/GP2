package com.example.nasri.gp;

/**
 * Created by Bors on 8/12/2018.
 */

public class ComplaintsInfo {

    int complaintId ;
    String userName ;
    String userPhone ;
    String masage ;
    int comlaintResponse ;

    public ComplaintsInfo(int id , String name , String phone , String msg){
        userName = name ;
        userPhone = phone ;
        masage = msg ;
        complaintId = id ;
    }

    public ComplaintsInfo(int respons){
        comlaintResponse = respons ;
    }

    public  ComplaintsInfo(){}

    public int getComplaintId(){return complaintId;}
    public String getUserName(){return userName;}
    public String getUserPhone(){return userPhone;}
    public String getMasage(){return masage;}
    public int getComlaintResponse(){return comlaintResponse;}
}
