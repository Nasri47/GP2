package com.example.nasri.gp;

public class FieldItem {

    int fieldId ;
    String fieldSize ;
    String fieldName ;
    String fieldCity ;

    public FieldItem (int id , String field , String city , String size){
        fieldName = field ;
        fieldCity = city ;
        fieldId = id ;
        fieldSize = size ;
    }
    public FieldItem (String city){
        fieldCity = city ;
    }
    public String getfieldName() {
        return fieldName;
    }
    public String getfieldCity() {
        return fieldCity;
    }
    public String getFieldSize() {
        return fieldSize;
    }
    public int getFieldId() {
        return fieldId;
    }


}

