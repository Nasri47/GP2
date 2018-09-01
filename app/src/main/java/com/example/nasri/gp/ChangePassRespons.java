package com.example.nasri.gp;

/**
 * Created by Bors on 8/30/2018.
 */

public class ChangePassRespons {

    int fieldId ;
    int response ;

    public ChangePassRespons(){}

    public ChangePassRespons(int id , int res){
        fieldId = id ;
        response = res ;
    }

    public int getFieldId(){return fieldId;}
    public int getResponse(){return response;}
}
