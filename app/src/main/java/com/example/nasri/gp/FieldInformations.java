package com.example.nasri.gp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bors on 8/11/2018.
 */

public class FieldInformations {
    String fieldName ;
    String ownerName ;
    String fieldCity ;
    String fieldSize ;
    String hourePrice ;
    String sPone ;
    String tPhone ;
    String ownerPhone ;
    String openTime ;
    String closeTime ;
    String suspendResons ;
    long fieldLat ;
    long fieldLng ;
    List<ResearvationsRequistsInfo> reserveInfo = new ArrayList<>();
    List<String> images = new ArrayList<>();
    int fieldId ;
    int response ;
    int suspendState ;

    public FieldInformations(int id , String fName , String oName , String fCity ,
                             String fSize , String price , String oPhone , String sPhone , String tPhone ,
                             String open , String close , List<ResearvationsRequistsInfo> reserve ,
                             long lat , long lng , int suspend , String resons , List<String> img){
        fieldName = fName ;
        ownerName = oName ;
        fieldCity = fCity ;
        fieldSize = fSize ;
        hourePrice = price ;
        ownerPhone = oPhone ;
        this.sPone = sPhone ;
        this.tPhone = tPhone ;
        openTime = open ;
        closeTime = close ;
        reserveInfo = reserve ;
        suspendResons = resons ;
        fieldLat = lat ;
        fieldLng = lng ;
        fieldId = id ;
        suspendState = suspend ;
        images = img ;
    }

    public FieldInformations(){

    }
    public FieldInformations(int res){
            response = res ;
    }
    public FieldInformations(int res , int id){
        response = res ;
        fieldId = id ;
    }

    public FieldInformations(int id , int suspend , String resons){
        fieldId = id ;
        suspendState = suspend ;
        suspendResons = resons ;
    }

    public FieldInformations(String resons){
        suspendResons = resons ;
    }


    public String getFieldName(){return fieldName;}
    public String getOwnerName(){return ownerName;}
    public String getFieldCity(){return fieldCity;}
    public String getFieldSize(){return fieldSize;}
    public String getHourePrice(){return hourePrice;}
    public String getOwnerPhone(){return ownerPhone;}
    public String getOpenTime(){return openTime;}
    public String getCloseTime(){return closeTime;}
    public List<ResearvationsRequistsInfo> getReserveInfo(){return reserveInfo;}
    public int getFieldId(){return fieldId;}
    public int getResponse(){return response;}
    public long getFieldLat(){return fieldLat;}
    public long getFieldLng(){return fieldLng;}
    public int getSuspendState(){return suspendState;}
    public List<String> getImages(){return images;}
    public String getSuspendResons(){return suspendResons;}
    public String getsPone(){return  sPone;}
    public String gettPhone(){return tPhone;}
}
