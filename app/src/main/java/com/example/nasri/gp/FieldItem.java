package com.example.nasri.gp;

public class FieldItem {

    String fieldName ;
    String fieldCity ;

    public FieldItem (String field , String city){
        fieldName = field ;
        fieldCity = city ;
    }
    public FieldItem (String city){
        fieldCity = city ;
    }

    public String getfieldName(){return fieldName;}
    public String getfieldCity (){return fieldCity ;}
}

