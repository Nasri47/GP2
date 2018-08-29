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
    String ownerPhone ;
    String openTime ;
    String closeTime ;
    long fieldLat ;
    long fieldLng ;
    List<ResearvationsRequistsInfo> reserveInfo = new ArrayList<>();
    int fieldId ;
    int response ;
    int suspendState ;

    public FieldInformations(int id , String fName , String oName , String fCity ,
                             String fSize , String price , String oPhone ,
                             String open , String close , List<ResearvationsRequistsInfo> reserve ,
                             long lat , long lng , int suspend){
        fieldName = fName ;
        ownerName = oName ;
        fieldCity = fCity ;
        fieldSize = fSize ;
        hourePrice = price ;
        ownerPhone = oPhone ;
        openTime = open ;
        closeTime = close ;
        reserveInfo = reserve ;
        fieldLat = lat ;
        fieldLng = lng ;
        fieldId = id ;
        suspendState = suspend ;
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
}
